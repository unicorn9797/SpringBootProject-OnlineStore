package com.example.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;


import com.example.demo.model.Category;


public interface CategoryDAO extends JpaRepository<Category, String>{
	
	public Category findByName(String name);
}
