package com.cxgm.dao;

import java.util.List;
import java.util.Map;

import com.cxgm.domain.Coupon;

public interface CouponMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Coupon record);

    int insertSelective(Coupon record);

    Coupon selectByPrimaryKey(Long id);
    
    List<Coupon> selectByList(Map<String,Object> map);

    int updateByPrimaryKeySelective(Coupon record);

    int updateByPrimaryKeyWithBLOBs(Coupon record);

    int updateByPrimaryKey(Coupon record);
}