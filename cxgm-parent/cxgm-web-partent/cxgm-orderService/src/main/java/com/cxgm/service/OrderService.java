package com.cxgm.service;

import java.math.BigDecimal;
import java.util.List;

import com.cxgm.domain.CategoryAndAmount;
import com.cxgm.domain.CouponDetail;
import com.cxgm.domain.Order;
import com.cxgm.domain.OrderProduct;
import com.github.pagehelper.PageInfo;

public interface OrderService {
  
	Integer addOrder(Order order);
	
	Integer updateOrder(Order order);
	
    PageInfo<Order> orderList(Integer pageNum, Integer pageSize,Integer userId,String status);
    
    Order findById(Integer orderId);
    
    List<CouponDetail> checkCoupons(Integer userId,List<OrderProduct> productList,List<CategoryAndAmount> amountList,BigDecimal totalAmount);

	Integer returnMoney(Integer orderId);

	Order orderDetail(Integer userId, Integer orderId);
}
