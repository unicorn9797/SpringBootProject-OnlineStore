package com.example.demo.service;

import java.util.List;

import com.example.demo.model.Employee;

public interface EmployeeService  {
	//create
	public boolean addEmployee(Employee employee);
	//read
	public List<Employee> getAllEmployee();
	public Employee getEmployeeById(String id);
	public Employee getEmployeeByUsername(String username);
	//update
	public boolean updateEmployee(String id, Employee employee);
	//delete
	public boolean deleteEmployee(String id);
	//login
	public boolean validateLogin(String username, String password);
}
