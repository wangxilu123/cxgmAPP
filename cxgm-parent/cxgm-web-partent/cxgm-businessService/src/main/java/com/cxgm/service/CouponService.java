package com.cxgm.service;

import com.cxgm.domain.AppUser;
import com.cxgm.domain.CouponDetail;
import com.github.pagehelper.PageInfo;

public interface CouponService {

	PageInfo<CouponDetail> findCouponByUserId(Integer userId,Integer pageNum,Integer pageSize,Integer status);
	
	CouponDetail exchangeCoupons(Integer userId,String code);

	void getCoupons(Integer userId, String couponIds);
	
	AppUser getAppUser(Integer userId);
	
}
