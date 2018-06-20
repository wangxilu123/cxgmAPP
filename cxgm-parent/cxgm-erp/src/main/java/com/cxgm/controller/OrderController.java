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

import com.cxgm.domain.Admin;
import com.cxgm.domain.Article;
import com.cxgm.domain.Order;
import com.cxgm.service.OrderServiceErp;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@RestController
public class OrderController {

	@Autowired
	private OrderServiceErp orderService;
	
	@RequestMapping(value = "/admin/order", method = RequestMethod.GET)
	public ModelAndView getOrder(HttpServletRequest request,
			@RequestParam(value = "num", defaultValue = "1") Integer num) {
		PageHelper.startPage(num, 10);
		SecurityContext ctx = SecurityContextHolder.getContext();
		Authentication auth = ctx.getAuthentication();
		Admin admin = (Admin) auth.getPrincipal();
		List<Order> orders = orderService.findAllOrders(admin.getShopId());
		PageInfo<Order> pager = new PageInfo<>(orders);
		request.setAttribute("pager", pager);
		request.setAttribute("admin", admin);
		return new ModelAndView("admin/order_list");
	}
	
	@RequestMapping(value = "/order/list", method = RequestMethod.POST)
	public ModelAndView orderList(HttpServletRequest request,
			@RequestParam(value = "pageNumber", defaultValue = "1") Integer num,
			@RequestParam(value = "keyword") String name, @RequestParam(value = "property") String property)
			throws SQLException {
		if (null != name && !"".equals(name)) {
			PageHelper.startPage(1, 10);
			SecurityContext ctx = SecurityContextHolder.getContext();
			Authentication auth = ctx.getAuthentication();
			Admin admin = (Admin) auth.getPrincipal();
//			Map<String, Object> map = new HashMap<>();
//			map.put("name", name);
//			map.put("shopId", admin.getShopId());
//			List<Article> articles = articleService.findArticlesByParam(map);
//			PageInfo<Article> pager = new PageInfo<>(articles);
//			request.setAttribute("pager", pager);
			request.setAttribute("admin", admin);
			return new ModelAndView("admin/order_list");
		}
		return this.getOrder(request, num);
	}
}
