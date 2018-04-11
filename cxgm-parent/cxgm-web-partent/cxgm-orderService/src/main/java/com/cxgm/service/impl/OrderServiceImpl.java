package com.cxgm.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cxgm.dao.OrderMapper;
import com.cxgm.domain.Order;
import com.cxgm.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService{

    @Autowired
    private OrderMapper mapper;

	@Override
	public Integer addOrder(Order order) {
		mapper.insert(order);
		return null;
	}

	@Override
	public Integer updateOrder(Order order) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer deleteOrder(Integer orderId, Integer userId) {
		// TODO Auto-generated method stub
		return null;
	}


}
