package com.example.demo.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class IdGenerator {
	
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private String generateId(IdType type)
	{
	    String datePart = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
	    String sql = String.format(
	        "SELECT %s FROM %s WHERE %s LIKE ? ORDER BY %s DESC LIMIT 1",
	        type.getColumnName(), type.getTableName(), type.getColumnName(), type.getColumnName()
	    );

	    String lastId = null;
	    try
	    {
	    	lastId = jdbcTemplate.queryForObject(sql, String.class, type.getPrefix() + datePart + "%");
	    } catch (EmptyResultDataAccessException e) {}

	    int next = 1;
	    if (lastId != null)
	    {
	        next = Integer.parseInt(lastId.substring(lastId.length() - 3)) + 1;
	    }

	    return String.format("%s%s-%03d", type.getPrefix(), datePart, next);
	}	
	
	public String getNewCategoryId()
	{
		return generateId(IdType.CATEGORY);
	}
	
	public String getNewEmployeeId()
	{
		return generateId(IdType.EMPLOYEE);
	}
	
	public String getNewMemberId()
	{
		return generateId(IdType.MEMBER);
	}
	
	public String getNewPorderId()
	{
		return generateId(IdType.PORDER);
	}
	
	public String getNewPorderDetailId()
	{
		return generateId(IdType.PORDER_DETAIL);
	}
	
	public String getNewProductId()
	{
		return generateId(IdType.PRODUCT);
	}
	
}
