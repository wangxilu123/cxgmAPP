package com.cxgm.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cxgm.common.WeixinReturnMonery;
import com.cxgm.dao.OrderMapper;
import com.cxgm.domain.Order;
import com.cxgm.domain.OrderExample;


@Service
public class OrderServiceErp {
	
	@Autowired
	private OrderMapper orderDao;
	
	public List<Order> findAllOrders(Integer storeId){
		OrderExample example = new OrderExample();
		example.createCriteria().andStoreIdEqualTo(storeId);
		example.setOrderByClause("order_time desc");
		return orderDao.selectByExample(example);
	}
	
	public List<Order> selectParseStatus(Integer storeId){
		return orderDao.selectParseStatus(storeId);
	}
	
	public List<Order> findOrdersWithParam(Map<String,Object> map){
		return orderDao.findOrdersWithParam(map);
	}

	public Long countByExample(OrderExample example) {
		return orderDao.countByExample(example);
	}
	
	public Integer updateOrderStatus(Integer id, Integer status) {
		OrderExample orderExample = new OrderExample();
		orderExample.createCriteria().andIdEqualTo(id);
		List<Order> orders = orderDao.selectByExample(orderExample);
		if(orders.size()>0) {
			Order order = orders.get(0);
			order.setStatus(String.valueOf(status));
			
			Integer num = orderDao.updateByExample(order, orderExample);
			
			if(num==1){
				
				WeixinReturnMonery.wechatRefund(order);//退款
				
			}
			
			return num;
		}
		return null;
	}
}
