package com.example.demo.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dao.CategoryDAO;
import com.example.demo.model.Category;
import com.example.demo.service.CategoryService;
import com.example.demo.util.IdGenerator;

@Service
public class CategoryServiceImpl implements CategoryService {

    private static final Logger log = LoggerFactory.getLogger(CategoryServiceImpl.class);

    private final CategoryDAO categoryDAO;
    private final IdGenerator idGenerator;

    public CategoryServiceImpl(CategoryDAO categoryDAO, IdGenerator idGenerator) {
        this.categoryDAO = categoryDAO;
        this.idGenerator = idGenerator;
    }

    @Override
    @Transactional
    public boolean addCategory(Category category) {
        try {
            category.setId(idGenerator.getNewCategoryId());
            categoryDAO.save(category);
            log.info("成功新增分類：{}", category.getId());
            return true;
        } catch (Exception e) {
            log.error("新增分類時發生錯誤", e);
            return false;
        }
    }

    @Override
    public List<Category> getAllCategory() {
        return categoryDAO.findAll();
    }

    @Override
    public Category getCategoryById(String id) {
        return categoryDAO.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public boolean updateCategory(String id, Category category) {
        return categoryDAO.findById(id).map(exist -> {
            category.setId(id);
            categoryDAO.save(category);
            log.info("成功更新分類資料：{}", id);
            return true;
        }).orElseGet(()->{
        	log.warn("更新失敗，找不到分類 ID:{}", id);
        	return false;
        });
    }

    @Override
    @Transactional
    public boolean deleteCategory(String id) {
        return categoryDAO.findById(id).map(exist -> {
            categoryDAO.deleteById(id);
            log.info("成功刪除分類：{}", id);
            return true;
        }).orElseGet(() -> {
            log.warn("刪除失敗，找不到分類 ID：{}", id);
            return false;
        });
    }
}
