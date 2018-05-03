package com.cxgm.controller;

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
import com.cxgm.domain.Promotion;
import com.cxgm.service.CouponService;
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
		request.setAttribute("allCoupon", coupons);
		request.setAttribute("shopId", admin.getShopId());
		return new ModelAndView("admin/promotion_input");
	}
}
