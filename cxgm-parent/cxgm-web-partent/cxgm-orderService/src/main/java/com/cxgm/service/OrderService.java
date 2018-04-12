package com.cxgm.service;

import com.cxgm.domain.Order;
import com.github.pagehelper.PageInfo;

public interface OrderService {
  
	Integer addOrder(Order order);
	
	Integer updateOrder(Order order);
	
    Integer deleteOrder(Integer orderId,Integer userId);
    
    PageInfo<Order> orderList(Integer pageNum, Integer pageSize,Integer userId);
}
