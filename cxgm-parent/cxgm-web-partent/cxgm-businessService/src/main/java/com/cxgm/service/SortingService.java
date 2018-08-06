package com.cxgm.service;

import com.cxgm.domain.Order;
import com.cxgm.domain.StaffSorting;
import com.github.pagehelper.PageInfo;

public interface SortingService {

	PageInfo<Order> orderList(Integer pageNum, Integer pageSize,Integer shopId ,String status,Integer adminId);
	
	Integer addSorting(StaffSorting staffSorting);
	
	Integer updateStatusByOrderId(Integer orderId,Integer shopId);
	
	Order orderDetail(Integer orderId);

	Integer cancelOrder(Integer orderId, String cancelReason);
	
}
