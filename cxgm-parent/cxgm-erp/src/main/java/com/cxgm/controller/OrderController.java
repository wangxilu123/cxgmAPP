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
import com.cxgm.domain.Order;
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
			@RequestParam(value = "num", defaultValue = "1") Integer num,
			@RequestParam(value = "property",required=false) String property,
			@RequestParam(value = "startDate",required=false) String startDate,
			@RequestParam(value = "endDate",required=false) String endDate) {
		PageHelper.startPage(num, 10);
		SecurityContext ctx = SecurityContextHolder.getContext();
		Authentication auth = ctx.getAuthentication();
		Admin admin = (Admin) auth.getPrincipal();
		List<Order> orders = orderService.selectParseStatus(admin.getShopId());
		PageInfo<Order> pager = new PageInfo<>(orders);
		request.setAttribute("pager", pager);
		request.setAttribute("admin", admin);
		request.setAttribute("property",property);
		return new ModelAndView("admin/order_list");
	}
	
	@RequestMapping(value = "/order/list", method = RequestMethod.POST)
	public ModelAndView orderList(HttpServletRequest request,
			@RequestParam(value = "pageNumber", defaultValue = "1") Integer num,
			@RequestParam(value = "keyword") String name,
			@RequestParam(value = "property") String property,
			@RequestParam(value = "startDate",required=false) String startDate,
			@RequestParam(value = "endDate",required=false) String endDate)
			throws SQLException {
		if ("".equals(name)&&"".equals(startDate)&&"".equals(endDate)) {
			return this.getOrder(request, num,property,startDate,endDate);
		}else{
			SecurityContext ctx = SecurityContextHolder.getContext();
			Authentication auth = ctx.getAuthentication();
			Admin admin = (Admin) auth.getPrincipal();
			Map<String, Object> map = new HashMap<>();
			map.put("storeId", admin.getShopId());
			if(property.equals("orderNum")) {
				PageHelper.startPage(num, 10);
				map.put("orderNum", name);
			}else if(property.equals("status")) {
				if(name.equals("下单")) {
					map.put("status", 0);
				}else if(name.equals("已经支付")) {
					map.put("status", 1);
				}else if(name.equals("分拣中")) {
					map.put("status", 2);
				}else if(name.equals("分拣完成")) {
					map.put("status", 3);
				}else if(name.equals("配送中")) {
					map.put("status", 4);
				}else if(name.equals("配送完成")) {
					map.put("status", 5);
				}else if(name.equals("待退款")) {
					map.put("status",6);
				}else if(name.equals("已退款")) {
					map.put("status",7);
				}else if(name.equals("已取消")) {
					map.put("status",8);
				}else{
					map.put("status",name);
				}
				PageHelper.startPage(num, 10);
			}else if(property.equals("totalAmount")) {
				map.put("totalAmount",Double.valueOf(name));
				PageHelper.startPage(num, 10);
			}else if(property.equals("phone")){
				map.put("phone",name);
				PageHelper.startPage(num, 10);
			}else if(property.equals("orderResource")){
				if(name.equals("APP")) {
					map.put("orderResource", "APP");
				}else if(name.equals("有赞")) {
					map.put("orderResource", "YOUZAN");
				}else{
					map.put("orderResource",name);
				}
				PageHelper.startPage(num, 10);
			}
			map.put("startDate", startDate);
			map.put("endDate", endDate);
			List<Order> orders = orderService.findOrdersWithParam(map);
			PageInfo<Order> pager = new PageInfo<>(orders);
			request.setAttribute("pager", pager);
			request.setAttribute("admin", admin);
			request.setAttribute("property",property);
			request.setAttribute("keyword",name);
			request.setAttribute("startDate",startDate);
			request.setAttribute("endDate",endDate);
			return new ModelAndView("admin/order_list");
		}
		
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
	
	@RequestMapping(value = "/order/detail", method = RequestMethod.GET, produces = "text/json;charset=UTF-8")
	public ModelAndView productDelete(HttpServletRequest request,@RequestParam(value = "orderId") Integer orderId) throws SQLException {
		Order order = orderService.orderDetail(orderId);
		String sortingMan = orderService.getAdminName("sorting", order.getId());
		String distributionMan = orderService.getAdminName("distribution", order.getId());
		request.setAttribute("sorting", sortingMan);
		request.setAttribute("distribution", distributionMan);
		request.setAttribute("order",order);
		return new ModelAndView("admin/orderDetail");
	}
	
}
