package com.cxgm.controller;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.cxgm.common.RSResult;
import com.cxgm.domain.Admin;
import com.cxgm.domain.Coupon;
import com.cxgm.domain.Product;
import com.cxgm.domain.ProductCategory;
import com.cxgm.domain.Promotion;
import com.cxgm.service.CouponService;
import com.cxgm.service.ProductCategoryService;
import com.cxgm.service.ProductService;
import com.cxgm.service.PromotionService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import net.sf.json.JSONObject;

@RestController
public class PromotionController {

	@Autowired
	PromotionService promotionService;
	
	@Autowired
	CouponService couponService;
	
	@Autowired
	ProductCategoryService productCategoryService;
	
	@Autowired
	ProductService productService;
	
	@RequestMapping(value = "/admin/promotion", method = RequestMethod.GET)
	public ModelAndView getPromotion(HttpServletRequest request,
			@RequestParam(value = "num", defaultValue = "1") Integer num) {
		PageHelper.startPage(num, 10);
		SecurityContext ctx = SecurityContextHolder.getContext();
		Authentication auth = ctx.getAuthentication();
		Admin admin = (Admin) auth.getPrincipal();
		Map<String, Object> map = new HashMap<>();
		map.put("shopId", admin.getShopId());
		List<Promotion> ptomotions = promotionService.findPromotionsWithParam(map);
		PageInfo<Promotion> pager = new PageInfo<>(ptomotions);
		request.setAttribute("pager", pager);
		request.setAttribute("admin", admin);
		return new ModelAndView("admin/promotion_list");
	}
	
	@RequestMapping(value = "/promotion/list", method = RequestMethod.POST)
	public ModelAndView couponList(HttpServletRequest request,
			@RequestParam(value = "pageNumber", defaultValue = "1") Integer num,
			@RequestParam(value = "keyword") String name, @RequestParam(value = "property") String property)
			throws SQLException {
		if (null != name && !"".equals(name)) {
			PageHelper.startPage(1, 10);
			SecurityContext ctx = SecurityContextHolder.getContext();
			Authentication auth = ctx.getAuthentication();
			Admin admin = (Admin) auth.getPrincipal();
			Map<String, Object> map = new HashMap<>();
			map.put(property, name);
			map.put("shopId", admin.getShopId());
			List<Promotion> ptomotions = promotionService.findPromotionsWithParam(map);
			PageInfo<Promotion> pager = new PageInfo<>(ptomotions);
			request.setAttribute("pager", pager);
			request.setAttribute("admin", admin);
			return new ModelAndView("admin/promotion_list");
		}
		return this.getPromotion(request, num);
	}

	@RequestMapping(value = "/promotion/delete", method = RequestMethod.GET, produces = "text/json;charset=UTF-8")
	public String promotionDelete(HttpServletRequest request) throws SQLException {
		RSResult rr = new RSResult();
		String[] promotionIds = request.getParameterValues("ids");
		int resultDelete = promotionService.delete(promotionIds);
		if (resultDelete == 1) {
			rr.setMessage("删除成功！");
			rr.setCode("200");
			rr.setStatus("success");
		} else {
			rr.setMessage("删除失败！");
			rr.setCode("0");
			rr.setStatus("failure");
		}
		return JSONObject.fromObject(rr).toString();
	}
	
	@RequestMapping(value = "/promotion/add", method = RequestMethod.GET)
	public ModelAndView promotionAdd(HttpServletRequest request) {
		SecurityContext ctx = SecurityContextHolder.getContext();
		Authentication auth = ctx.getAuthentication();
		Admin admin = (Admin) auth.getPrincipal();
		Map<String, Object> map = new HashMap<>();
		map.put("shopId", admin.getShopId());
		List<Coupon> coupons = couponService.findCouponsWithParam(map);
		List<Product> products = productService.findProducts(map);
		List<ProductCategory> productCategoryTreeList = productCategoryService.getProductCategory(0);
		request.setAttribute("productCategoryTreeList", productCategoryTreeList);
		request.setAttribute("allCoupon", coupons);
		request.setAttribute("products", products);
		request.setAttribute("shopId", admin.getShopId());
		return new ModelAndView("admin/promotion_input");
	}
	
