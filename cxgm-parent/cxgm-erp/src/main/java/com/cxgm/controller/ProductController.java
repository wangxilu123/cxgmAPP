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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.cxgm.common.SystemConfig;
import com.cxgm.domain.Admin;
import com.cxgm.domain.ProductCategory;
import com.cxgm.domain.ProductTransfer;
import com.cxgm.service.ProductCategoryService;
import com.cxgm.service.ProductService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@RestController
public class ProductController {

	@Autowired
	ProductService productService;
	@Autowired
	ProductCategoryService productCategoryService;
	
	
	@RequestMapping(value = "/admin/product/product", method = RequestMethod.GET)
	public ModelAndView getProduct(HttpServletRequest request,
			@RequestParam(value = "num", defaultValue = "1") Integer num) throws SQLException {
		PageHelper.startPage(num, 10);
		SecurityContext ctx = SecurityContextHolder.getContext();  
	    Authentication auth = ctx.getAuthentication(); 
	    Admin admin = (Admin) auth.getPrincipal();
	    Map<String,Object> map = new HashMap<>();
	    map.put("shopId", admin.getShopId());
		List<ProductTransfer> products = productService.findListAllWithCategory(map);
		PageInfo<ProductTransfer> pager = new PageInfo<>(products);
		request.setAttribute("pager", pager);
		request.setAttribute("admin", admin);
		return new ModelAndView("admin/product_list");
	}
	
	@RequestMapping(value = "/product/add", method = RequestMethod.GET)
	public ModelAndView productAdd(HttpServletRequest request) {
		SecurityContext ctx = SecurityContextHolder.getContext();  
	    Authentication auth = ctx.getAuthentication(); 
	    Admin admin = (Admin) auth.getPrincipal();
		List<ProductCategory> productCategoryTreeList = productCategoryService.getProductCategory(0,admin.getShopId());
		String shopId = request.getParameter("product.shop");
		request.setAttribute("productCategoryTreeList", productCategoryTreeList);
		request.setAttribute("shopId", shopId);
		SystemConfig systemConfig = new SystemConfig();
		systemConfig.setUploadLimit(10);
		systemConfig.setAllowedUploadImageExtension("png,jpg");
		
		request.setAttribute("systemConfig",systemConfig);
		return new ModelAndView("admin/product_input");
	}
	
	@RequestMapping(value = "/product/save", method = RequestMethod.POST)
	public ModelAndView productSave(HttpServletRequest request,
			@RequestParam(value = "product.name") String name,
			@RequestParam(value = "product.productSn") String productSn,
			@RequestParam(value = "product.originPlace") String originPlace,
			@RequestParam(value = "product.storageCondition") String storageCondition,
			@RequestParam(value = "product.descriptionWeight") String descriptionWeight,
			@RequestParam(value = "parentId") String pid,
			@RequestParam(value = "product.brand") String brand,
			@RequestParam(value = "product.price") BigDecimal price,
			@RequestParam(value = "product.marketPrice") BigDecimal marketPrice,
			@RequestParam(value = "product.weight") Integer weight,
			@RequestParam(value = "weightUnit") String unit,
			@RequestParam(value = "product.stock") Integer stock,
			@RequestParam(value = "product.isMarketable") boolean isMarketable,
			@RequestParam(value = "product.introduction") String introduction,
			@RequestParam(value = "product.shop") Integer shop
			) throws SQLException {
		
		List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("productImages");
		try {
			productService.insert(name, productSn, originPlace, storageCondition,
					descriptionWeight, pid, brand, price, marketPrice, weight, unit, 
					stock, isMarketable, introduction, shop, files);
			ModelAndView mv = new ModelAndView("redirect:/admin/product/product");
			return mv;
		}catch(Exception e) {
			request.setAttribute("errorMessages", e.getMessage());
			request.setAttribute("redirectionUrl", "/admin/product/product");
			return new ModelAndView("admin/error");
		}
	}
	
}
