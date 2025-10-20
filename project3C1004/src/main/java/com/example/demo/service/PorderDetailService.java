package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.PorderDetailDTO;
import com.example.demo.model.PorderDetail;

public interface PorderDetailService {
	//create
	public boolean addPorderDetail(PorderDetail porderDetail);
	//read
	public List<PorderDetailDTO> getAllPorderDetail();
	public PorderDetail getPorderDetailById(String id);
	public List<PorderDetailDTO> getPorderDetailByPorderId(String id);
	//update
	public boolean updatePorderDetail(String id, PorderDetail porderDetail);
	//delete
	public boolean deletePorderDetail(String id);
}
