//呼叫後端 確認帳密
function sendLogin(memObj) {
    $.ajax({
        method: "POST",
        url: "http://localhost:8080/api/member/login",
        dataType: "json",
        contentType: "application/json",
        data: JSON.stringify(memObj),
        success: (memRes) => {
            sessionStorage.setItem("loginMember", JSON.stringify(memRes));
            window.location.href = "menu.html";
        },
        statusCode: {
            404: function () {
                alert("無此會員手機號碼");
            },
            401: function () {
                alert("密碼錯誤");
            }
        }
    });
}

$(document).ready(() => {
    //登入 按鈕事件
    $("#login_btn").click(() => {
        const uPhone = $("#uphone").val().trim();
        const uPassword = $("#upassword").val().trim();

        if (!uPhone || !uPassword) {
            alert("請輸入手機與密碼！");
            return;
        }

        const memObj = { phone: uPhone, password: uPassword };
        sendLogin(memObj);
    });

    //註冊 按鈕事件
    $(".add_btn").on("click", () => {
        location.href = "member.html";
    });
});