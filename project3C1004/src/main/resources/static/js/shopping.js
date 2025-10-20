var cateJson = [];
//取得category
function getCateJson() {
    $.ajax({
        method: "GET",
        url: "http://localhost:8080/api/category",
        dataType: "json",
        statusCode: {
            200: function (data) {
                cateJson = data;
                fillCategorySelect();
            },
            204: function () {
                alert("目前沒有商品分類！");
            }
        },
        error: function () {
            alert("取得分類資料失敗！");
        }
    });
}
//顯示category清單
function fillCategorySelect() {
    $("#category").empty().append('<option value="">請選擇</option>');
    $.each(cateJson, function (i, ele) {
        $("#category").append($("<option></option>").val(ele.id).text(ele.name));
    });
}

$(document).ready(function () {
    //確認->取得session登入者
    const loginData = sessionStorage.getItem("loginMember");
    if (!loginData) {
        alert("請先登入！");
        window.location.href = "login.html";
        return;
    }
    //顯示登入者名字
    const member = JSON.parse(loginData);
    $("#memberName").text(`${member.name} 歡迎光臨！`);
    //登入 按鈕事件
    $("#logoutBtn").click(() => {
        if (confirm("確定要登出嗎？")) {
            sessionStorage.removeItem("loginMember");
            alert("您已登出！");
            window.location.href = "login.html";
        }
    });
    //向後台要category值
    getCateJson();

    //category清單 選取事件
    $("#category").on("change", function () {
        var cSelected = $("#category").val();
        $("#showProductName").empty();

        if (cSelected === "") {
            $("#showProductName").append("<li>請先選擇商品種類</li>");
            return;
        }

        var category = cateJson.find(c => c.id == cSelected);
        if (!category || !category.products || category.products.length === 0) {
            $("#showProductName").append("<li>此分類目前沒有商品</li>");
            return;
        }

        $.each(category.products, function (i, prod) {
            var li = $("<li></li>").text(prod.name)
                .attr("data-id", prod.id)
                .attr("data-cid", cSelected);
            $("#showProductName").append(li);
        });
    });

    //產品清單 選取事件
    $(document).on("click", "#showProductName li", function () {
        var pid = $(this).attr("data-id");
        var cid = $(this).attr("data-cid");

        var category = cateJson.find(c => c.id == cid);
        var product = category.products.find(p => p.id == pid);

        if (product) {
            $("#productDetail").html(`
                    <div id="productImage">
                    <img src="${product.imageUrl}" alt="${product.name}">
                    </div>
                    <div id="productInfo">
                    <h3>${product.name}</h3>
                    <p><strong>產品代號：</strong>${product.id}</p>
                    <p><strong>產品規格：</strong>${product.specification}</p>
                    <p><strong>價格：</strong>$${product.price}</p>

                    <form id="buyForm" data-id="${product.id}" data-cid="${cid}">
                    <label>購買數量：
                    <input type="number" id="quantity" name="quantity" min="1" max="99" value="1">
                    </label>
                    <input type="submit" value="加入購物車">
                    </form>
                    </div>
                `);

        }
    });

    //js內產生的按鈕事件
    $(document).on("submit", "#buyForm", function (e) {
        e.preventDefault();

        var pid = $(this).attr("data-id");
        var cid = $(this).attr("data-cid");
        var qty = parseInt($("#quantity").val(), 10);

        var category = cateJson.find(c => c.id == cid);
        var product = category.products.find(p => p.id == pid);

        if (!product) {
            alert("找不到商品資料！");
            return;
        }

        let cart = JSON.parse(sessionStorage.getItem("cart")) || [];
        let existing = cart.find(item => item.id == pid);
        if (existing) {
            existing.quantity += qty;
        } else {
            cart.push({ id: pid, name: product.name, quantity: qty, price: product.price });
        }

        sessionStorage.setItem("cart", JSON.stringify(cart));
        alert("商品已加入購物車！");
    });


});