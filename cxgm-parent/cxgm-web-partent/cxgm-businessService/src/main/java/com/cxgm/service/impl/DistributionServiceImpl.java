package com.cxgm.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.cxgm.dao.OrderMapper;
import com.cxgm.dao.StaffDistributionMapper;
import com.cxgm.dao.UserAddressMapper;
import com.cxgm.domain.DistributionOrder;
import com.cxgm.domain.Order;
import com.cxgm.domain.OrderExample;
import com.cxgm.domain.StaffDistribution;
import com.cxgm.domain.UserAddress;
import com.cxgm.domain.UserAddressExample;
import com.cxgm.service.DistributionService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Primary
@Service
public class DistributionServiceImpl implements DistributionService {

	@Autowired
	private OrderMapper orderMapper;

	@Autowired
	private UserAddressMapper userAddressMapper;
	
	@Autowired
	private StaffDistributionMapper distributionMapper;
	
	@Override
	public PageInfo<DistributionOrder> orderList(Integer pageNum, Integer pageSize, Integer shopId, String status) {
		
		
		//根据门店ID和状态
		PageHelper.startPage(pageNum, pageSize);
		OrderExample example = new OrderExample();
		if ("".equals(status) == false && status != null) {
			example.createCriteria().andStoreIdEqualTo(shopId).andStatusEqualTo(status);
		} else {
			example.createCriteria().andStoreIdEqualTo(shopId);
		}
		example.setOrderByClause("order_time asc");
		List<Order> list = orderMapper.selectByExample(example);
		
		List<DistributionOrder> distributionOrderList = new ArrayList<DistributionOrder>();
		
		for(Order order : list){
			
			//根据addressID查询地址信息
			UserAddressExample example1 = new UserAddressExample();
			
			example1.createCriteria().andIdEqualTo(Integer.parseInt(order.getAddressId()));
			List<UserAddress> userAddressList = userAddressMapper.selectByExample(example1);
			UserAddress userAddress = userAddressList.get(0);
			
			DistributionOrder distributionOrder = new DistributionOrder();
			
			distributionOrder.setOrderId(order.getId());
			distributionOrder.setOrderTime(order.getOrderTime());
			distributionOrder.setAddress(userAddress.getAddress());
			distributionOrder.setArea(userAddress.getArea());
			distributionOrder.setDimension(userAddress.getDimension());
			distributionOrder.setId(userAddress.getId());
			distributionOrder.setIsDef(userAddress.getIsDef());
			distributionOrder.setLongitude(userAddress.getLongitude());
			distributionOrder.setPhone(userAddress.getPhone());
			distributionOrder.setRealName(userAddress.getRealName());
			distributionOrder.setRemarks(userAddress.getRemarks());
			distributionOrder.setUserId(userAddress.getUserId());
			
			distributionOrderList.add(distributionOrder);
		}
		PageInfo<DistributionOrder> page = new PageInfo<DistributionOrder>(distributionOrderList);
		return page;
	}

	@Override
	public Integer addDistribution(StaffDistribution distribution) {
		
		return distributionMapper.insert(distribution);
	}

	@Override
	public Integer updateStatusByOrderId(Integer orderId, String status, Integer shopId) {
		return null;
	}

	

}
