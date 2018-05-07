package com.cxgm.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.cxgm.common.CodeUtil;
import com.cxgm.common.DateUtil;
import com.cxgm.dao.OrderMapper;
import com.cxgm.dao.OrderProductMapper;
import com.cxgm.dao.ProductMapper;
import com.cxgm.domain.Order;
import com.cxgm.domain.OrderExample;
import com.cxgm.domain.OrderProduct;
import com.cxgm.domain.OrderProductTransfer;
import com.cxgm.domain.Product;
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
    
    @Autowired
    private ProductMapper productMapper;

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
			//修改销量
			Product product = productMapper.findProductById((long)orderProduct.getProductId());
			
			product.setSales(product.getSales()+orderProduct.getProductNum());
			
			productMapper.update(product);
			
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
			
			List<OrderProductTransfer> productList=orderProductMapper.selectOrderDetail(order.getId());
			
			for(OrderProductTransfer orderDetail :productList){
				
				BigDecimal price = orderDetail.getPrice();
				
				BigDecimal num = new BigDecimal(orderDetail.getProductNum());
				
				orderDetail.setAmount(price.multiply(num));
				
			}
			
			order.setProductDetails(productList);
			
		}
		
		PageInfo<Order> page = new PageInfo<Order>(list);
		
		return page;
	}


}
