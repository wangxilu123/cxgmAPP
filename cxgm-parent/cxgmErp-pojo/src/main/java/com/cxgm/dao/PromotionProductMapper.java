package com.cxgm.dao;

import java.util.List;
import java.util.Map;

import com.cxgm.domain.PromotionProduct;

public interface PromotionProductMapper {
    int delete(Long id);
    
    int deleteByPromotionCategory(Long promotionId);
    
    int deleteByPromotionProduct(Long promotionId);

    int insert(PromotionProduct record);

    PromotionProduct select(Long id);

    int update(PromotionProduct record);
    
    List<PromotionProduct> findByParams(Map<String,Object> map);

}