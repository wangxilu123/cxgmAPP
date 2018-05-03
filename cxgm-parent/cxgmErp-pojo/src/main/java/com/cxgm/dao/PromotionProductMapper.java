package com.cxgm.dao;

import com.cxgm.domain.PromotionProduct;

public interface PromotionProductMapper {
    int delete(Long id);

    int insert(PromotionProduct record);

    PromotionProduct select(Long id);

    int update(PromotionProduct record);

}