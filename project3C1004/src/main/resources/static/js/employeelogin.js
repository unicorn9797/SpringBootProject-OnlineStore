$(document).ready(function(){
    $("#loginBtn").click(function(){
        const username = $("#username").val().trim();
        const password = $("#password").val().trim();

        if(!username || !password){
            alert("請輸入帳號密碼");
            return;
        }

        $.ajax({
            url: "/api/auth/login",
            method: "POST",
            data: { username: username, password: password },
            success: function(res){
                if(res.token){
                    // 存 token 和 username
					const emp = res.employee;
					localStorage.setItem("token", res.token);
					localStorage.setItem("name", emp.name);
					localStorage.setItem("username", username);
					localStorage.setItem("role", emp.role);
					localStorage.setItem("id", emp.id);
                    // 跳轉管理頁
                    window.location.href = "management.html";
                } else {
                    alert("登入失敗");
                }
            },
            error: function(xhr){
                if(xhr.status === 401){
                    alert("帳號或密碼錯誤");
                } else {
                    alert("登入錯誤，請稍後再試");
                }
            }
        });
    });
});
