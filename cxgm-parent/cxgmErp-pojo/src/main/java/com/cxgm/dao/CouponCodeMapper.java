package com.cxgm.dao;

import java.util.List;
import java.util.Map;

import com.cxgm.domain.CouponCode;
import com.cxgm.domain.CouponDetail;

public interface CouponCodeMapper {
    int delete(Long id);
    int insert(CouponCode record);
    CouponCode select(Long id);
    int update(CouponCode record);
    List<CouponCode> findCouponsWithParam(Map<String,Object> map);
    
    List<CouponDetail> findCouponsByUserId(Integer userId);

    int findListCount(Map<String,Object> map);
    int findDispatchCount(Map<String,Object> map);
}