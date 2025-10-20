package com.example.demo.util;

public enum IdType {
	 MEMBER("M", "member", "member_id"),
	 EMPLOYEE("E", "employee", "employee_id"),
	 PRODUCT("P", "product", "product_id"),
	 PORDER("PO", "porder", "porder_id"),
	 PORDER_DETAIL("D", "porderdetail", "porderDetail_id"),
	 CATEGORY("C", "category", "category_id");
	
	private final String prefix;
	private final String tableName;
	private final String columnName;
	
	private IdType(String prefix, String tableName, String columnName) {
		this.prefix = prefix;
		this.tableName = tableName;
		this.columnName = columnName;
	}

	public String getPrefix() {
		return prefix;
	}

	public String getTableName() {
		return tableName;
	}

	public String getColumnName() {
		return columnName;
	}
	
	
}
