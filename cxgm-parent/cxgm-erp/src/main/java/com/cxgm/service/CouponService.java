package com.cxgm.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cxgm.common.DateKit;
import com.cxgm.dao.CouponCodeMapper;
import com.cxgm.dao.CouponMapper;
import com.cxgm.domain.Coupon;
import com.cxgm.domain.CouponCode;
import com.cxgm.exception.TipException;

@Service
public class CouponService {

	@Autowired
	CouponMapper couponDao;
	@Autowired
	CouponCodeMapper couponCodeDao;
	
	public List<Coupon> findCouponsWithParam(Map<String,Object> map){
		return couponDao.findCouponsWithParam(map);
	}
	
	@Transactional
	public void insert(String name, String prefix,String beginDate,String endDate,BigDecimal minimumPrice,
			BigDecimal maximumPrice,Integer minimumQuantity,
			Integer maximumQuantity,boolean isEnabled,Integer pid,
			String priceExpression,String introduction,Integer shopId) {
		Coupon coupon = new Coupon();
		Map<String,Object> map = new HashMap<>();
		map.put("name", name);
		map.put("shopId", shopId);
		List<Coupon> coupons = couponDao.findCouponsWithParam(map);
		if(coupons.size()>0) {
			throw new TipException("已经存在相同的优惠券");
		}
		coupon.setName(name);
		coupon.setPrefix(prefix);
		coupon.setBeginDate(DateKit.dateFormat(beginDate,"yyyy-MM-dd HH:mm"));
		coupon.setEndDate(DateKit.dateFormat(endDate,"yyyy-MM-dd HH:mm"));
		coupon.setMinimumPrice(minimumPrice);
		coupon.setMaximumPrice(maximumPrice);
		coupon.setCreationDate(new Date());
		coupon.setMinimumQuantity(minimumQuantity);
		coupon.setMaximumQuantity(maximumQuantity);
		coupon.setIsEnabled(isEnabled);
		coupon.setProductCategoryId(Long.valueOf(pid));
		coupon.setIntroduction(introduction);
		coupon.setPriceExpression(priceExpression);
		coupon.setShopId(shopId);
		couponDao.insert(coupon);
	}
	@Transactional
	public int delete(String[] couponIds) {
		int resultDelete = 0;
		if (couponIds != null && couponIds.length > 0) {
			for(String couponId : couponIds) {
				couponDao.delete(Long.valueOf(couponId));
			}
		}
		resultDelete = 1;
		return resultDelete;
	}
	
	public Coupon select(Long id) {
		return couponDao.select(id);
	}
	
	@Transactional
	public void update(Long id, String name, String prefix,String beginDate,String endDate,BigDecimal minimumPrice,
			BigDecimal maximumPrice,Integer minimumQuantity,
			Integer maximumQuantity,boolean isEnabled,Integer pid,
			String priceExpression,String introduction,Integer shopId) {
		Coupon coupon = couponDao.select(id);
		coupon.setName(null != name?name:coupon.getName());
		Map<String,Object> map = new HashMap<>();
		map.put("couponId", coupon.getId());
		List<CouponCode> couponCodes = couponCodeDao.findCouponsWithParam(map);
		if(couponCodes.size()<=0) {
			coupon.setPrefix(prefix);
		}
		coupon.setBeginDate(null!=beginDate?DateKit.dateFormat(beginDate,"yyyy-MM-dd HH:mm"):coupon.getBeginDate());
		coupon.setEndDate(null!=endDate?DateKit.dateFormat(endDate,"yyyy-MM-dd HH:mm"):coupon.getEndDate());
		coupon.setMinimumPrice(null!=minimumPrice?minimumPrice:coupon.getMinimumPrice());
		coupon.setMaximumPrice(null!=maximumPrice?maximumPrice:coupon.getMaximumPrice());
		coupon.setMinimumQuantity(null!=minimumQuantity?minimumQuantity:coupon.getMinimumQuantity());
		coupon.setMaximumQuantity(null!=maximumQuantity?maximumQuantity:coupon.getMaximumQuantity());
		coupon.setIsEnabled(isEnabled);
		coupon.setProductCategoryId(Long.valueOf(pid));
		coupon.setPriceExpression(null!=priceExpression?priceExpression:coupon.getPriceExpression());
		coupon.setIntroduction(introduction);
		coupon.setShopId(shopId);
		couponDao.update(coupon);
	}
	
}
