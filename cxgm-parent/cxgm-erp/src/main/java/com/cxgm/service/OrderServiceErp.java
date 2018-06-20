package com.cxgm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
