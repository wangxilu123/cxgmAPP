package com.cxgm.dao;

import java.util.List;
import java.util.Map;

import com.cxgm.domain.Coupon;

public interface CouponMapper {
    int delete(Long id);

    int insert(Coupon record);

    Coupon select(Long id);

    Coupon selectByPrimaryKey(Long id);
    
    int update(Coupon record);
    
    List<Coupon> findCouponsWithParam(Map<String,Object> map);

}