package com.example.demo.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dao.CategoryDAO;
import com.example.demo.dao.ProductDAO;
import com.example.demo.dto.ProductDTO;
import com.example.demo.model.Category;
import com.example.demo.model.Product;
import com.example.demo.service.ProductService;
import com.example.demo.util.IdGenerator;

@Service
public class ProductServiceImpl implements ProductService{
	
	private static final Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);

    private final ProductDAO productDAO;
    private final IdGenerator idGenerator;
    private final CategoryDAO categoryDAO;
    
	
	public ProductServiceImpl(ProductDAO productDAO, IdGenerator idGenerator, CategoryDAO categoryDAO) {
		
		this.productDAO = productDAO;
		this.idGenerator = idGenerator;
		this.categoryDAO = categoryDAO;
	}

	@Override
	@Transactional
	public boolean addProduct(Product product) {
		try {
			product.setId(idGenerator.getNewProductId());
			if(product.getCategory() != null && product.getCategory().getName() != null)
			{
				Category category = categoryDAO.findByName(product.getCategory().getName());
				product.setCategory(category);
			}
			productDAO.save(product);
            log.info("成功新增產品：{}", product.getId());
            return true;
        } catch (Exception e) {
            log.error("新增產品時發生錯誤", e);
            return false;
        }
	}

	@Override
	public List<ProductDTO> getAllProduct() {
		
		return productDAO.findAll().stream().map(p -> new ProductDTO(
				p.getId(),
	            p.getName(),
	            p.getPrice(),
	            p.getCategory() != null ? p.getCategory().getName() : "",
	            p.getImageUrl()
				))
				.collect(Collectors.toList());
	}

	@Override
	public Product getProductById(String id) {
		
		return productDAO.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public boolean updateProduct(String id, Product newData) {
	    return productDAO.findById(id).map(exist -> {
	        exist.setName(newData.getName());
	        exist.setPrice(newData.getPrice());
	        if(newData.getSpecification() != null && !newData.getSpecification().isBlank() )
	        	exist.setSpecification(newData.getSpecification());

	        // 🔹 用 category id查回真正的 Category
	        if (newData.getCategory() != null && newData.getCategory().getId() != null) {
	            Category category = categoryDAO.findById(newData.getCategory().getId()).get();
	                    
	            exist.setCategory(category);
	        }

	        productDAO.save(exist);
	        log.info("✅ 成功更新產品資料：{}", id);
	        return true;
	    }).orElseGet(() -> {
	        log.warn("❌ 更新失敗，找不到產品 ID: {}", id);
	        return false;
	    });
	}

	@Override
	@Transactional
	public boolean deleteProduct(String id) {
		return productDAO.findById(id).map(exist -> {
			productDAO.deleteById(id);
			log.info("成功刪除產品：{}", id);
            return true;
        }).orElseGet(() -> {
            log.warn("刪除失敗，找不到產品 ID：{}", id);
            return false;
        });
	}

}
