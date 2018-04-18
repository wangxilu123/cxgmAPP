package com.cxgm.controller;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.cxgm.domain.ProductTransfer;
import com.cxgm.service.ProductService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@RestController
public class ProductController {

	@Autowired
	ProductService productService;
	
	
	@RequestMapping(value = "/admin/product/product", method = RequestMethod.GET)
	public ModelAndView getProduct(HttpServletRequest request,
			@RequestParam(value = "num", defaultValue = "1") Integer num) throws SQLException {
		PageHelper.startPage(num, 10);
		List<ProductTransfer> products = productService.findListAllWithCategory();
		PageInfo<ProductTransfer> pager = new PageInfo<>(products);
		request.setAttribute("pager", pager);
		return new ModelAndView("admin/product_list");
	}
}
