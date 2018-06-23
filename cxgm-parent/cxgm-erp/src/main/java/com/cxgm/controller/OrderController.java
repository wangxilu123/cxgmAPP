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
import com.cxgm.domain.Order;
import com.cxgm.domain.OrderExample;
import com.cxgm.service.OrderServiceErp;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import net.sf.json.JSONObject;

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
		List<Order> orders = orderService.selectParseStatus(admin.getShopId());
		PageInfo<Order> pager = new PageInfo<>(orders);
		request.setAttribute("pager", pager);
		request.setAttribute("admin", admin);
		return new ModelAndView("admin/order_list");
	}
	
	@RequestMapping(value = "/order/list", method = RequestMethod.POST)
	public ModelAndView orderList(HttpServletRequest request,
			@RequestParam(value = "pageNumber", defaultValue = "1") Integer num,
			@RequestParam(value = "keyword") String name,
			@RequestParam(value = "property") String property)
			throws SQLException {
		if (null != name && !"".equals(name) ) {
			SecurityContext ctx = SecurityContextHolder.getContext();
			Authentication auth = ctx.getAuthentication();
			Admin admin = (Admin) auth.getPrincipal();
			Map<String, Object> map = new HashMap<>();
			map.put("storeId", admin.getShopId());
			if(property.equals("orderNum")) {
				PageHelper.startPage(1, 10);
				map.put("orderNum", name);
			}else if(property.equals("status")) {
				OrderExample orderExample = new OrderExample();
				if(name.equals("下单")) {
					map.put("status", 0);
					orderExample.createCriteria().andStatusEqualTo("0");
				}else if(name.equals("已经支付")) {
					map.put("status", 1);
					orderExample.createCriteria().andStatusEqualTo("1");
				}else if(name.equals("分拣中")) {
					map.put("status", 2);
					orderExample.createCriteria().andStatusEqualTo("2");
				}else if(name.equals("分拣完成")) {
					map.put("status", 3);
					orderExample.createCriteria().andStatusEqualTo("3");
				}else if(name.equals("配送中")) {
					map.put("status", 4);
					orderExample.createCriteria().andStatusEqualTo("4");
				}else if(name.equals("配送完成")) {
					map.put("status", 5);
					orderExample.createCriteria().andStatusEqualTo("5");
				}else if(name.equals("待退款")) {
					map.put("status",6);
					orderExample.createCriteria().andStatusEqualTo("6");
				}else if(name.equals("已退款")) {
					map.put("status",7);
					orderExample.createCriteria().andStatusEqualTo("7");
				}
				PageHelper.startPage(1, orderService.countByExample(orderExample).intValue());
			}else if(property.equals("totalAmount")) {
				map.put("totalAmount",Double.valueOf(name));
				OrderExample orderExample = new OrderExample();
				orderExample.createCriteria().andTotalAmountEqualTo(BigDecimal.valueOf(Double.valueOf(name)));
				PageHelper.startPage(1, orderService.countByExample(orderExample).intValue());
			}
			List<Order> orders = orderService.findOrdersWithParam(map);
			PageInfo<Order> pager = new PageInfo<>(orders);
			request.setAttribute("pager", pager);
			request.setAttribute("admin", admin);
			return new ModelAndView("admin/order_list");
		}
		return this.getOrder(request, num);
	}
	
	@RequestMapping(value = "/order/process", method = RequestMethod.GET, produces = "text/json;charset=UTF-8")
	public String productDelete(HttpServletRequest request) throws SQLException {
		RSResult rr = new RSResult();
		String orderId = request.getParameter("orders.id");
		int resultUpdate =  orderService.updateOrderStatus(Integer.valueOf(orderId), 7);//7已退款
		if (resultUpdate == 1) {
			rr.setMessage("更新成功！");
			rr.setCode("200");
			rr.setStatus("success");
		} else {
			rr.setMessage("更新失败！");
			rr.setCode("0");
			rr.setStatus("failure");
		}
		return JSONObject.fromObject(rr).toString();
	}
	
}
