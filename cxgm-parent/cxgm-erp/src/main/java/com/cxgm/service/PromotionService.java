package com.cxgm.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cxgm.common.DateKit;
import com.cxgm.dao.CouponMapper;
import com.cxgm.dao.PromotionCouponMapper;
import com.cxgm.dao.PromotionMapper;
import com.cxgm.domain.Coupon;
import com.cxgm.domain.Promotion;
import com.cxgm.domain.PromotionCoupon;
import com.cxgm.exception.TipException;

@Service
public class PromotionService {

	@Autowired
	PromotionMapper promotionDao;
	@Autowired
	PromotionCouponMapper promotionCouponDao;
	@Autowired
	CouponMapper couponDao;
	
	public List<Promotion> findPromotionsWithParam(Map<String,Object> map){
		return promotionDao.findPromotionsWithParam(map);
	}
	
	
	public Promotion select(Long id) {
		List<Coupon> couponList = new ArrayList<>();
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("promotions", id);
		List<PromotionCoupon> promotionCoupons = promotionCouponDao.findPromotionCouponsWithParam(map);
		for(PromotionCoupon promotionCoupon:promotionCoupons) {
			Coupon coupon = couponDao.select(promotionCoupon.getCoupons());
			couponList.add(coupon);
		}
		Promotion promotion = promotionDao.select(id);
		promotion.setCouponList(couponList);
		return promotion;
	}
	@Transactional
	public int delete(String[] couponIds) {
		int resultDelete = 0;
		if (couponIds != null && couponIds.length > 0) {
			for(String couponId : couponIds) {
				promotionDao.delete(Long.valueOf(couponId));
			}
		}
		resultDelete = 1;
		return resultDelete;
	}
	
	@Transactional
	public void insert(String name,String title, String beginDate,String endDate,Integer pid,
			String priceExpression,Boolean isCouponAllowed,String introduction,Integer shopId,
			String[] couponIds) {
		Promotion promotion = new Promotion();
		Map<String,Object> map = new HashMap<>();
		map.put("name", name);
		map.put("shopId", shopId);
		List<Promotion> promotions = promotionDao.findPromotionsWithParam(map);
		if(promotions.size()>0) {
			throw new TipException("已经存在相同的促销活动");
		}
		promotion.setName(name);
		promotion.setTitle(title);
		promotion.setBeginDate(DateKit.dateFormat(beginDate,"yyyy-MM-dd HH:mm"));
		promotion.setEndDate(DateKit.dateFormat(endDate,"yyyy-MM-dd HH:mm"));
		promotion.setCreationDate(new Date());
		promotion.setIntroduction(introduction);
		promotion.setPriceExpression(priceExpression);
		promotion.setIsCouponAllowed(isCouponAllowed);
		promotion.setShopId(shopId);
		promotionDao.insert(promotion);
		for(String couponId : couponIds) {
			PromotionCoupon promotionCoupon = new PromotionCoupon();
			promotionCoupon.setCoupons(Long.valueOf(couponId));
			promotionCoupon.setPromotions(promotion.getId());
			promotionCouponDao.insert(promotionCoupon);
		}
	}
	
	@Transactional
	public void update(Long id,String name, String title, String beginDate,String endDate,Integer pid,
			String priceExpression,Boolean isCouponAllowed,String introduction,Integer shopId,
			String[] couponIds) {
		Promotion promotion = promotionDao.select(id);
		promotion.setName(name);
		promotion.setTitle(title);
		promotion.setBeginDate(DateKit.dateFormat(beginDate,"yyyy-MM-dd HH:mm"));
		promotion.setEndDate(DateKit.dateFormat(endDate,"yyyy-MM-dd HH:mm"));
		promotion.setIntroduction(introduction);
		promotion.setPriceExpression(priceExpression);
		promotion.setShopId(shopId);
		promotionDao.update(promotion);
		Map<String,Object> map = new HashMap<>();
		map.put("promotions", promotion.getId());
		List<PromotionCoupon> promotionCoupons = promotionCouponDao.findPromotionCouponsWithParam(map);
		for(PromotionCoupon promotionCoupon:promotionCoupons) {
			promotionCouponDao.delete(promotionCoupon.getId());
		}
		for(String couponId : couponIds) {
			PromotionCoupon promotionCoupon = new PromotionCoupon();
			promotionCoupon.setCoupons(Long.valueOf(couponId));
			promotionCoupon.setPromotions(promotion.getId());
			promotionCouponDao.insert(promotionCoupon);
		}
	}
}
