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

import com.example.demo.dto.PorderDTO;
import com.example.demo.model.Porder;
import com.example.demo.service.impl.PorderServiceImpl;
@CrossOrigin
@RestController
@RequestMapping("/api/porder")
public class PorderController {

	@Autowired
	PorderServiceImpl porderServiceImpl;
	

	@PostMapping
	public ResponseEntity<?> addPorder(@RequestBody Porder porder)
	{
		if(porderServiceImpl.addPorder(porder))
			return ResponseEntity.ok(porder);
		else
			return ResponseEntity.internalServerError().body("新增訂單失敗");
		
	}
	
	@GetMapping
	public ResponseEntity<?> getAllPorder()
	{
		List<PorderDTO> tempList = porderServiceImpl.getAllPorder();
		if(tempList.size() == 0)
			return ResponseEntity.noContent().build();
		else
			return ResponseEntity.ok(tempList);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getPorderById(@PathVariable String id)
	{
		Porder temp = porderServiceImpl.getPorderById(id);
		if(temp != null)
			return ResponseEntity.ok(temp);
		else
			return ResponseEntity.noContent().build();
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> updatePorder(@PathVariable String id, @RequestBody Porder porder)
	{
		
		if(porderServiceImpl.updatePorder(id, porder))
			return ResponseEntity.ok(porder);		
		else
			return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deletePorder(@PathVariable String id)
	{		
		if(porderServiceImpl.deletePorder(id))			
			return ResponseEntity.ok("訂單已刪除" + id);		
		else
			return ResponseEntity.notFound().build();
	}
}
