package com.cxgm.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.math.distribution.Distribution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.cxgm.dao.OrderMapper;
import com.cxgm.dao.OrderProductMapper;
import com.cxgm.dao.ProductImageMapper;
import com.cxgm.dao.StaffSortingMapper;
import com.cxgm.domain.DistributionOrder;
import com.cxgm.domain.Order;
import com.cxgm.domain.OrderExample;
import com.cxgm.domain.OrderProductTransfer;
import com.cxgm.domain.ProductImage;
import com.cxgm.domain.StaffSorting;
import com.cxgm.domain.StaffSortingExample;
import com.cxgm.service.DistributionService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Primary
@Service
public class DistributionServiceImpl implements DistributionService {

	@Autowired
	private OrderMapper orderMapper;

	@Autowired
	private OrderProductMapper orderProductMapper;

	@Autowired
	private ProductImageMapper productImageMapper;

	@Autowired
	private StaffSortingMapper staffSortingMapper;

	@Override
	public PageInfo<DistributionOrder> orderList(Integer pageNum, Integer pageSize, Integer shopId, String status) {
		
		
		return null;
	}

	@Override
	public Integer addDistribution(Distribution distribution) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer updateStatusByOrderId(Integer orderId, String status, Integer shopId) {
		// TODO Auto-generated method stub
		return null;
	}

	

}
