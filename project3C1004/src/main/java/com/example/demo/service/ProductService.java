package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.ProductDTO;
import com.example.demo.model.Product;

public interface ProductService {
	//create
	public boolean addProduct(Product product);
	//read
	public List<ProductDTO> getAllProduct();
	public Product getProductById(String id);
	//update
	public boolean updateProduct(String id, Product product);
	//delete
	public boolean deleteProduct(String id);
}
