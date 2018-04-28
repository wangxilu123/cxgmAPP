package com.cxgm.service;

import java.util.List;

import com.cxgm.domain.Coupon;

public interface CouponService {

	List<Coupon> findCouponByUserId(Integer userId,Integer pageNum,Integer pageSize);

  
}
