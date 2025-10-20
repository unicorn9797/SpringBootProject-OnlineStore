// management.js
$(document).ready(function() {

    // 🔹 取得 JWT token
    let token = localStorage.getItem("token");
    const currentName = localStorage.getItem("name");

    if (!token) {
        // 沒登入就跳回 employeelogin.html
        window.location.href = "employeelogin.html";
        return;
    }

    // 顯示登入者
    $("#currentUser").text(`登入者: ${currentName}`);

    // -------------------- 共用 AJAX --------------------
    function api(endpoint, method = "GET", data = null, callback) {
        $.ajax({
            url: "/api" + endpoint,
            method: method,
            data: data ? JSON.stringify(data) : null,
            contentType: "application/json",
            headers: {
                "Authorization": "Bearer " + token
            },
            success: function(res) { callback(res); },
            error: function(xhr) {
                console.error("API call failed:", xhr.status, xhr.responseText);
                callback(null);
            }
        });
    }

    // -------------------- 導覽卡切換 --------------------
    $("nav button").click(function() {
        const id = $(this).data("card");
        $(".card").removeClass("active");
        $("#" + id).addClass("active");
        $("nav button").removeClass("active");
        $(this).addClass("active");
    });

    // -------------------- 會員 --------------------
	function renderMembers() {
	    const currentUserRole = localStorage.getItem("role");

	    api("/member", "GET", null, function(members) {
	        if (!members) return;
	        let tbody = $("#memberTable tbody").empty();
	        members.forEach(m => {
	            // 不允許員工編輯會員欄位（以避免員工修改會員資料的情況）
	            // 只有 ADMIN 可以刪除會員（如果你不想任何人刪都可註解掉 deleteBtn）
	            let deleteBtn = currentUserRole === "ADMIN" ? `<button onclick="remove('member','${m.id}')">刪除</button>` : '';

	            tbody.append(`
	                <tr>
	                    <td>${m.id}</td>
	                    <td>${m.name}</td>
	                    <td>${m.email}</td>
	                    <td>${m.phone}</td>
	                    <td>${m.address}</td>
	                    <td>${deleteBtn}</td>
	                </tr>
	            `);
	        });
	        updateMemberSelects();
	    });
	}
 

    // -------------------- 產品 --------------------
	function renderProducts() {
	    const currentUserRole = localStorage.getItem("role");
	    api("/product", "GET", null, function(products) {
	        if (!products) return;
	        let tbody = $("#productTable tbody").empty();
	        products.forEach(p => {
	            let catName = p.categoryName;

	            // 權限：MANAGER & ADMIN 可以開編輯 modal；只有 ADMIN 可刪除
	            const canModifyProduct = (currentUserRole === "ADMIN" || currentUserRole === "MANAGER");
	            const canDeleteProduct = (currentUserRole === "ADMIN");

	            const editBtn = canModifyProduct
	                ? `<button onclick="openEditModal('${p.id}', '${escapeHtmlForAttr(p.name)}', '${escapeHtmlForAttr(catName)}', '${p.price}', \`${p.specification || ''}\`)">修改</button>`
	                : '';

	            const deleteBtn = canDeleteProduct ? `<button onclick="remove('product','${p.id}')">刪除</button>` : '';

	            // 若沒權限修改，把 name/price 改為不可編輯
	            const nameCell = canModifyProduct
	                ? `<td contenteditable onblur="edit('product','${p.id}','name',this.innerText)">${p.name}</td>`
	                : `<td>${p.name}</td>`;

	            const priceCell = canModifyProduct
	                ? `<td contenteditable onblur="edit('product','${p.id}','price',this.innerText)">${p.price}</td>`
	                : `<td>${p.price}</td>`;

	            tbody.append(`
	                <tr>
	                    <td>${p.id}</td>
	                    ${nameCell}
	                    <td>${catName}</td>
	                    ${priceCell}
	                    <td>
	                        ${editBtn}
	                        ${deleteBtn}
	                    </td>
	                </tr>
	            `);
	        });
	    });
	}
	
	$("#addCategoryBtn").click(function(){
		const currentUserRole = localStorage.getItem("role");
		if(!(currentUserRole ==="ADMIN" || currentUserRole ==="MANAGER"))
		{
			alert("❌ 僅 ADMIN 與 MANAGER 可新增分類");
			return;		
		}
		if(isEmpty("cName"))
		{
			alert("❌ 名字不可為空");
			return;
		}
		let data = { "name" : $("#cName").val().trim()};
		api("/category", "POST", data, function(res){
			if(res)
			{
				alert("✅ 分類新增成功");
		        $("#cName").val("");
			}
			else
			{
				alert("❌ 分類新增失敗");
			}
			
			});
	});

	$("#addProductBtn").click(function() {
	    const currentUserRole = localStorage.getItem("role");
	    if (!(currentUserRole === "ADMIN" || currentUserRole === "MANAGER")) {
	        alert("❌ 僅 ADMIN 與 MANAGER 可新增產品");
	        return;
	    }
		if( isEmpty("pName") || isEmpty("pPrice"))
		{
			alert("❌ 名字與價格不可為空!!");	
			return;	
		}
	    let data = {
	        name: $("#pName").val(),
	        price: $("#pPrice").val(),
	        category: {"name" : $("#pCategory").val()}
	    };
	    api("/product", "POST", data, function(res) { 
			if(res)
			{
				alert("新增產品成功");
				$("#pName").val("");
				$("#pPrice").val("");
				renderProducts();				
			}	
			 });
	});

	/* helper: escape quotes for inline-attr usage (防止 product name 裡有單雙引號打破 HTML) */
	function escapeHtmlForAttr(str) {
	    if (str == null) return '';
	    return String(str).replace(/'/g, "\\'").replace(/"/g, '&quot;');
	}
	
	
	//修改產品
	window.openEditModal = function(id, name, category, price, spec) {
	    $("#editProductId").val(id);
	    $("#editProductName").val(name);
	    $("#editProductPrice").val(price);
	    $("#editProductSpec").val(spec);

	    // 載入分類清單
	    api("/category", "GET", null, function(categories) {
	        let sel = $("#editProductCategory").empty();
	        categories.forEach(c => {
	            sel.append(`<option value="${c.id}" ${c.name === category ? "selected" : ""}>${c.name}</option>`);
	        });
	        $("#editProductModal").show();
	    });
	};

	window.closeEditModal = function() {
	    $("#editProductModal").hide();
	};

	window.updateProduct = function() {
	    let id = $("#editProductId").val();
		if(isEmpty("editProductName") || isEmpty("editProductPrice"))
		{
			alert("❌ 產品名稱與價格不可為空");
			return;
		}
	    let data = {
	        name: $("#editProductName").val(),
	        price: $("#editProductPrice").val(),
	        specification: $("#editProductSpec").val(),
	        category:{ "id" : $("#editProductCategory").val()}
	    };

	    api("/product/" + id, "PUT", data, function(res) {
	        if (res) {
	            alert("✅ 修改成功");
	            closeEditModal();
	            renderProducts();
	        } else {
	            alert("❌ 修改失敗");
	        }
	    });
	};

    // -------------------- 訂單 --------------------
	function renderOrders() {
	    const currentUserRole = localStorage.getItem("role");
	    api("/porder", "GET", null, function(orders) {
	        if (!orders) return;
	        let tbody = $("#orderTable tbody").empty();
	        orders.forEach(o => {
	            // 權限：ADMIN / MANAGER 可以新增；僅 ADMIN 可刪除
	            const canDeleteOrder = currentUserRole === "ADMIN";
	            const deleteBtn = canDeleteOrder ? `<button onclick="remove('order','${o.id}')">刪除</button>` : '';
	            
				tbody.append(`
				  <tr>
				    <td>${o.id}</td>
				    <td>${o.memberName}</td>
				    <td>${o.totalPrice}</td>
				    <td>${o.dateTime}</td>
				    <td>
				      <button onclick="viewPorderDetail('${o.id}')">查看明細</button>
				      ${deleteBtn}
				    </td>
				  </tr>
				`);
	        });
	    });
	}
	
	// 🔹 查看訂單明細
	window.viewPorderDetail = function(orderId) {
	    api(`/porderDetail/porder/${orderId}`, "GET", null, function(details) {
	        if (!details || details.length === 0) {
	            alert("找不到訂單明細");
	            return;
	        }

	        // 填入訂單編號
	        $("#porderDetailOrderId").text(orderId);

	        // 清空舊資料
	        let tbody = $("#porderDetailTable tbody").empty();
	        let totalPrice = 0;

	        // 渲染每個明細
	        details.forEach(d => {
	            const subtotal = d.unitPrice * d.quantity;
	            totalPrice += subtotal;

	            tbody.append(`
	                <tr>
	                  <td>${d.productId}</td>
	                  <td>${d.productName}</td>
	                  <td>${d.quantity}</td>
	                  <td>${d.unitPrice}</td>
	                  <td>${subtotal.toFixed(2)}</td>
	                </tr>
	            `);
	        });

	        // 顯示總價
	        $("#porderDetailTotalPrice").text(totalPrice.toFixed(2));

	        // 顯示彈窗
	        $("#porderDetailModal").show();
	    });
	};

	// 🔹 關閉明細視窗
	window.closePorderDetailModal = function() {
	    $("#porderDetailModal").hide();
	};

	


    // -------------------- 員工 --------------------
	function renderEmployees() {
	    const currentUserRole = localStorage.getItem("role");
	    const currentUsername = localStorage.getItem("username");

	    api("/employee", "GET", null, function(employees) {
	        if (!employees) return;
	        let tbody = $("#employeeTable tbody").empty();

	        employees.forEach(e => {
	            let roleCell = e.role;

	            // ADMIN 可以修改角色
	            if(currentUserRole === "ADMIN") {
	                roleCell = `<select id = "roleSelect-${e.id}" onchange="enableRoleUpdateBtn('${e.id}')">
	                                <option ${e.role==='ADMIN'?'selected':''}>ADMIN</option>
	                                <option ${e.role==='MANAGER'?'selected':''}>MANAGER</option>
	                                <option ${e.role==='STAFF'?'selected':''}>STAFF</option>
	                            </select>
	                            <button id="updateRoleBtn-${e.id}" style="display:none;" onclick="updateRole('${e.id}')">確認更新</button>`;
	            }

	            tbody.append(`
	                <tr>
	                    <td>${e.id}</td>
	                    <td>${e.name}</td>
	                    <td>${e.username}</td>
	                    <td>${roleCell}</td>
	                    <td>${currentUserRole === "ADMIN" ? `<button onclick="remove('employee','${e.id}')">刪除</button>` : ''}</td>
	                </tr>
	            `);
	        });
	    });
	}
	
	// 🔹 檢查帳號是否重複（回傳 Promise）
	function checkUsernameDuplicate(username) {
	    return new Promise((resolve, reject) => {
	        api(`/employee/checkusername/${username}`, "GET", null, function(res, status) {
	            if (status === 204 || !res) resolve(false); // 無重複
	            else resolve(true); // 有重複
	        });
	    });
	}
	
	// 顯示角色更新按鈕
	window.enableRoleUpdateBtn = function(empId){
	    $(`#updateRoleBtn-${empId}`).show();
	}

	// 更新角色
	window.updateRole = function(empId){
	    const select = document.getElementById(`roleSelect-${empId}`);
	    if(!select) {
	        alert("找不到角色選單，請刷新頁面");
	        return;
	    }
	    const newRole = select.value;

	    if(!confirm(`確定將此員工角色改為 ${newRole}？`)) return;

	    let data = {	        
	        "role" : newRole,            
	    };

	    api(`/employee/${empId}`, "PUT", data, function(res){
	        if(res){
	            alert("✅ 角色更新成功");
	            renderEmployees();
	        } else {
	            alert("❌ 更新失敗");
	        }
	    });
	}
	
	$("#editSelfBtn").click(function(){
	    const currentUsername = localStorage.getItem("username");
	    const empId = localStorage.getItem("id"); // 確認有存 id
	    api("/employee", "GET", null, function(employees){
	        const me = employees.find(e=> e.username === currentUsername);
	        if(!me) { alert("找不到員工資料"); return; }

	        // 開啟彈窗
	        $("#editEmployeeId").val(me.id);
	        $("#editEmployeeName").val(me.name);
	        $("#editEmployeeUsername").val(me.username);
	        $("#editEmployeeOldPassword").val("");
	        $("#editEmployeePassword").val("");

	        $("#selfEditArea").show();
	        $("#roleEditArea").hide();
	        $("#editEmployeeModal").show();
	    });
	});
	// 開啟彈窗並載入員工資料
	window.openEditEmployeeModal = function(e) {
	    const currentUserRole = localStorage.getItem("role");
	    const currentUsername = localStorage.getItem("username");

	    $("#editEmployeeId").val(e.id);

	    if(e.username === currentUsername) {
	        // 員工自己修改自己的資料
	        $("#selfEditArea").show();
	        $("#roleEditArea").hide();
	        $("#editEmployeeName").val(e.name);
	        $("#editEmployeeUsername").val(e.username);
	        $("#editEmployeeOldPassword").val("");
	        $("#editEmployeePassword").val("");

	    } else if(currentUserRole === "ADMIN") {
	        // ADMIN 修改角色
	        $("#selfEditArea").hide();
	        $("#roleEditArea").show();
	        $("#editEmployeeRole").val(e.role);

	    } else {
	        alert("❌ 你沒有權限修改這個員工");
	        return;
	    }

	    $("#editEmployeeModal").show();
	};
	
	// 關閉彈窗
	window.closeEditEmployeeModal = function() {
	    $("#editEmployeeModal").hide();
	};
	
	
	window.updateEmployee = function() {
	    const currentUserRole = localStorage.getItem("role");
	    const empId = $("#editEmployeeId").val();
	    const username = $("#editEmployeeUsername").val();

	    let data = {};

	    if($("#selfEditArea").is(":visible")) {
	        // 員工自己修改資料
	        const oldPwd = $("#editEmployeeOldPassword").val();
	        if(!oldPwd) {
	            alert("請輸入舊密碼");
	            return;
	        }

	        // 先檢查帳號是否重複（排除自己）
	        checkUsernameDuplicate(username).then(isDuplicate => {
	            if (isDuplicate && username !== localStorage.getItem("username")) {
	                alert("❌ 帳號名稱已存在，請更換！");
	                return;
	            }
			
	            api(`/auth/validate/${empId}`, "POST", { password: oldPwd }, function(validateRes){
	                if(!validateRes) {
	                    alert("❌ 舊密碼驗證失敗");
	                    return;
	                }

	                data.name = $("#editEmployeeName").val();
	                data.username = username;
	                const newPwd = $("#editEmployeePassword").val();
	                if(newPwd) data.password = newPwd;
	                data.role = localStorage.getItem("role");
	                data.id = empId;

	                api(`/employee/${empId}`, "PUT", data, function(res){
	                    if(res) {
	                        alert("✅ 資料更新成功");
	                        closeEditEmployeeModal();
	                        renderEmployees();
	                    } else {
	                        alert("❌ 更新失敗");
	                    }
	                });
	            });
	        });

	    } else if($("#roleEditArea").is(":visible") && currentUserRole === "ADMIN") {
	        // ADMIN 修改角色
	        checkUsernameDuplicate(username).then(isDuplicate => {
	            if (isDuplicate) {
	                alert("❌ 帳號名稱已存在，請更換！");
	                return;
	            }

	            data.name = $("#editEmployeeName").val();
	            data.username = username;
	            data.role = $("#editEmployeeRole").val();

	            api(`/employee/${empId}`, "PUT", data, function(res){
	                if(res) {
	                    alert("✅ 角色更新成功");
	                    closeEditEmployeeModal();
	                    renderEmployees();
	                } else {
	                    alert("❌ 更新失敗");
	                }
	            });
	        });
	    } else {
	        alert("❌ 無權限更新資料");
	        return;
	    }
	};




	// 🔹 只有 ADMIN 才能新增員工
	$("#addEmployeeBtn").click(function() {
	    const currentUserRole = localStorage.getItem("role");
	    if (currentUserRole !== "ADMIN") {
	        alert("❌ 僅 ADMIN 可新增員工");
	        return;
	    }
		
		if(isEmpty("eName") || isEmpty("eUsername") || isEmpty("ePassword"))
		{
			alert("❌ 名字、帳號與密碼不可為空");
			return;	
		}
	    const username = $("#eUsername").val();
	    checkUsernameDuplicate(username).then(isDuplicate => {
	        if (isDuplicate) {
	            alert("❌ 帳號名稱已存在，請更換！");
	            return;
	        }

	        let data = {
	            name: $("#eName").val(),
	            username: username,
	            password: $("#ePassword").val(),
	            role: $("#eRole").val()
	        };

	        api("/employee", "POST", data, function(res) {
	            if (res) {
	                alert("✅ 新增成功");
	                $("#eName").val("");
	                $("#eUsername").val("");
	                $("#ePassword").val("");
	                renderEmployees();
	            } else {
	                alert("❌ 新增失敗");
	            }
	        });
	    });
	});

    // -------------------- 共用 --------------------
    window.remove = function(type, id) {
        if (!confirm("確定要刪除？")) return;
        let endpoint = "";
        switch(type){
            case "member": endpoint="/member/"+id; break;
            case "product": endpoint="/product/"+id; break;
            case "order": endpoint="/porder/"+id; break;
            case "employee": endpoint="/employee/"+id; break;            
        }
        api(endpoint, "DELETE", null, function(res) { renderAll(); });
    };

    window.edit = function(type, id, field, value) {
        let endpoint = "";
        let data = {};
        data[field] = value;
        switch(type){
            case "member": endpoint="/member/"+id; break;
            case "product": endpoint="/product/"+id; break;
            case "order": endpoint="/porder/"+id; break;
            case "employee": endpoint="/employee/"+id; break;            
        }
        api(endpoint, "PUT", data, function(res){ renderAll(); });
    };

    function renderAll(){
        renderMembers();
        renderProducts();
        renderOrders();
        renderEmployees();        
    }

    function updateMemberSelects(){
        let selects = [$("#oMember"), $("#cMember")];
        api("/member", "GET", null, function(members){
            if (!members) return;
            selects.forEach(sel => {
                sel.empty();
                members.forEach(m => sel.append(`<option>${m.name}</option>`));
            });
        });
    }

    function initCategories(){
        api("/category", "GET", null, function(categories){
            if (!categories) return;
            let sel = $("#pCategory");
            sel.empty();
            categories.forEach(c => sel.append(`<option>${c.name}</option>`));
        });
    }
	
	// 🔹 登出功能
	window.logout = function() {
	    if (confirm("確定要登出嗎？")) {
	        localStorage.removeItem("username");
	        localStorage.removeItem("role");
	        localStorage.removeItem("token");
	        // 或直接清空全部：
	        // localStorage.clear();

	        alert("已登出，將返回登入頁");
	        window.location.href = "/employeelogin.html"; // 這裡改成你的登入頁路徑
	    }
	};
	
	//驗證空值
	function isEmpty(input)
	{
		return $("#" + input).val().trim() ==="";
	}

    // 初始化
    renderAll();
    initCategories();
    updateMemberSelects();

});
