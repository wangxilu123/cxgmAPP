package com.cxgm.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.cxgm.common.DateKit;
import com.cxgm.dao.CouponMapper;
import com.cxgm.dao.ProductCategoryMapper;
import com.cxgm.dao.ProductMapper;
import com.cxgm.dao.PromotionCouponMapper;
import com.cxgm.dao.PromotionMapper;
import com.cxgm.dao.PromotionProductMapper;
import com.cxgm.domain.Coupon;
import com.cxgm.domain.Product;
import com.cxgm.domain.ProductCategory;
import com.cxgm.domain.ProductTransfer;
import com.cxgm.domain.Promotion;
import com.cxgm.domain.PromotionCoupon;
import com.cxgm.domain.PromotionProduct;
import com.cxgm.exception.TipException;

@Service
public class PromotionService {

	@Autowired
	PromotionMapper promotionDao;
	@Autowired
	PromotionCouponMapper promotionCouponDao;
	@Autowired
	CouponMapper couponDao;
	@Autowired
	ProductCategoryMapper productCategoryDao;
	@Autowired
	PromotionProductMapper promotionProductDao;
	@Autowired
	ProductMapper productDao;
	@Autowired
	PlatformTransactionManager txManager;

	public List<Promotion> findPromotionsWithParam(Map<String, Object> map) {
		return promotionDao.findPromotionsWithParam(map);
	}

	public Promotion select(Long id) {
		List<Coupon> couponList = new ArrayList<>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("promotions", id);
		List<PromotionCoupon> promotionCoupons = promotionCouponDao.findPromotionCouponsWithParam(map);
		for (PromotionCoupon promotionCoupon : promotionCoupons) {
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
			for (String couponId : couponIds) {
				promotionDao.delete(Long.valueOf(couponId));
			}
		}
		resultDelete = 1;
		return resultDelete;
	}

	@Transactional
	public void insert(String name, String title, String beginDate, String endDate, BigDecimal minimumPrice,
			BigDecimal maximumPrice, Integer minimumQuantity, Integer maximumQuantity, String priceExpression,
			Boolean isCouponAllowed, String introduction, Integer categoryId, Integer productId, Integer shopId,
			String[] couponIds) {
		Promotion promotion = new Promotion();
		Map<String, Object> map = new HashMap<>();
		map.put("name", name);
		map.put("shopId", shopId);
		List<Promotion> promotions = promotionDao.findPromotionsWithParam(map);
		if (promotions.size() > 0) {
			throw new TipException("已经存在相同的促销活动");
		}

		promotion.setName(name);
		promotion.setTitle(title);
		promotion.setBeginDate(DateKit.dateFormat(beginDate, "yyyy-MM-dd HH:mm"));
		promotion.setEndDate(DateKit.dateFormat(endDate, "yyyy-MM-dd HH:mm"));
		promotion.setCreationDate(new Date());
		promotion.setMaximumPrice(maximumPrice);
		promotion.setMaximumQuantity(maximumQuantity);
		promotion.setMinimumPrice(minimumPrice);
		promotion.setMinimumQuantity(minimumQuantity);
		promotion.setIntroduction(introduction);
		promotion.setPriceExpression(priceExpression);
		promotion.setIsCouponAllowed(isCouponAllowed);
		promotion.setShopId(shopId);
		promotionDao.insert(promotion);
		if (null != couponIds && couponIds.length > 0) {
			for (String couponId : couponIds) {
				PromotionCoupon promotionCoupon = new PromotionCoupon();
				promotionCoupon.setCoupons(Long.valueOf(couponId));
				promotionCoupon.setPromotions(promotion.getId());
				promotionCouponDao.insert(promotionCoupon);
			}
		}

		ProductCategory productCategory = productCategoryDao.findById(categoryId.longValue());
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus status = txManager.getTransaction(def);
		try {
			if (null != productCategory) {
				switch (productCategory.getGrade()) {
				case 0:
					map.put("productCategoryId", categoryId.longValue());
					break;
				case 1:
					map.put("productCategoryTwoId", categoryId.longValue());
					break;
				case 2:
					map.put("productCategoryThirdId", categoryId.longValue());
					;
					break;
				}
				List<Product> products = productDao.findProducts(map);
				for (Product product : products) {
					PromotionProduct promotionProduct = new PromotionProduct();
					promotionProduct.setProducts(product.getId());
					promotionProduct.setPromotions(promotion.getId());
					promotionProduct.setType(1);
					promotionProductDao.insert(promotionProduct);
				}
			}
			txManager.commit(status); // 提交事务
		} catch (Exception e) {
			txManager.rollback(status); // 回滚事务
		}
		;

		map.clear();
		map.put("promotions", promotion.getId());
		map.put("products", productId.longValue());
		List<PromotionProduct> promotionProducts = promotionProductDao.findByParams(map);
		PromotionProduct promotionProduct = new PromotionProduct();
		if (promotionProducts.size() > 0) {
			promotionProduct = promotionProducts.get(0);
			promotionProduct.setProducts(productId.longValue());
			promotionProduct.setPromotions(promotion.getId());
			promotionProduct.setType(0);
			promotionProductDao.update(promotionProduct);
		} else {
			promotionProduct.setProducts(productId.longValue());
			promotionProduct.setPromotions(promotion.getId());
			promotionProduct.setType(0);
			promotionProductDao.insert(promotionProduct);
		}

	}