	@RequestMapping(value = "/promotion/save", method = RequestMethod.POST)
	public ModelAndView promotionSave(HttpServletRequest request, @RequestParam(value = "promotion.name") String name,
			@RequestParam(value = "promotion.title") String title,
			@RequestParam(value = "promotion.beginDate") String beginDate,
			@RequestParam(value = "promotion.endDate") String endDate,
			@RequestParam(value = "promotion.minimumPrice",required=false) BigDecimal minimumPrice,
			@RequestParam(value = "promotion.maximumPrice",required=false) BigDecimal maximumPrice,
			@RequestParam(value = "promotion.minimumQuantity",required=false) Integer minimumQuantity,
			@RequestParam(value = "promotion.maximumQuantity",required=false) Integer maximumQuantity,
			@RequestParam(value = "promotion.priceExpression") String priceExpression,
			@RequestParam(value = "promotion.isCouponAllowed",required=false) boolean isCouponAllowed,
			@RequestParam(value = "promotion.introduction",required=false) String introduction,
			@RequestParam(value = "promotion.catetoryId") Integer catetoryId,
			@RequestParam(value = "promotion.productId") Integer productId,
			@RequestParam(value = "promotion.shop") Integer shopId) throws SQLException {
		try {
			String[] couponIds = request.getParameterValues("couponIds");
			promotionService.insert(name, title, beginDate, endDate, minimumPrice, maximumPrice, minimumQuantity, maximumQuantity, priceExpression, isCouponAllowed, introduction, catetoryId, productId,shopId, couponIds);
			ModelAndView mv = new ModelAndView("redirect:/admin/promotion");
			return mv;
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("errorMessages", "操作失误");
			request.setAttribute("redirectionUrl", "/admin/promotion");
			return new ModelAndView("admin/error");
		}
	}
	@RequestMapping(value = "/promotion/edit", method = RequestMethod.GET)
	public ModelAndView promotionEdit(HttpServletRequest request) {
		String id = request.getParameter("id");
		Promotion promotion = promotionService.select(Long.valueOf(id));
		int categoryId = promotionService.getCategoryId(promotion.getId().intValue());
		promotion.setProductCategoryId(categoryId);
		int productId = promotionService.getProductId(promotion.getId().intValue());
		promotion.setProductId(productId);
		SecurityContext ctx = SecurityContextHolder.getContext();
		Authentication auth = ctx.getAuthentication();
		Admin admin = (Admin) auth.getPrincipal();
		Map<String, Object> map = new HashMap<>();
		map.put("shopId", admin.getShopId());
		List<Coupon> coupons = couponService.findCouponsWithParam(map);
		List<Product> products = productService.findProducts(map);
		List<ProductCategory> productCategoryTreeList = productCategoryService.getProductCategory(0);
		request.setAttribute("productCategoryTreeList", productCategoryTreeList);
		request.setAttribute("allCoupon", coupons);
		request.setAttribute("shopId", admin.getShopId());
		request.setAttribute("products", products);
		request.setAttribute("promotion", promotion);
		return new ModelAndView("admin/promotion_input");
	}
	
	@RequestMapping(value = "/promotion/update", method = RequestMethod.POST)
	public ModelAndView promotionUpdate(HttpServletRequest request, @RequestParam(value = "promotion.id") Long id,
			@RequestParam(value = "promotion.name") String name,
			@RequestParam(value = "promotion.title") String title,
			@RequestParam(value = "promotion.beginDate") String beginDate,
			@RequestParam(value = "promotion.endDate") String endDate,
			@RequestParam(value = "promotion.minimumPrice") BigDecimal minimumPrice,
			@RequestParam(value = "promotion.maximumPrice") BigDecimal maximumPrice,
			@RequestParam(value = "promotion.minimumQuantity") Integer minimumQuantity,
			@RequestParam(value = "promotion.maximumQuantity") Integer maximumQuantity,
			@RequestParam(value = "promotion.priceExpression") String priceExpression,
			@RequestParam(value = "promotion.isCouponAllowed") boolean isCouponAllowed,
			@RequestParam(value = "promotion.introduction",required=false) String introduction,
			@RequestParam(value = "promotion.catetoryId") Integer catetoryId,
			@RequestParam(value = "promotion.productId") Integer productId,
			@RequestParam(value = "promotion.shop") Integer shopId) throws SQLException {
		try {
			String[] couponIds = request.getParameterValues("couponIds");
			promotionService.update(id, name, title, beginDate, endDate, minimumPrice, maximumPrice, minimumQuantity, maximumQuantity, priceExpression, isCouponAllowed, introduction, catetoryId, productId, shopId, couponIds);
			ModelAndView mv = new ModelAndView("redirect:/admin/promotion");
			return mv;
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("errorMessages", "操作失误");
			request.setAttribute("redirectionUrl", "/admin/promotion");
			return new ModelAndView("admin/error");
		}
	}
}
