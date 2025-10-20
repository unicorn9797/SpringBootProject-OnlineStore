package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.ProductDTO;
import com.example.demo.model.Product;
import com.example.demo.service.impl.ProductServiceImpl;

@RestController
@RequestMapping("/api/product")
@CrossOrigin(origins = "*") // ⚠️ 如果前端用不同 port，例如 8081，要開放 CORS
public class ProductController {
	
	@Autowired
	ProductServiceImpl productServiceImpl;
	
	@PostMapping
	public ResponseEntity<?> addProduct(@RequestBody Product product)
	{
		if(productServiceImpl.addProduct(product))
			return ResponseEntity.ok(product);
		else
			return ResponseEntity.internalServerError().body("新增商品失敗");		
	}
	
	@GetMapping
	public ResponseEntity<?> getAllProduct()
	{
		List<ProductDTO> tempList = productServiceImpl.getAllProduct();
		if(tempList.size() == 0)
			return ResponseEntity.noContent().build();
		else
			return ResponseEntity.ok(tempList);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getProductById(@PathVariable String id)
	{
		Product temp = productServiceImpl.getProductById(id);
		if(temp != null)
			return ResponseEntity.ok(temp);
		else
			return ResponseEntity.notFound().build();
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> updateProduct(@PathVariable String id, @RequestBody Product product)
	{		
		if(productServiceImpl.updateProduct(id, product))		
			return ResponseEntity.ok("產品" + id + "修改成功");		
		else
			return ResponseEntity.notFound().build();		
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteProduct(@PathVariable String id)
	{		
		if(productServiceImpl.deleteProduct(id))		
			return ResponseEntity.ok("產品已刪除:" + id);		
		else
			return ResponseEntity.notFound().build();
	}
}
