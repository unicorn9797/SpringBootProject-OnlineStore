package com.example.demo.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dao.EmployeeDAO;
import com.example.demo.model.Employee;
import com.example.demo.service.EmployeeService;
import com.example.demo.util.IdGenerator;

@Service
public class EmployeeServiceImpl implements EmployeeService{
	
	private static final Logger log = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    private final EmployeeDAO employeeDAO;
    private final IdGenerator idGenerator;    
    
 // 🔹 建立 BCrypt 加密器
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    
	public EmployeeServiceImpl(EmployeeDAO employeeDAO, IdGenerator idGenerator) {
		
		this.employeeDAO = employeeDAO;
		this.idGenerator = idGenerator;
	}
	
	
	// 新增員工（僅 ADMIN 可用）
	@Override
	@Transactional
	public boolean addEmployee(Employee employee) {
		Employee temp = employeeDAO.findByUsername(employee.getUsername());
		if(temp == null)
		{	
			try {			
	            employee.setId(idGenerator.getNewEmployeeId());
	            employee.setPassword(passwordEncoder.encode(employee.getPassword()));
	            employeeDAO.save(employee);
	            log.info("成功新增員工：{}", employee.getId());
	            return true;
	        } catch (Exception e) {
	            log.error("新增員工時發生錯誤", e);
	            return false;
	        }
		}
		else
		{
			log.error("員工帳號名重複");
			return false;
		}
	}

	@Override
	public List<Employee> getAllEmployee() {
		
		return employeeDAO.findAll();
	}

	@Override
	public Employee getEmployeeById(String id) {
		
		return employeeDAO.findById(id).orElse(null);
	}
	
	@Override
	public Employee getEmployeeByUsername(String username) {
		
		return employeeDAO.findByUsername(username);
	}

	@Override
	@Transactional	
	public boolean updateEmployee(String id, Employee employee)
	{
	    return employeeDAO.findById(id).map(exist -> {
	    	// 員工修改自己的資料
	        boolean isSelf = id.equals(employee.getId());
	        Employee temp = employeeDAO.findByUsername(employee.getUsername());
	        if(temp != null/*希望改的username已經有了*/ && !(temp.getUsername().equals(exist.getUsername())/*排除自己的username被找到的情況*/) )
	        {
	        	log.error("員工修改帳號名稱不可重複");
	        	return false;
	        }
	        if(isSelf)
	        {	            
	            if(employee.getName() != null && !employee.getName().isEmpty())
	                exist.setName(employee.getName());
	            if(employee.getUsername() != null && !employee.getUsername().isEmpty())
	                exist.setUsername(employee.getUsername());
	            if(employee.getPassword() != null && !employee.getPassword().isEmpty())
	                exist.setPassword(passwordEncoder.encode(employee.getPassword()));	            
	        }	        
	        exist.setRole(employee.getRole());
	         
	        
	        employeeDAO.save(exist);
	        log.info("成功更新員工資料：{}", id);
	        return true;
	    }).orElseGet(() -> {
	        log.warn("更新失敗，找不到員工 ID:{}", id);
	        return false;
	    });
	}

	@Override
	@Transactional
	public boolean deleteEmployee(String id) {
		return employeeDAO.findById(id).map(exist -> {
			employeeDAO.deleteById(id);
			log.info("成功刪除員工：{}", id);
            return true;
        }).orElseGet(() -> {
            log.warn("刪除失敗，找不到員工 ID：{}", id);
            return false;
        });
	}

	@Override
	public boolean validateLogin(String username, String rawPassword) {
		Employee temp = employeeDAO.findByUsername(username);
		if(temp != null && passwordEncoder.matches(rawPassword, temp.getPassword()))
		{
			return true;
		}		
		return false;
	}



	

}
