package com.cxgm.dao;

import java.util.List;
import java.util.Map;

import com.cxgm.domain.CouponCode;

public interface CouponCodeMapper {
    int delete(Long id);
    int insert(CouponCode record);
    CouponCode select(Long id);
    int update(CouponCode record);
    List<CouponCode> findCouponsWithParam(Map<String,Object> map);

}