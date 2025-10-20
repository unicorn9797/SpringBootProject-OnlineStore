package com.example.demo.service;

import java.util.List;

import com.example.demo.model.Category;

public interface CategoryService {
	//create
	public boolean addCategory(Category category);
	//read
	public List<Category> getAllCategory();
	public Category getCategoryById(String id);
	//update
	public boolean updateCategory(String id, Category category);
	//delete
	public boolean deleteCategory(String id);
}