	@Transactional
	public void update(Long id, String name, String title, String beginDate, String endDate, BigDecimal minimumPrice,
			BigDecimal maximumPrice, Integer minimumQuantity, Integer maximumQuantity, String priceExpression,
			Boolean isCouponAllowed, String introduction, Integer categoryId, Integer productId, Integer shopId,
			String[] couponIds) {
		Promotion promotion = promotionDao.select(id);
		promotion.setName(name);
		promotion.setTitle(title);
		promotion.setBeginDate(DateKit.dateFormat(beginDate, "yyyy-MM-dd HH:mm"));
		promotion.setEndDate(DateKit.dateFormat(endDate, "yyyy-MM-dd HH:mm"));
		promotion.setMaximumPrice(maximumPrice);
		promotion.setMaximumQuantity(maximumQuantity);
		promotion.setMinimumPrice(minimumPrice);
		promotion.setMinimumQuantity(minimumQuantity);
		promotion.setIntroduction(introduction);
		promotion.setPriceExpression(priceExpression);
		promotion.setShopId(shopId);
		promotionDao.update(promotion);
		Map<String, Object> map = new HashMap<>();
		map.put("promotions", promotion.getId());
		List<PromotionCoupon> promotionCoupons = promotionCouponDao.findPromotionCouponsWithParam(map);
		for (PromotionCoupon promotionCoupon : promotionCoupons) {
			promotionCouponDao.delete(promotionCoupon.getId());
		}
		if (null != couponIds && couponIds.length > 0) {
			for (String couponId : couponIds) {
				PromotionCoupon promotionCoupon = new PromotionCoupon();
				promotionCoupon.setCoupons(Long.valueOf(couponId));
				promotionCoupon.setPromotions(promotion.getId());
				promotionCouponDao.insert(promotionCoupon);
			}
		}
		promotionProductDao.deleteByPromotionCategory(promotion.getId());
		ProductCategory productCategory = productCategoryDao.findById(categoryId.longValue());

		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus status = txManager.getTransaction(def);
		try {
			if (null != productCategory) {
				switch (productCategory.getGrade()) {
				case 0:
					map.put("productCategoryId", categoryId.longValue());
					break;
				case 1:
					map.put("productCategoryTwoId", categoryId.longValue());
					break;
				case 2:
					map.put("productCategoryThirdId", categoryId.longValue());
					;
					break;
				}
				List<Product> products = productDao.findProducts(map);
				for (Product product : products) {

					PromotionProduct promotionProduct = new PromotionProduct();
					promotionProduct.setProducts(product.getId());
					promotionProduct.setPromotions(promotion.getId());
					promotionProduct.setType(1);
					promotionProductDao.insert(promotionProduct);

				}
			}
			txManager.commit(status); // 提交事务
		} catch (Exception e) {
			txManager.rollback(status); // 回滚事务
		}
		;
		promotionProductDao.deleteByPromotionProduct(promotion.getId());
		PromotionProduct promotionProduct = new PromotionProduct();
		promotionProduct.setProducts(productId.longValue());
		promotionProduct.setPromotions(promotion.getId());
		promotionProduct.setType(0);
		promotionProductDao.insert(promotionProduct);

	}

	public Integer getCategoryId(Integer promotionId) {
		int categoryId = -1;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("promotions", promotionId.longValue());
		map.put("type", 1);
		List<PromotionProduct> promotionProducts = promotionProductDao.findByParams(map);
		if(promotionProducts.size()==0) {
			return categoryId;
		}
		ProductTransfer initProduct = productDao.findById(promotionProducts.get(0).getProducts());
		int firstId = 1;
		int twoId = 1;
		int thirdId = 1;
		for (int i = 1; i < promotionProducts.size(); i++) {
			if (null != productDao.findById(promotionProducts.get(i).getProducts()).getProductCategoryId()
					&& productDao.findById(promotionProducts.get(i).getProducts()).getProductCategoryId()
							.longValue() == initProduct.getProductCategoryId().longValue())
				firstId++;

			if (null!= initProduct.getProductCategoryTwoId() && null != productDao.findById(promotionProducts.get(i).getProducts()).getProductCategoryTwoId()
					&& productDao.findById(promotionProducts.get(i).getProducts()).getProductCategoryTwoId()
							.longValue() == initProduct.getProductCategoryTwoId().longValue())
				twoId++;
			if (null!=initProduct.getProductCategoryThirdId() && null != productDao.findById(promotionProducts.get(i).getProducts()).getProductCategoryThirdId()
					&& productDao.findById(promotionProducts.get(i).getProducts()).getProductCategoryThirdId()
							.longValue() == initProduct.getProductCategoryThirdId().longValue())
				thirdId++;
		}
		
		if (thirdId == promotionProducts.size()) {
			return initProduct.getProductCategoryThirdId().intValue();
		}
		if (twoId == promotionProducts.size()) {
			return initProduct.getProductCategoryTwoId().intValue();
		}
		if (firstId == promotionProducts.size()) {
			return initProduct.getProductCategoryId().intValue();
		}
		
		
		return categoryId;
	}

	public Integer getProductId(Integer promotionId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("promotions", promotionId.longValue());
		map.put("type", 0);
		List<PromotionProduct> promotionProducts = promotionProductDao.findByParams(map);
		if (promotionProducts.size() > 0) {
			PromotionProduct promotionProduct = promotionProducts.get(0);
			ProductTransfer initProduct = productDao.findById(promotionProduct.getProducts());
			return initProduct.getId().intValue();
		} else {
			return -1;
		}
	}
}
