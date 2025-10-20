package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

import com.example.demo.model.Member;
import com.example.demo.service.impl.MemberServiceImpl;
@CrossOrigin
@RestController
@RequestMapping("/api/member")
public class MemberController {
	
	@Autowired
	MemberServiceImpl memberServiceImpl;	
	
	@PostMapping
	public ResponseEntity<?> addMember(@RequestBody Member member)
	{
		
		List<Member> lm=memberServiceImpl.getAllMember();
		Member found=lm.stream().filter(f->f.getPhone().equals(member.getPhone())).findAny().orElse(null);
		if(found!=null) {
			return ResponseEntity.internalServerError().body("手機號碼 已有人使用過，請檢查");
		}
		if(memberServiceImpl.addMember(member))
			return ResponseEntity.ok(member);
		else		
			return ResponseEntity.internalServerError().body("新增會員失敗");
		
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> loginMember(@RequestBody Member member)
	{
		
		List<Member> lm=memberServiceImpl.getAllMember();
		Member found=lm.stream().filter(f->f.getPhone().equals(member.getPhone())).findAny().orElse(null);
	
		if (found == null) {
	        return ResponseEntity.notFound().build(); // 會員不存在
	    } else if (found.getPassword().equals(member.getPassword())) {
	        found.setPassword(null);  // 清除密碼避免回傳
	        return ResponseEntity.ok(found); // 登入成功，回傳會員資料
	    } else {
	    	 return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); // 密碼錯誤
	    }
		
	}
	
	@GetMapping
	public ResponseEntity<?> getAllMember()
	{
		List<Member> tempList = memberServiceImpl.getAllMember();
		if(tempList.size() == 0)
			return ResponseEntity.noContent().build();
		else
			return ResponseEntity.ok(tempList);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getMemberById(@PathVariable String id)
	{
		Member temp = memberServiceImpl.getMemberById(id);
		if(temp != null)
			return ResponseEntity.ok(temp);
		else
			return ResponseEntity.notFound().build();
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> updateMember(@PathVariable String id, @RequestBody Member member)
	{
		/*新加上的程式 修改個資_手機防碰撞*/
		List<Member> lm=memberServiceImpl.getAllMember();
		Member newPhoneFound=lm.stream().filter(f->f.getPhone().equals(member.getPhone())).findAny().orElse(null);
		Member memberFound=lm.stream().filter(f->f.getId().equals(id)).findAny().orElse(null);
				
		if (newPhoneFound==null || memberFound.getPhone().equals(member.getPhone())) {
			
	       if(memberServiceImpl.updateMember(id, member))					
	    	   return ResponseEntity.ok(member);		
	       else
	    	   return ResponseEntity.notFound().build();
			
	    } else 
	    	return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); // 手機號碼重複
	    }
		/*新加上的程式 修改個資_手機防碰撞*/
		
		
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteMember(@PathVariable String id)
	{		
		if(memberServiceImpl.deleteMember(id))		
			return ResponseEntity.ok("會員已刪除:" + id);		
		else
			return ResponseEntity.notFound().build();
	}
}
