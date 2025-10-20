
function loginCheck() {
    const loginData = sessionStorage.getItem("loginMember");

    if (!loginData) {
        alert("請先登入！");
        window.location.href = "login.html";
        return;
    }

    const member = JSON.parse(loginData);
    $("#memberName").text(`${member.name} 歡迎光臨！`);
}

$(document).ready(() => {
    //確認是否有登入者
    loginCheck();

    $("#shopping").click(() => window.location.href = "shopping.html");
    $("#member").click(() => window.location.href = "member.html");
    $("#porder").click(() => window.location.href = "porder.html");

    //登出 按鈕事件
    $("#logoutBtn").click(() => {
        if (confirm("確定要登出嗎？")) {
            sessionStorage.removeItem("loginMember");
            alert("您已登出！");
            window.location.href = "login.html";
        }
    });
});