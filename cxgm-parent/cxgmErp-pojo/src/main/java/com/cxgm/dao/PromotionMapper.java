package com.cxgm.dao;

import java.util.List;
import java.util.Map;

import com.cxgm.domain.Promotion;

public interface PromotionMapper {
	
    int delete(Long id);
    
    int insert(Promotion record);
    
    Promotion select(Long id);
    
    int update(Promotion record);

    List<Promotion> findPromotionsWithParam(Map<String,Object> map);
}