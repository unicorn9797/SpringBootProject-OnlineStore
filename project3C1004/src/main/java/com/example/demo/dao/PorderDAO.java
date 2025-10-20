package com.example.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Porder;

public interface PorderDAO extends JpaRepository<Porder, String>{

}
