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
import com.cxgm.common.UUID;
import com.cxgm.dao.CouponCodeMapper;
import com.cxgm.dao.CouponMapper;
import com.cxgm.domain.Coupon;
import com.cxgm.domain.CouponCode;
import com.cxgm.exception.TipException;

/**
 * @author Administrator
 *
 */
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
			Integer maximumQuantity,boolean isEnabled,Long pid,
			String priceExpression,String introduction,Integer shopId,Long productId) {
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
		coupon.setProductId(productId);
		coupon.setProductCategoryId(pid);
		coupon.setIntroduction(introduction);
		coupon.setPriceExpression(priceExpression);
		coupon.setShopId(shopId);
		couponDao.insert(coupon);
	}
	@Transactional
	public int delete(String[] couponIds) {
		int resultDelete = 0;
		Map<String,Object> map = new HashMap<>();
		
		if (couponIds != null && couponIds.length > 0) {
			for(String couponId : couponIds) {
				map.put("couponId", couponId);
				List<CouponCode> couponCodes = couponCodeDao.findCouponsWithParam(map);
				if(couponCodes.size()>0) {
					return 0;
				}else {
					couponDao.delete(Long.valueOf(couponId));
				}
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
			Integer maximumQuantity,boolean isEnabled,Long pid,
			String priceExpression,String introduction,Integer shopId,Long productId) {
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
		coupon.setProductCategoryId(pid);
		coupon.setProductId(productId);
		coupon.setPriceExpression(null!=priceExpression?priceExpression:coupon.getPriceExpression());
		coupon.setIntroduction(introduction);
		coupon.setShopId(shopId);
		couponDao.update(coupon);
	}
	@Transactional
	public void couponCodeInsert(Long couponid,Integer codesNumber,Integer type) {
		Coupon coupon = couponDao.select(couponid);
		int generateBatch = Integer.valueOf(coupon.getPrefix())+1;
		for(int i=0;i<codesNumber;i++) {
			CouponCode cc = new CouponCode();
			cc.setCouponId(couponid);
			cc.setType(type);
			cc.setCode(UUID.generateCouponCode(6, String.valueOf(generateBatch)));
			cc.setStatus(0);
			cc.setCreationDate(new Date());
			couponCodeDao.insert(cc);
		}
		coupon.setPrefix(String.valueOf(generateBatch));
		couponDao.update(coupon);
	}
	
	/**
	 * 查询某优惠券的指定条件优惠码的数量
	 * @param couponId
	 * @return
	 */
	public int findCouponCodeListCount(Long couponId,Integer status) {
		Map<String,Object> map = new HashMap<>();
		map.put("couponId", couponId);
		map.put("status", status);
		return couponCodeDao.findListCount(map);
	}
	
	
	/**
	 * 查询被领取的优惠码
	 * @param couponId
	 * @return
	 */
	public int findCouponCodeDispatch(Long couponId) {
		Map<String,Object> map = new HashMap<>();
		map.put("couponId", couponId);
		return couponCodeDao.findDispatchCount(map);
	}
	
	
	
	/**
	 * 根据优惠券id查询优惠券code列表
	 * @param couponId
	 * @return
	 */
	public List<CouponCode> findCouponCodeById(Long couponId){
		Map<String,Object> map = new HashMap<>();
		map.put("couponId", couponId);
		return couponCodeDao.findCouponsWithParamExt(map);
		
	}
}
