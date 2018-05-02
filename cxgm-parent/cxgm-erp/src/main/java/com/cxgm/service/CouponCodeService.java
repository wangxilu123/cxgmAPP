package com.cxgm.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cxgm.dao.CouponCodeMapper;
import com.cxgm.domain.CouponCode;
@Service
public class CouponCodeService {

	@Autowired
	CouponCodeMapper couponCodeDao;
	public List<CouponCode> findCouponsWithParam(Map<String,Object> map){
		return couponCodeDao.findCouponsWithParam(map);
	}
}
