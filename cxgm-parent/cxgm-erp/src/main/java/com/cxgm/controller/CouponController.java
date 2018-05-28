package com.cxgm.controller;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.cxgm.common.PoiUtils;
import com.cxgm.common.RSResult;
import com.cxgm.domain.Admin;
import com.cxgm.domain.Coupon;
import com.cxgm.domain.CouponCode;
import com.cxgm.domain.Product;
import com.cxgm.domain.ProductCategory;
import com.cxgm.service.CouponCodeService;
import com.cxgm.service.CouponService;
import com.cxgm.service.ProductCategoryService;
import com.cxgm.service.ProductService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import net.sf.json.JSONObject;

@RestController
public class CouponController {

	@Autowired
	CouponService couponService;
	@Autowired
	CouponCodeService couponCodeService;
	@Autowired
	ProductCategoryService productCategoryService;
	@Autowired
	ProductService productService;

	@RequestMapping(value = "/admin/coupon", method = RequestMethod.GET)
	public ModelAndView getCoupon(HttpServletRequest request,
			@RequestParam(value = "num", defaultValue = "1") Integer num) {
		PageHelper.startPage(num, 10);
		SecurityContext ctx = SecurityContextHolder.getContext();
		Authentication auth = ctx.getAuthentication();
		Admin admin = (Admin) auth.getPrincipal();
		Map<String, Object> map = new HashMap<>();
		map.put("shopId", admin.getShopId());
		List<Coupon> coupons = couponService.findCouponsWithParam(map);
		PageInfo<Coupon> pager = new PageInfo<>(coupons);
		request.setAttribute("pager", pager);
		request.setAttribute("admin", admin);
		return new ModelAndView("admin/coupon_list");
	}

