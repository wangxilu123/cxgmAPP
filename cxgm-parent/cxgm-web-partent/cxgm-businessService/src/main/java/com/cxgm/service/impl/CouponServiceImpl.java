package com.cxgm.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.cxgm.dao.CouponCodeMapper;
import com.cxgm.domain.CouponCode;
import com.cxgm.domain.CouponDetail;
import com.cxgm.service.CouponService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Primary
@Service
public class CouponServiceImpl implements CouponService {

	@Autowired
	private CouponCodeMapper couponCodeMapper;
	
	@Override
	public PageInfo<CouponDetail> findCouponByUserId(Integer userId,Integer pageNum,Integer pageSize,Integer status) {
		
		PageHelper.startPage(pageNum, pageSize);
		
		HashMap<String,Object> map = new HashMap<>();
		
		map.put("userId", userId);
		map.put("status", status);
		
		List<CouponDetail> list = couponCodeMapper.findCouponsByUserId(map);
		
		PageInfo<CouponDetail> page = new PageInfo<CouponDetail>(list);
		
		return page;
	}

	@Override
	public CouponDetail exchangeCoupons(Integer userId, String code) {
		
		//根据兑换码查询优惠券信息
		CouponDetail  couponDetail = couponCodeMapper.findCouponsByCode(code);
		
		if(couponDetail!=null){
			
			CouponCode couponCode = couponCodeMapper.select((long)couponDetail.getCodeId());
			
			if(couponCode.getUserId()==null){
				couponCode.setUserId((long)userId);
				
				couponCodeMapper.update(couponCode);
				
			}
			
			return couponDetail;
		}else{
			return null;
		}
		
	}
}
