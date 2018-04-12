package com.cxgm.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cxgm.common.CodeUtil;
import com.cxgm.common.DateUtil;
import com.cxgm.dao.OrderMapper;
import com.cxgm.domain.Order;
import com.cxgm.domain.OrderExample;
import com.cxgm.service.OrderService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
public class OrderServiceImpl implements OrderService{

    @Autowired
    private OrderMapper mapper;

	@Override
	public Integer addOrder(Order order) {
		
		String orderNum=DateUtil.formatDateTime2()+CodeUtil.genCodes(6);
		order.setOrderNum(orderNum);
		order.setOrderTime(new Date());
		order.setStatus("0");
		mapper.insert(order);
		return order.getId();
	}

	@Override
	public Integer updateOrder(Order order) {
		
		return null;
	}

	@Override
	public Integer deleteOrder(Integer orderId, Integer userId) {
		
        OrderExample example = new OrderExample();
		
		example.createCriteria().andUserIdEqualTo(userId).andIdEqualTo(orderId);
		
		return mapper.deleteByExample(example);
	}

	@Override
	public PageInfo<Order> orderList(Integer pageNum, Integer pageSize,Integer userId) {
		
		PageHelper.startPage(pageNum, pageSize);
		
		OrderExample example = new OrderExample();
		
		example.createCriteria().andUserIdEqualTo(userId);
		
		List<Order> result = mapper.selectByExample(example);
		
		PageInfo<Order> page = new PageInfo<Order>(result);
		
		return page;
	}


}
