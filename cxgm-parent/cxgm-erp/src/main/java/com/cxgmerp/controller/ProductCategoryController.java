package com.cxgmerp.controller;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.cxgmerp.domain.ProductCategory;
import com.cxgmerp.service.ProductCategoryService;

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
	
	
}
