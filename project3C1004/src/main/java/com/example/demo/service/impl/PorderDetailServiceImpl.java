package com.example.demo.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dao.PorderDetailDAO;
import com.example.demo.dto.PorderDetailDTO;
import com.example.demo.model.PorderDetail;
import com.example.demo.service.PorderDetailService;
import com.example.demo.util.IdGenerator;

@Service
public class PorderDetailServiceImpl implements PorderDetailService{

	private static final Logger log = LoggerFactory.getLogger(PorderDetailServiceImpl.class);

    private final PorderDetailDAO porderDetailDAO;
    private final IdGenerator idGenerator;
    
    
	
	public PorderDetailServiceImpl(PorderDetailDAO porderDetailDAO, IdGenerator idGenerator) {
		
		this.porderDetailDAO = porderDetailDAO;
		this.idGenerator = idGenerator;
	}

	@Override
	@Transactional
	public boolean addPorderDetail(PorderDetail porderDetail) {
		try {
			porderDetail.setId(idGenerator.getNewPorderDetailId());
			porderDetailDAO.save(porderDetail);
            log.info("成功新增訂單明細：{}", porderDetail.getId());
            return true;
        } catch (Exception e) {
            log.error("新增訂單明細時發生錯誤", e);
            return false;
        }
	}

	@Override
	public List<PorderDetailDTO> getAllPorderDetail() {
		
		return porderDetailDAO.findAll().stream().map(pd -> new PorderDetailDTO(
												  pd.getId(),
												  pd.getPorder().getId(),
												  pd.getProduct().getId(),
												  pd.getProduct().getName(),
												  pd.getUnitPrice(),
												  pd.getQuantity()))
												 .collect(Collectors.toList());
	}

	@Override
	public PorderDetail getPorderDetailById(String id) {
		
		return porderDetailDAO.findById(id).orElse(null);
	}
	
	@Override
	public List<PorderDetailDTO> getPorderDetailByPorderId(String id) {
		
		return porderDetailDAO.findByPorderId(id).stream().map(pd -> new PorderDetailDTO(
														  pd.getId(),
														  pd.getPorder().getId(),
														  pd.getProduct().getId(),
														  pd.getProduct().getName(),
														  pd.getUnitPrice(),
														  pd.getQuantity()))
														  .collect(Collectors.toList());
	}

	@Override
	@Transactional
	public boolean updatePorderDetail(String id, PorderDetail porderDetail) {
		return porderDetailDAO.findById(id).map(exist -> {
			 porderDetail.setId(id);
			 porderDetailDAO.save(porderDetail);
			 log.info("成功更新訂單明細資料：{}", id);
           return true;
       }).orElseGet(()->{
       	log.warn("更新失敗，找不到訂單明細 ID:{}", id);
       	return false;
       });
	}

	@Override
	@Transactional
	public boolean deletePorderDetail(String id) {
		return porderDetailDAO.findById(id).map(exist -> {
			porderDetailDAO.deleteById(id);
			log.info("成功刪除訂單明細：{}", id);
            return true;
        }).orElseGet(() -> {
            log.warn("刪除失敗，找不到訂單明細 ID：{}", id);
            return false;
        });
	}

	

}
