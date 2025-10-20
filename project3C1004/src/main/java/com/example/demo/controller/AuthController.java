package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.dto.EmployeeDTO;
import com.example.demo.model.Employee;
import com.example.demo.service.impl.EmployeeServiceImpl;
import com.example.demo.util.JwtUtil;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private EmployeeServiceImpl employeeServiceImpl;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String username, @RequestParam String password) {
        if (employeeServiceImpl.validateLogin(username, password)) {
            Employee employee = employeeServiceImpl.getEmployeeByUsername(username);
            if (employee == null) {
                return ResponseEntity.status(404).body("找不到使用者資料");
            }

            String token = JwtUtil.generateToken(username);

            // 使用 DTO（不含密碼）
            EmployeeDTO dto = new EmployeeDTO(
                employee.getId(),
                employee.getName(),
                employee.getUsername(),
                employee.getRole()
            );

            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("employee", dto);

            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(401).body("帳號或密碼錯誤");
        }
    }
    
    @PostMapping("/validate/{id}")
	public ResponseEntity<?> validateEmployeePassword(@PathVariable String id, @RequestBody Map<String, String> body)
	{
    	String password = body.get("password");
		Employee temp = employeeServiceImpl.getEmployeeById(id);
		if(temp == null)
			return ResponseEntity.badRequest().build();
		else
		{
			if(employeeServiceImpl.validateLogin(temp.getUsername(), password))
			{
				return ResponseEntity.ok(temp);
			}
			else
				return ResponseEntity.badRequest().build();
		}
	}
}
