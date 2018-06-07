package com.cxgm.service;

import com.cxgm.domain.Order;
import com.cxgm.domain.StaffSorting;
import com.github.pagehelper.PageInfo;

public interface SortingService {

	PageInfo<Order> orderList(Integer pageNum, Integer pageSize,Integer shopId ,String status);
	
	Integer addSorting(StaffSorting staffSorting);
	
	Integer updateStatusByOrderId(Integer orderId,String status,Integer shopId);
	
	Order orderDetail(Integer orderId);
	
}
