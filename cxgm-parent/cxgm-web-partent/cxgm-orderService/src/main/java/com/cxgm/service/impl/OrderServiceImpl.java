package com.cxgm.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.cxgm.common.CodeUtil;
import com.cxgm.common.DateUtil;
import com.cxgm.dao.OrderMapper;
import com.cxgm.dao.OrderProductMapper;
import com.cxgm.domain.Order;
import com.cxgm.domain.OrderExample;
import com.cxgm.domain.OrderProduct;
import com.cxgm.domain.OrderProductExample;
import com.cxgm.service.OrderService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Primary
@Service
public class OrderServiceImpl implements OrderService{

    @Autowired
    private OrderMapper orderMapper;
    
    @Autowired
    private OrderProductMapper orderProductMapper;

	@Override
	public Integer addOrder(Order order) {
		
		String orderNum=DateUtil.formatDateTime2()+CodeUtil.genCodes(6);
		order.setOrderNum(orderNum);
		order.setOrderTime(new Date());
		order.setStatus("0");
		orderMapper.insert(order);
		
		for(OrderProduct orderProduct : order.getProductList()){
			
			orderProduct.setOrderId(order.getId());
			orderProduct.setCreateTime(new Date());
			orderProductMapper.insert(orderProduct);
		}
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
		
		return orderMapper.deleteByExample(example);
	}

	@Override
	public PageInfo<Order> orderList(Integer pageNum, Integer pageSize,Integer userId) {
		
		PageHelper.startPage(pageNum, pageSize);
		
		OrderExample example = new OrderExample();
		
		example.createCriteria().andUserIdEqualTo(userId);
		
		List<Order> list = orderMapper.selectByExample(example);
		
		for(Order order : list){
			//根据orderId查询订单详情信息
			
			OrderProductExample example1 = new OrderProductExample();
			
			example1.createCriteria().andOrderIdEqualTo(order.getId());
			
			List<OrderProduct> productList=orderProductMapper.selectByExample(example1);
			
		}
		
		PageInfo<Order> page = new PageInfo<Order>(list);
		
		return page;
	}


}
