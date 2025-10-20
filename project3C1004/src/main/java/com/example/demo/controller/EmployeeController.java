package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Employee;
import com.example.demo.service.impl.EmployeeServiceImpl;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {
	
	@Autowired
	EmployeeServiceImpl employeeServiceImpl;
	
	@PostMapping
	public ResponseEntity<?> addEmployee(@RequestBody Employee employee)
	{
		if(employeeServiceImpl.addEmployee(employee))
			return ResponseEntity.ok(employee);
		else	
			return ResponseEntity.internalServerError().body("新增員工失敗" );
		
	}
	
	
	
	@GetMapping
	public ResponseEntity<?> getAllEmployee()
	{
		List<Employee> tempList = employeeServiceImpl.getAllEmployee();
		
		if(tempList.size() == 0)
			return ResponseEntity.noContent().build();
		else
			return ResponseEntity.ok(tempList);		
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getEmployeeById(@PathVariable String id)
	{
		Employee temp = employeeServiceImpl.getEmployeeById(id);
		if(temp != null)
			return ResponseEntity.ok(temp);
		else
			return ResponseEntity.notFound().build();
	}
	
	@GetMapping("/checkusername/{username}")
	public ResponseEntity<?> getEmployeeByUserName(@PathVariable String username)
	{
		Employee temp = employeeServiceImpl.getEmployeeByUsername(username);
		if(temp != null)
			return ResponseEntity.ok(temp);
		else
			return ResponseEntity.noContent().build();
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> updateEmployee(@PathVariable String id,@RequestBody Employee employee)
	{

	    boolean success = employeeServiceImpl.updateEmployee(id, employee);
	    
	    if (success)
	        return ResponseEntity.ok("員工資料更新成功");
	    else
	        return ResponseEntity.status(403).body("更新失敗：無權限或員工不存在");
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteEmployee(@PathVariable String id)
	{
		if(employeeServiceImpl.deleteEmployee(id))		
			return ResponseEntity.ok("員工已刪除:" +id);		
		else
			return ResponseEntity.notFound().build();
	}
}
