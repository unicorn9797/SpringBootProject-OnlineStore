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

import com.example.demo.dto.PorderDetailDTO;
import com.example.demo.model.PorderDetail;

import com.example.demo.service.impl.PorderDetailServiceImpl;

@CrossOrigin
@RestController
@RequestMapping("/api/porderDetail")
public class PorderDetailController {
	
	@Autowired
	PorderDetailServiceImpl porderDetailServiceImpl;

	
	@PostMapping
	public ResponseEntity<?> addPorder(@RequestBody List<PorderDetail> porderDetail)
	{
		//System.out.println(porderDetail.getUnitPrice());
		int count=0;
		for(PorderDetail p:porderDetail) {
			if(!porderDetailServiceImpl.addPorderDetail(p))
				count++;
		}
		if(count==0)		
			return ResponseEntity.ok(porderDetail);
		else
			return ResponseEntity.internalServerError().body("新增訂單明細失敗");
		
	}
	
	@GetMapping
	public ResponseEntity<?> getAllPorder()
	{
		List<PorderDetailDTO> tempList = porderDetailServiceImpl.getAllPorderDetail();
		if(tempList.size() == 0)
			return ResponseEntity.noContent().build();
		else
			return ResponseEntity.ok(tempList);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getPorderDetailById(@PathVariable String id)
	{
		PorderDetail temp = porderDetailServiceImpl.getPorderDetailById(id);
		if(temp != null)
			return ResponseEntity.ok(temp);
		else
			return ResponseEntity.notFound().build();
	}
	
	@GetMapping("/porder/{id}")
	public ResponseEntity<?> getPorderDetailByPorderId(@PathVariable String id)
	{
		List<PorderDetailDTO> tempList = porderDetailServiceImpl.getPorderDetailByPorderId(id);
		if(tempList.size() == 0)
			return ResponseEntity.noContent().build();
		else
			return ResponseEntity.ok(tempList);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> updatePorder(@PathVariable String id, @RequestBody PorderDetail porderDetail)
	{		
		if(porderDetailServiceImpl.updatePorderDetail(id, porderDetail))		
			return ResponseEntity.ok(porderDetail);		
		else
			return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deletePorder(@PathVariable String id)
	{		
		if(porderDetailServiceImpl.deletePorderDetail(id))		
			return ResponseEntity.ok("訂單明細已刪除:" + id);		
		else
			return ResponseEntity.notFound().build();
	}
}
