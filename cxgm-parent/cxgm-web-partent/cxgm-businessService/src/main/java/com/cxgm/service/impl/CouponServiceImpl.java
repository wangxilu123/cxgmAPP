package com.cxgm.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.cxgm.dao.CouponMapper;
import com.cxgm.domain.Coupon;
import com.cxgm.service.CouponService;

@Primary
@Service
public class CouponServiceImpl implements CouponService {

	@Autowired
	private CouponMapper couponMapper;

	@Override
	public List<Coupon> findCouponByUserId(Integer userId,Integer pageNum,Integer pageSize) {
		
		Map<String,Object> map = new HashMap<String,Object>();
		
		List<Coupon> list = couponMapper.findCouponsWithParam(map);
		
		return null;
	}
}