	@RequestMapping(value = "/coupon/list", method = RequestMethod.POST)
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
			map.put("name", name);
			map.put("shopId", admin.getShopId());
			List<Coupon> coupons = couponService.findCouponsWithParam(map);
			PageInfo<Coupon> pager = new PageInfo<>(coupons);
			request.setAttribute("pager", pager);
			request.setAttribute("admin", admin);
			return new ModelAndView("admin/coupon_list");
		}
		return this.getCoupon(request, num);
	}

	@RequestMapping(value = "/coupon/add", method = RequestMethod.GET)
	public ModelAndView couponAdd(HttpServletRequest request) {
		SecurityContext ctx = SecurityContextHolder.getContext();
		Authentication auth = ctx.getAuthentication();
		Admin admin = (Admin) auth.getPrincipal();
		Map<String, Object> map = new HashMap<>();
		map.put("shopId", admin.getShopId());
		List<Product> products = productService.findProducts(map);
		List<ProductCategory> productCategoryTreeList = productCategoryService.getProductCategory(0);
		request.setAttribute("productCategoryTreeList", productCategoryTreeList);
		request.setAttribute("shopId", admin.getShopId());
		request.setAttribute("products", products);
		return new ModelAndView("admin/coupon_input");
	}

	@RequestMapping(value = "/coupon/save", method = RequestMethod.POST)
	public ModelAndView couponSave(HttpServletRequest request, @RequestParam(value = "coupon.name") String name,
			@RequestParam(value = "coupon.prefix") String prefix,
			@RequestParam(value = "coupon.beginDate") String beginDate,
			@RequestParam(value = "coupon.endDate") String endDate,
			@RequestParam(value = "coupon.minimumPrice") BigDecimal minimumPrice,
			@RequestParam(value = "coupon.maximumPrice") BigDecimal maximumPrice,
			@RequestParam(value = "coupon.minimumQuantity") Integer minimumQuantity,
			@RequestParam(value = "coupon.maximumQuantity") Integer maximumQuantity,
			@RequestParam(value = "coupon.isEnabled") boolean isEnabled, @RequestParam(value = "parentId",required=false) Long pid,
			@RequestParam(value = "coupon.priceExpression") String priceExpression,
			@RequestParam(value = "coupon.introduction",required=false) String introduction,
			@RequestParam(value = "coupon.shop") Integer shopId,
			@RequestParam(value = "coupon.productId",required=false) Long productId) throws SQLException {
		try {
			if(pid==-1)pid=null;
			if(productId==-1)productId=null;
			couponService.insert(name, prefix, beginDate, endDate, minimumPrice, maximumPrice, minimumQuantity,
					maximumQuantity, isEnabled, pid, priceExpression, introduction, shopId,productId);
			ModelAndView mv = new ModelAndView("redirect:/admin/coupon");
			return mv;
		} catch (Exception e) {
			request.setAttribute("errorMessages", e.getMessage());
			request.setAttribute("redirectionUrl", "/admin/coupon");
			return new ModelAndView("admin/error");
		}
	}

	@RequestMapping(value = "/coupon/delete", method = RequestMethod.GET, produces = "text/json;charset=UTF-8")
	public String couponDelete(HttpServletRequest request) throws SQLException {
		RSResult rr = new RSResult();
		String[] couponIds = request.getParameterValues("ids");
		int resultDelete = couponService.delete(couponIds);
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
	
	@RequestMapping(value = "/coupon/edit", method = RequestMethod.GET)
	public ModelAndView couponEdit(HttpServletRequest request) {
		String id = request.getParameter("id");
		Coupon coupon = couponService.select(Long.valueOf(id));
		SecurityContext ctx = SecurityContextHolder.getContext();
		Authentication auth = ctx.getAuthentication();
		Admin admin = (Admin) auth.getPrincipal();
		Map<String, Object> map = new HashMap<>();
		map.put("shopId", admin.getShopId());
		List<Product> products = productService.findProducts(map);
		List<ProductCategory> productCategoryTreeList = productCategoryService.getProductCategory(0);
		request.setAttribute("productCategoryTreeList", productCategoryTreeList);
		request.setAttribute("shopId", admin.getShopId());
		request.setAttribute("coupon", coupon);
		request.setAttribute("products", products);
		return new ModelAndView("admin/coupon_input");
	}
	
	@RequestMapping(value = "/coupon/update", method = RequestMethod.POST)
	public ModelAndView couponUpdate(HttpServletRequest request, 
			@RequestParam(value = "coupon.id") Long id,
			@RequestParam(value = "coupon.name") String name,
			@RequestParam(value = "coupon.prefix") String prefix,
			@RequestParam(value = "coupon.beginDate") String beginDate,
			@RequestParam(value = "coupon.endDate") String endDate,
			@RequestParam(value = "coupon.minimumPrice") BigDecimal minimumPrice,
			@RequestParam(value = "coupon.maximumPrice") BigDecimal maximumPrice,
			@RequestParam(value = "coupon.minimumQuantity") Integer minimumQuantity,
			@RequestParam(value = "coupon.maximumQuantity") Integer maximumQuantity,
			@RequestParam(value = "coupon.isEnabled") boolean isEnabled, @RequestParam(value = "parentId",required=false) Long pid,
			@RequestParam(value = "coupon.priceExpression") String priceExpression,
			@RequestParam(value = "coupon.introduction",required=false) String introduction,
			@RequestParam(value = "coupon.shop") Integer shopId,
			@RequestParam(value = "coupon.productId",required=false) Long productId) throws SQLException {
		try {
			if(pid==-1)pid=null;
			if(productId==-1)productId=null;
			couponService.update(id, name, prefix, beginDate, endDate, minimumPrice, maximumPrice, minimumQuantity, maximumQuantity, isEnabled, pid, priceExpression, introduction, shopId,productId);
			ModelAndView mv = new ModelAndView("redirect:/admin/coupon");
			return mv;
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("errorMessages", e.getMessage());
			request.setAttribute("redirectionUrl", "/admin/coupon");
			return new ModelAndView("admin/error");
		}
	}
	
	@RequestMapping(value = "/couponcode/add", method = RequestMethod.GET)
	public ModelAndView couponcodeAdd(HttpServletRequest request) {
		String id = request.getParameter("id");
		String type = request.getParameter("type");
		Coupon coupon = couponService.select(Long.valueOf(id));
		request.setAttribute("coupon", coupon);
		request.setAttribute("type", type);
		return new ModelAndView("admin/coupon_code_input");
	}
	@RequestMapping(value = "/couponcode/save", method = RequestMethod.POST)
	public ModelAndView couponcodeSave(HttpServletRequest request,
			@RequestParam(value = "coupon.id",required=true) Long couponid,
			@RequestParam(value = "coupon.codes",required=true) Integer codesNumber,
			@RequestParam(value = "type",required=true) Integer type) {
		try {
			couponService.couponCodeInsert(couponid, codesNumber, type);
			ModelAndView mv = new ModelAndView("redirect:/admin/coupon");
			return mv;
		}catch(Exception e) {
			request.setAttribute("errorMessages", e.getMessage());
			request.setAttribute("redirectionUrl", "/admin/coupon");
			return new ModelAndView("admin/error");
		}
	}
	
	@RequestMapping(value = "/coupon/couponcode", method = RequestMethod.GET)
	public ModelAndView getCouponCode(HttpServletRequest request) {
		String id = request.getParameter("id");
		Coupon coupon = couponService.select(Long.valueOf(id));
		int totalNumber  = couponService.findCouponCodeListCount(coupon.getId(), null);
		int usedNumber = couponService.findCouponCodeListCount(coupon.getId(), 2);
		int unusedNumber = couponService.findCouponCodeListCount(coupon.getId(), 1);
		int dispatchNumber = couponService.findCouponCodeDispatch(coupon.getId());
		int overdueNumber = couponService.findCouponCodeListCount(coupon.getId(), 3);
		coupon.setTotalNumber(totalNumber);
		coupon.setUsedNumber(usedNumber);
		coupon.setUnusedNumber(unusedNumber);
		coupon.setDispatchNumber(dispatchNumber);
		coupon.setOverdueNumber(overdueNumber);
		request.setAttribute("coupon", coupon);
		return new ModelAndView("admin/coupon_code_list");
	}
	
	@RequestMapping(value = "/coupon/export", method = RequestMethod.GET)
	public ResponseEntity<byte[]> exportCouponCode(HttpServletRequest request) {
		String id = request.getParameter("id");
		Map<String,Object> map = new HashMap<>();
		map.put("couponId", id);
		map.put("type", 1);
		List<CouponCode> emps = couponCodeService.findCouponsWithParam(map);
		return PoiUtils.exportCoupon2Excel(emps);
	}
}
