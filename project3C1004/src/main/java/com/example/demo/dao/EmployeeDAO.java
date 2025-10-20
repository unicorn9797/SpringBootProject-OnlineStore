package com.example.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Employee;

public interface EmployeeDAO extends JpaRepository<Employee, String>{

	Employee findByUsername(String username);
}
