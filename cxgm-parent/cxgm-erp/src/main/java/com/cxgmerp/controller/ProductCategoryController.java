package com.cxgmerp.controller;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.cxgmerp.common.RSResult;
import com.cxgmerp.domain.ProductCategory;
import com.cxgmerp.service.ProductCategoryService;

import net.sf.json.JSONObject;

@RestController
public class ProductCategoryController {

	@Autowired
	ProductCategoryService productCategoryService;
	
	
	@RequestMapping(value = "/admin/productCategory", method = RequestMethod.GET)
	public ModelAndView getProductCategory(HttpServletRequest request) throws SQLException {
		List<ProductCategory> productCategoryTreeList = productCategoryService.getProductCategory(0);
		request.setAttribute("productCategoryTreeList", productCategoryTreeList);
		return new ModelAndView("admin/product_category_list");
	}
	
	@RequestMapping(value = "/productCategory/add", method = RequestMethod.GET)
	public ModelAndView productCategoryAdd(HttpServletRequest request) throws SQLException {
		List<ProductCategory> productCategoryTreeList = productCategoryService.getProductCategory(0);
		request.setAttribute("productCategoryTreeList", productCategoryTreeList);
		return new ModelAndView("admin/product_category_input");
	}
	
	@RequestMapping(value = "/productCategory/save", method = RequestMethod.POST)
	public ModelAndView productCategorySave(HttpServletRequest request,@RequestParam(value="parentId") String parentId,
			@RequestParam(value="productCategory.name") String name) throws SQLException {
		try {
			productCategoryService.insert(Long.valueOf(parentId), name);
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
		try {
			productCategoryService.update(Long.valueOf(id),Long.valueOf(parentId), name);
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
		request.setAttribute("productCategoryTreeList", productCategoryTreeList);
		request.setAttribute("productCategory", productCategory);
		return new ModelAndView("admin/product_category_input");
	}
}
