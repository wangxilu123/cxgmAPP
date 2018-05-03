package com.cxgm.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cxgm.dao.PromotionMapper;
import com.cxgm.domain.Promotion;

@Service
public class PromotionService {

	@Autowired
	PromotionMapper promotionDao;
	
	public List<Promotion> findPromotionsWithParam(Map<String,Object> map){
		return promotionDao.findPromotionsWithParam(map);
	}
	
	@Transactional
	public int delete(String[] couponIds) {
		int resultDelete = 0;
		if (couponIds != null && couponIds.length > 0) {
			for(String couponId : couponIds) {
				promotionDao.delete(Long.valueOf(couponId));
			}
		}
		resultDelete = 1;
		return resultDelete;
	}
}
