package com.example.demo.init;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.example.demo.model.Employee;
import com.example.demo.model.enums.EmployeeRole;
import com.example.demo.service.impl.EmployeeServiceImpl;

@Component
public class DataInitializer {

    @Autowired
    private EmployeeServiceImpl employeeServiceImpl;

    @PostConstruct
    public void init() {
        // 檢查是否已存在，避免重複新增
        Employee empAdmin = employeeServiceImpl.getEmployeeByUsername("admin");
        if (empAdmin == null) {
            Employee admin = new Employee("E20251015-001", "管理員", "admin", "1234", EmployeeRole.ADMIN);
            employeeServiceImpl.addEmployee(admin); // 自動加密密碼存入 DB
            System.out.println("初始化員工帳號完成: admin / 1234");
        }
        
        Employee empManager = employeeServiceImpl.getEmployeeByUsername("manager");
        if (empManager == null) {
            Employee admin = new Employee("E20251015-002", "小主管", "manager", "1234", EmployeeRole.MANAGER);
            employeeServiceImpl.addEmployee(admin); // 自動加密密碼存入 DB
            System.out.println("初始化員工帳號完成: manager / 1234");
        }
        
        Employee emp = employeeServiceImpl.getEmployeeByUsername("staff");
        if (emp == null) {
            Employee admin = new Employee("E20251015-003", "一般員工", "staff", "1234", EmployeeRole.STAFF);
            employeeServiceImpl.addEmployee(admin); // 自動加密密碼存入 DB
            System.out.println("初始化員工帳號完成: stafff / 1234");
        }
    }
}
