package com.cxgm.dao;

import com.cxgm.domain.CouponCode;

public interface CouponCodeMapper {
    int deleteByPrimaryKey(Long id);

    int insert(CouponCode record);

    int insertSelective(CouponCode record);

    CouponCode selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CouponCode record);

    int updateByPrimaryKey(CouponCode record);
}