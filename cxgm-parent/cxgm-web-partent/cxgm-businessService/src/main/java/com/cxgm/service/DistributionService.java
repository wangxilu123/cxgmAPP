package com.cxgm.service;

import com.cxgm.domain.DistributionOrder;
import com.cxgm.domain.StaffDistribution;
import com.github.pagehelper.PageInfo;

public interface DistributionService {

	PageInfo<DistributionOrder> orderList(Integer pageNum, Integer pageSize,Integer shopId ,String status);
	
	Integer addDistribution(StaffDistribution distribution);
	
	Integer updateStatusByOrderId(Integer orderId);

	Integer cancelOrder(Integer orderId, String cancelReason);
	
}
