package com.cxgm.service;

import org.apache.commons.math.distribution.Distribution;

import com.cxgm.domain.DistributionOrder;
import com.github.pagehelper.PageInfo;

public interface DistributionService {

	PageInfo<DistributionOrder> orderList(Integer pageNum, Integer pageSize,Integer shopId ,String status);
	
	Integer addDistribution(Distribution distribution);
	
	Integer updateStatusByOrderId(Integer orderId,String status,Integer shopId);
	
}
