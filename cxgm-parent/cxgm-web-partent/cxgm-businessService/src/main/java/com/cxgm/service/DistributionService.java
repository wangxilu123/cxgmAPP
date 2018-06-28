package com.cxgm.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.xml.rpc.ServiceException;
import javax.xml.soap.SOAPException;

import com.cxgm.domain.DistributionOrder;
import com.cxgm.domain.Order;
import com.cxgm.domain.StaffDistribution;
import com.github.pagehelper.PageInfo;

public interface DistributionService {

	PageInfo<DistributionOrder> orderList(Integer pageNum, Integer pageSize,Integer shopId ,String status);
	
	Integer addDistribution(StaffDistribution distribution);
	
	Integer updateStatusByOrderId(Integer orderId) throws UnsupportedEncodingException, SOAPException, ServiceException, IOException;

	Integer cancelOrder(Integer orderId, String cancelReason);

	Order orderDetail(Integer orderId);
	
}
