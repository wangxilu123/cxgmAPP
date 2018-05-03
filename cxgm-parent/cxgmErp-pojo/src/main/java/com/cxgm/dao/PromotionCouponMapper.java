package com.cxgm.dao;

import com.cxgm.domain.PromotionCoupon;

public interface PromotionCouponMapper {
    int delete(Long id);

    int insert(PromotionCoupon record);

    PromotionCoupon select(Long id);

    int update(PromotionCoupon record);

}