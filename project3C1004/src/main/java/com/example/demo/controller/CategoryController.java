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

import com.example.demo.model.Category;
import com.example.demo.service.impl.CategoryServiceImpl;


@RestController
@RequestMapping("/api/category")
public class CategoryController {
	
	@Autowired
	CategoryServiceImpl categoryServiceImpl;	

	
	@PostMapping
	public ResponseEntity<?> addCategory(@RequestBody Category category)
	{
		if(categoryServiceImpl.addCategory(category))			
			return ResponseEntity.ok(category);		
		else		
			return ResponseEntity.internalServerError().body("新增分類失敗");
		
	}
	
	@GetMapping
	public ResponseEntity<?> getAllCategory()
	{
		List<Category> tempList = categoryServiceImpl.getAllCategory();
		if(tempList.size() == 0)
			return ResponseEntity.noContent().build();
		else
			return ResponseEntity.ok(tempList);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getCategoryById(@PathVariable String id)
	{
		Category category = categoryServiceImpl.getCategoryById(id);
		if(category != null)
			return ResponseEntity.ok(category);
		else
			return ResponseEntity.notFound().build();
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> updateCategory(@PathVariable String id, @RequestBody Category category)
	{
		
		if(categoryServiceImpl.updateCategory(id, category))
			return ResponseEntity.ok(category);		
		else
			return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteCategory(@PathVariable String id)
	{		
		if(categoryServiceImpl.deleteCategory(id))					
			return ResponseEntity.ok("分類已刪除:" + id);		
		else
			return ResponseEntity.notFound().build();
	}
}
