package com.cxgm.dao;

import com.cxgm.domain.Coupon;

public interface CouponMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Coupon record);

    int insertSelective(Coupon record);

    Coupon selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Coupon record);

    int updateByPrimaryKeyWithBLOBs(Coupon record);

    int updateByPrimaryKey(Coupon record);
}