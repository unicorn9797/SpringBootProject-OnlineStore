package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.PorderDTO;
import com.example.demo.model.Porder;

public interface PorderService {
	//create
	public boolean addPorder(Porder porder);
	//read
	public List<PorderDTO> getAllPorder();
	public Porder getPorderById(String id);
	//update
	public boolean updatePorder(String id, Porder porder);
	//delete
	public boolean deletePorder(String id);
}
