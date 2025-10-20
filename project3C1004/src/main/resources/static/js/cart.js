var cart = [];
var member = {};
var total = 0;
var arrObj = [];
var porObj = {};

// 取得購物車資料
function getCart() {
    return JSON.parse(sessionStorage.getItem("cart")) || [];
}

// 儲存購物車資料
function saveCart(newCart) {
    sessionStorage.setItem("cart", JSON.stringify(newCart));
    cart = newCart; // 更新全域 cart
}

// 取得商品 by ID
function getItemById(pid) {
    return cart.find(item => item.id === pid);
}

// 更新總金額顯示
function updateTotalPrice() {
    total = cart.reduce((sum, item) => sum + item.price * item.quantity, 0);
    $("#totalPrice").text("總金額：$" + total);
}

// 載入購物車顯示
function loadCart() {
    cart = getCart();
    const tbody = $("#cartTable tbody");
    tbody.empty();

    if (cart.length === 0) {
        tbody.append("<tr><td colspan='5'>購物車是空的</td></tr>");
        $("#totalPrice").text("總金額：$0");
        return;
    }

    cart.forEach(item => {
        const row = `
            <tr>
                <td>${item.id}</td>
                <td>${item.name}</td>
                <td>$${item.price}</td>
                <td>${item.quantity}</td>
                <td><button class="update" data-pid="${item.id}"> 修改</button></td>
                <td><button class="remove" data-pid="${item.id}"> 刪除</button></td>
            </tr>
            `;
        tbody.append(row);
    });

    updateTotalPrice();
}


//建立add porder json檔
function createFullOrderObject() {
    const cart = getCart(); // 取購物車資料
    const dateTime = new Date().toISOString();
    const remarkStr = $("#remarkInput").val();

    const fullOrder = {
        member: { id: member.id },
        totalPrice: total,
        dateTime: dateTime,
        remark: remarkStr,
        details: cart.map(item => ({
            product: { id: item.id },
            unitPrice: item.price,
            quantity: item.quantity
        }))
    };

    return fullOrder;
}


$(document).ready(function () {
    // 檢查登入狀態
    const loginData = sessionStorage.getItem("loginMember");
    if (!loginData) {
        alert("請先登入！");
        window.location.href = "login.html";
        return;
    }
    //顯示登入者姓名
    member = JSON.parse(loginData);
    $("#memberName").text(`${member.name} 歡迎光臨！`);

    // 登出功能
    $("#logoutBtn").click(() => {
        if (confirm("確定要登出嗎？")) {
            sessionStorage.removeItem("loginMember");
            alert("您已登出！");
            window.location.href = "login.html";
        }
    });
    //購物車顯示
    loadCart();

    // 修改數量
    $(document).on("click", ".update", function () {
        const pid = $(this).data("pid");
        let item = getItemById(pid);

        if (!item) {
            alert("找不到該商品！");
            return;
        }

        const newQtyStr = prompt(`請輸入 ${item.name} 的新數量：`, item.quantity);
        if (newQtyStr === null) return; // 取消

        const newQty = parseInt(newQtyStr);
        if (isNaN(newQty) || newQty <= 0) {
            alert("請輸入有效的正整數！");
            return;
        }

        item.quantity = newQty;
        saveCart(cart);
        loadCart();
    });

    // 刪除商品
    $(document).on("click", ".remove", function () {
        const pid = $(this).data("pid");
        if (confirm(`確定要刪除商品 ${pid} 嗎？`)) {
            let newCart = cart.filter(item => item.id !== pid);
            saveCart(newCart);
            loadCart();
        }
    });

    // 送單
    $("#savePorder").click(function () {
        if (cart.length === 0) {
            alert("購物車是空的，無法送單！");
            return;
        }
        var fullOrder = createFullOrderObject();
        $.ajax({
            method: "POST",
            url: "http://localhost:8080/api/porder",
            contentType: "application/json",
            data: JSON.stringify(fullOrder),
            success: function (res) {
                alert("訂單送出成功！");
                $("#remarkInput").val("");
                sessionStorage.removeItem("cart");
                loadCart();

            },
            error: function () {
                alert("訂單送出失敗！");
            }
        });

    });

    // 清空購物車
    $("#clearCart").click(function () {
        if (confirm("確定要清空購物車？")) {
            sessionStorage.removeItem("cart");
            loadCart();
        }
    });
});