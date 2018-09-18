package com.cxgm.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
				couponCode.setStatus(0);
				couponCodeMapper.update(couponCode);
				
			}
			
			return couponDetail;
		}else{
			return null;
		}
		
	}
	
	@Override
	public void getCoupons(Integer userId, String couponIds) {
		String[] couponList = couponIds.split(",");
		for(int i=0;i<couponList.length;i++){
			
			Map<String,Object> map= new HashMap<>();
			map.put("userId", userId);
			map.put("couponId", couponList[i]);
			List<CouponCode> couponCodeList = couponCodeMapper.findCouponsWithParam(map);
			
			if(couponCodeList.size()==0){
				//根据优惠券ID分配兑换码
				List<CouponCode>  couponCodes = couponCodeMapper.findCouponsWithStatus( Long.valueOf(couponList[i]));
				if(couponCodes.size()!=0){
					couponCodes.get(0).setUserId((long)userId);
					couponCodes.get(0).setStatus(0);
					couponCodeMapper.update(couponCodes.get(0));
				}
			}
		}
	}
}
