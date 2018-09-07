package com.cxgm.controller;

import java.sql.SQLException;
import java.util.ArrayList;
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

import com.cxgm.common.RSResult;
import com.cxgm.common.SystemConfig;
import com.cxgm.domain.Admin;
import com.cxgm.domain.Product;
import com.cxgm.domain.ProductCategory;
import com.cxgm.domain.ProductImage;
import com.cxgm.service.ProductCategoryService;
import com.cxgm.service.ProductService;

import net.sf.json.JSONObject;

@RestController
public class ProductCategoryController {

	@Autowired
	ProductCategoryService productCategoryService;
	
	@Autowired
	ProductService productService;
	
	
	@RequestMapping(value = "/admin/productCategory", method = RequestMethod.GET)
	public ModelAndView getProductCategory(HttpServletRequest request) throws SQLException {
		SecurityContext ctx = SecurityContextHolder.getContext();  
	    Authentication auth = ctx.getAuthentication(); 
	    Admin admin = (Admin) auth.getPrincipal();
		List<ProductCategory> productCategoryTreeList = productCategoryService.getProductCategory(0);
		request.setAttribute("productCategoryTreeList", productCategoryTreeList);
		request.setAttribute("admin", admin);
		return new ModelAndView("admin/product_category_list");
	}
	
	@RequestMapping(value = "/productCategory/add", method = RequestMethod.GET)
	public ModelAndView productCategoryAdd(HttpServletRequest request) throws SQLException {
		List<ProductCategory> productCategoryTreeList = new ArrayList<>();
		productCategoryTreeList = productCategoryService.getProductCategory(0);
		SystemConfig systemConfig = new SystemConfig();
		systemConfig.setUploadLimit(10);
		systemConfig.setAllowedUploadImageExtension("png,jpg");
		request.setAttribute("systemConfig",systemConfig);
		request.setAttribute("productCategoryTreeList", productCategoryTreeList);
		return new ModelAndView("admin/product_category_input");
	}
	
	@RequestMapping(value = "/productCategory/save", method = RequestMethod.POST)
	public ModelAndView productCategorySave(HttpServletRequest request,@RequestParam(value="parentId") String parentId,
			@RequestParam(value="productCategory.name") String name) throws SQLException {
		List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("productImages");
		try {
			productCategoryService.insert(Long.valueOf(parentId), name,files);
			ModelAndView mv = new ModelAndView("redirect:/admin/productCategory");
			return mv;
		}catch(Exception e) {
			request.setAttribute("errorMessages", e.getMessage());
			request.setAttribute("redirectionUrl", "/admin/productCategory");
			return new ModelAndView("admin/error");
		}
	}
	
	
	@RequestMapping(value = "/productCategory/update", method = RequestMethod.POST)
	public ModelAndView productCategoryUpdate(HttpServletRequest request,@RequestParam(value="parentId") String parentId,
			@RequestParam(value="productCategory.name") String name,
			@RequestParam(value="productCategory.id") String id) throws SQLException {
		List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("productImages");
		String[] productImageIds = request.getParameterValues("productImageIds");
		try {
			productCategoryService.update(Long.valueOf(id),Long.valueOf(parentId), name, files, productImageIds);
			
			productService.updateCategory(name,id);
			ModelAndView mv = new ModelAndView("redirect:/admin/productCategory");
			return mv;
		}catch(Exception e) {
			request.setAttribute("errorMessages", e.getMessage());
			request.setAttribute("redirectionUrl", "/admin/productCategory");
			return new ModelAndView("admin/error");
		}
	}
	
	@RequestMapping(value = "/productCategory/delete", method = RequestMethod.POST,produces = "text/json;charset=UTF-8")
	public String productCategoryDelete(HttpServletRequest request) throws SQLException {
		RSResult rr = new RSResult();
		String id = request.getParameter("id");
		int resultDelete = productCategoryService.delete(Long.valueOf(id));
		if (resultDelete == 1) {
			rr.setMessage("删除成功！");
			rr.setCode("200");
			rr.setStatus("success");
		}else {
			rr.setMessage("删除失败！");
			rr.setCode("404");
			rr.setStatus("failure");
		}
		return JSONObject.fromObject(rr).toString();
	}
	
	@RequestMapping(value = "/productCategory/edit", method = RequestMethod.GET)
	public ModelAndView productCategoryEdit(HttpServletRequest request,@RequestParam(value="id") String id) throws SQLException {
		ProductCategory productCategory = productCategoryService.findById(Long.valueOf(id));
		List<ProductCategory> productCategoryTreeList = productCategoryService.getProductCategory(0);
		SystemConfig systemConfig = new SystemConfig();
		systemConfig.setUploadLimit(10);
		systemConfig.setAllowedUploadImageExtension("png,jpg");
		List<ProductImage> productImages = productCategoryService.getAllAttachmentImage(productCategory);
		productCategory.setProductImageList(productImages);
		request.setAttribute("productCategoryTreeList", productCategoryTreeList);
		request.setAttribute("productCategory", productCategory);
		request.setAttribute("systemConfig",systemConfig);
		return new ModelAndView("admin/product_category_input");
	}
}
