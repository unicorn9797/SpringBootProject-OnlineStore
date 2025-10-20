package com.example.demo.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dao.PorderDAO;
import com.example.demo.dto.PorderDTO;
import com.example.demo.model.Porder;
import com.example.demo.model.PorderDetail;
import com.example.demo.service.PorderService;
import com.example.demo.util.IdGenerator;


@Service
public class PorderServiceImpl implements PorderService{

	private static final Logger log = LoggerFactory.getLogger(PorderServiceImpl.class);

    private final PorderDAO porderDAO;
    private final IdGenerator idGenerator;    
    

	
	public PorderServiceImpl(PorderDAO porderDAO, IdGenerator idGenerator) {
		
		this.porderDAO = porderDAO;
		this.idGenerator = idGenerator;
	}

	@Override
	@Transactional
	public boolean addPorder(Porder porder) {
	    try {
	        porder.setId(idGenerator.getNewPorderId());
	        porder.setDateTime(LocalDateTime.now());
	        if(porder.getDetails() != null && !porder.getDetails().isEmpty())
	        {	
	        	int i = 1;
	        	for(PorderDetail porderDetail : porder.getDetails())
	        	{
	        		porderDetail.setId(porder.getId() + String.format("-D%03d", i));
	        		i++;
	        	}
	        }
	        porderDAO.save(porder);
	        log.info("成功新增訂單：{}", porder.getId());
	        return true;
	    } catch (Exception e) {
	        log.error("新增訂單時發生錯誤", e);
	        return false;
	    }
	}

	@Override
	public List<PorderDTO> getAllPorder() {
		List<PorderDTO> tempList = porderDAO.findAll().stream()
												   .map(po-> new PorderDTO(
														   po.getId(),
														   po.getMember().getId(),
														   po.getMember().getName(),
														   po.getTotalPrice(),
														   po.getDateTime(),
												   		   po.getRemark()))
														   .collect(Collectors.toList());
		
		return tempList;
	}

	@Override
	public Porder getPorderById(String id) {
		
		return porderDAO.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public boolean updatePorder(String id, Porder porder) {
		return porderDAO.findById(id).map(exist -> {
			 porder.setId(id);
			 porderDAO.save(porder);
			 log.info("成功更新訂單資料：{}", id);
          return true;
      }).orElseGet(()->{
      	log.warn("更新失敗，找不到訂單 ID:{}", id);
      	return false;
      });
	}

	@Override
	@Transactional
	public boolean deletePorder(String id) {
		return porderDAO.findById(id).map(exist -> {
			porderDAO.deleteById(id);
			log.info("成功刪除訂單：{}", id);
            return true;
        }).orElseGet(() -> {
            log.warn("刪除失敗，找不到訂單 ID：{}", id);
            return false;
        });
	}

}
