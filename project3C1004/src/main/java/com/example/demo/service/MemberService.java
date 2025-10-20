package com.example.demo.service;
import java.util.List;

import com.example.demo.model.Member;

public interface MemberService {
	//create
	public boolean addMember(Member member);
	//read
	public List<Member> getAllMember();
	public Member getMemberById(String id);
	//update
	public boolean updateMember(String id, Member member);
	//delete
	public boolean deleteMember(String id);
}
