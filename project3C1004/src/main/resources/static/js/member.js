const loginData = sessionStorage.getItem("loginMember");
const member = JSON.parse(loginData);
//已登入者資料填入(密碼不顯露，直接修改)
function loginText() {
    if (member) {
        $("#uname").val(member.name);
        $("#uphone").val(member.phone);
        $("#upassword").val(member.password);
        $("#uaddress").val(member.address);
        $("#uemail").val(member.email);
        return true;
    }
    return false;
}
//確認資料正確性
function validateForm() {
    const name = $("#uname").val().trim();
    const phone = $("#uphone").val().trim();
    const password = $("#upassword").val().trim();
    const address = $("#uaddress").val().trim();
    const email = $("#uemail").val().trim();
    const errorBox = $("#errorBox");
    errorBox.text("");

    if (!name) {
        errorBox.text("請輸入姓名！");
        return false;
    }
    if (!/^[0-9]{10}$/.test(phone)) {
        errorBox.text("請輸入正確的手機號碼（10位數字）！");
        return false;
    }
    if (password.length < 4) {
        errorBox.text("密碼至少需 4 位數！");
        return false;
    }
    if (!address) {
        errorBox.text("請輸入地址！");
        return false;
    }
    const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailPattern.test(email)) {
        errorBox.text("請輸入有效的 Email！");
        return false;
    }
    return true;
}
//呼叫後端 新增或修改member
function sendMember(memObj) {
    if (loginText()) {
        $.ajax({
            method: "PUT",
            url: "http://localhost:8080/api/member/" + memObj.id,
            contentType: "application/json",
            dataType: "json",
            data: JSON.stringify(memObj),
            success: (memRes) => {
                alert("修改成功！");
                sessionStorage.removeItem("loginMember");
                alert("請重新登入！");
                window.location.href = "login.html";
            },
			error: (xhr, status, error) => {
			        if (xhr.status === 401) {
			            alert("修改失敗，手機號碼已被使用");
			        } else {
			            alert("修改失敗：" + (xhr.responseText || error));
			        }
			    }
        });
    } else {
        $.ajax({
            method: "POST",
            url: "http://localhost:8080/api/member",
            contentType: "application/json",
            dataType: "json",
            data: JSON.stringify(memObj),
            success: (memRes) => {
                alert("新增成功！");
                window.location.href = "login.html";
            },
            error: (xhr, status, error) => {
                alert("新增失敗：" + (xhr.responseText || error));
            }
        });
    }
}

$(document).ready(() => {
	//輸入已登入者資訊
	    loginText();
    
    //送出 按鈕事件
    $("#send_btn").click(() => {
        if (validateForm()) {
            const memObj = {
                id: member?.id,
                name: $("#uname").val().trim(),
                password: $("#upassword").val().trim(),
                phone: $("#uphone").val().trim(),
                address: $("#uaddress").val().trim(),
                email: $("#uemail").val().trim(),
                status: "ACTIVE"
            };
            sendMember(memObj);
        } else {
            alert("資料有錯誤，請檢查");
        }
    });
    //返回 按鈕事件(未登入回login，已登入回menu)
    $("#back_btn").click(() => {
        loginText()? window.location.href = "menu.html" : window.location.href = "login.html";
    });
});