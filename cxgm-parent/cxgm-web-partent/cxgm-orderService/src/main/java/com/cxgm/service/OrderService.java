package com.cxgm.service;

import com.cxgm.domain.Order;

public interface OrderService {
  
	Integer addOrder(Order order);
	
	Integer updateOrder(Order order);
	
    Integer deleteOrder(Integer orderId,Integer userId);
}
