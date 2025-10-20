var poJson = [];
var deJson = [];
//取得產品名稱
async function getProductNameById(pid) {
    try {
        const response = await $.ajax({
            method: "GET",
            url: "http://localhost:8080/api/product/" + pid,
            dataType: "json"
        });
        return response.name;
    } catch {
        return "(未知商品)";
    }
}
// 日期格式化：yyyy-MM-dd hh:mm:ss
function formatDateTime(datetimeStr) {
    const date = new Date(datetimeStr);
    const y = date.getFullYear();
    const m = String(date.getMonth() + 1).padStart(2, '0');
    const d = String(date.getDate()).padStart(2, '0');
    const hh = String(date.getHours()).padStart(2, '0');
    const mm = String(date.getMinutes()).padStart(2, '0');
    const ss = String(date.getSeconds()).padStart(2, '0');
    return `${y}-${m}-${d} ${hh}:${mm}:${ss}`;
}

$(document).ready(function () {
    // 檢查登入狀態
    const loginData = sessionStorage.getItem("loginMember");
    //確認是否有登入
    if (!loginData) {
        alert("請先登入！");
        window.location.href = "login.html";
        return;
    }

    // 解析會員資料
    const member = JSON.parse(loginData);
    $("#memberName").text(`${member.name} 歡迎光臨！`);

    // 登出功能
    $("#logoutBtn").click(() => {
        if (confirm("確定要登出嗎？")) {
            sessionStorage.removeItem("loginMember");
            alert("您已登出！");
            window.location.href = "login.html";
        }
    });
    //呼叫後台 取porder值
    $.ajax({
        method: "GET",
        url: "http://localhost:8080/api/porder",
        dataType: "json"
    }).then(function (response) {
        poJson = response;
        $.each(poJson, function (i, ele) {
            if (ele.memberId === member.id) {
                var li = $("<li></li>").text(ele.id);
                $("#list").append(li);
            }
        });

    }).fail(function () {
        alert("porder資料讀取失敗");
    });
    //呼叫後台 取detail值
    $.ajax({
        method: "GET",
        url: "http://localhost:8080/api/porderDetail",
        dataType: "json"
    }).then(function (response) {
        deJson = response;
    }).fail(function () {
        alert("porderDetail資料讀取失敗");
    });

    // 點選左邊的訂單號，只顯示該訂單
    $(document).on("click", "#list li", async function () {
        var selectedId = $(this).text();
        $("#showRecord").empty();
        $("#showPorder").empty();

        var order = poJson.find(o => String(o.id) === String(selectedId));

        if (!order) {
            alert("找不到訂單資料！");
            return;
        }

        var tr1 = $("<tr></tr>");
        tr1.append($("<td></td>").text(order.id));
        tr1.append($("<td></td>").text(order.memberName));
        tr1.append($("<td></td>").text("$" + order.totalPrice));

        // 使用格式化函式顯示 dateTime
        tr1.append($("<td></td>").text(formatDateTime(order.dateTime)));

        tr1.append($("<td></td>").text(order.remark || ""));
        $("#showPorder").append(tr1);

        for (const ele of deJson) {
            if (ele.porderId === order.id) {
                const pName = await getProductNameById(ele.productId);
                var tr = $("<tr></tr>");
                tr.append($("<td></td>").text(pName));
                tr.append($("<td></td>").text("$" + ele.unitPrice));
                tr.append($("<td></td>").text(ele.quantity));
                $("#showRecord").append(tr);
            }
        }
    });
});