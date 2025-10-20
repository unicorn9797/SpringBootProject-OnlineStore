package com.example.demo.dto;

import com.example.demo.model.enums.EmployeeRole;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class EmployeeDTO {
	private String id;
	private String name;
	private String username;
	private EmployeeRole role; 
}
