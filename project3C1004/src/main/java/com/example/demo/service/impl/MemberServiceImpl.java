package com.example.demo.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dao.MemberDAO;
import com.example.demo.model.Member;
import com.example.demo.service.MemberService;
import com.example.demo.util.IdGenerator;

@Service
public class MemberServiceImpl implements MemberService{
	
	private static final Logger log = LoggerFactory.getLogger(MemberServiceImpl.class);

    private final MemberDAO memberDAO;
    private final IdGenerator idGenerator;
    
    
	
	public MemberServiceImpl(MemberDAO memberDAO, IdGenerator idGenerator) {		
		this.memberDAO = memberDAO;
		this.idGenerator = idGenerator;
	}

	@Override
	@Transactional
	public boolean addMember(Member member) {
		try {
			member.setId(idGenerator.getNewMemberId());
			memberDAO.save(member);
            log.info("成功新增會員：{}", member.getId());
            return true;
        } catch (Exception e) {
            log.error("新增會員時發生錯誤", e);
            return false;
        }
	}

	@Override
	public List<Member> getAllMember() {
		
		return memberDAO.findAll();
	}

	@Override
	public Member getMemberById(String id) {
		
		return memberDAO.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public boolean updateMember(String id, Member member) {
		return memberDAO.findById(id).map(exist -> {
			 member.setId(id);
			 memberDAO.save( member);
			 log.info("成功更新會員資料：{}", id);
            return true;
        }).orElseGet(()->{
        	log.warn("更新失敗，找不到會員 ID:{}", id);
        	return false;
        });
	}

	@Override
	@Transactional
	public boolean deleteMember(String id) {
		return memberDAO.findById(id).map(exist -> {
			memberDAO.deleteById(id);
			log.info("成功刪除會員：{}", id);
            return true;
        }).orElseGet(() -> {
            log.warn("刪除失敗，找不到會員 ID：{}", id);
            return false;
        });
	}

}
