package com.cxgm.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.cxgm.dao.OrderMapper;
import com.cxgm.dao.OrderProductMapper;
import com.cxgm.dao.ProductImageMapper;
import com.cxgm.dao.StaffSortingMapper;
import com.cxgm.domain.Order;
import com.cxgm.domain.OrderExample;
import com.cxgm.domain.OrderProductTransfer;
import com.cxgm.domain.ProductImage;
import com.cxgm.domain.StaffSorting;
import com.cxgm.domain.StaffSortingExample;
import com.cxgm.service.SortingService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import lombok.Synchronized;

@Primary
@Service
public class SortingServiceImpl implements SortingService {

	@Autowired
	private OrderMapper orderMapper;

	@Autowired
	private OrderProductMapper orderProductMapper;

	@Autowired
	private ProductImageMapper productImageMapper;

	@Autowired
	private StaffSortingMapper staffSortingMapper;

	@Override
	public PageInfo<Order> orderList(Integer pageNum, Integer pageSize, Integer shopId, String status,Integer adminId) {
		
		

		OrderExample example = new OrderExample();
		if("1".equals(status)){
			PageHelper.startPage(pageNum, pageSize);
			example.createCriteria().andStoreIdEqualTo(shopId).andStatusEqualTo(status).andOrderResourceTo("APP");
		}else {
			PageHelper.startPage(pageNum, pageSize);
			//根据当前登录者查询订单
			StaffSortingExample example2 = new StaffSortingExample();
			example2.createCriteria().andAdminIdEqualTo(adminId).andStatusEqualTo(status);
			example2.setOrderByClause("create_time asc");
			List<StaffSorting> staffList = staffSortingMapper.selectByExample(example2);
			
			List<Integer> orderIds = new ArrayList<>();
			if(staffList.size()!=0){
				for(StaffSorting staffSorting : staffList){
					orderIds.add(staffSorting.getOrderId());
		        }
			}
			if(orderIds.size()!=0){
				example.createCriteria().andStoreIdEqualTo(shopId).andIdIn(orderIds).andOrderResourceTo("APP");
			}else{
				example.createCriteria().andStoreIdEqualTo(shopId).andStatusEqualTo("111").andOrderResourceTo("APP");
			}
			
		} 
		example.setOrderByClause("order_time asc");
		List<Order> list = orderMapper.selectByExample(example);

		for (Order order : list) {
			// 根据orderId查询订单详情信息

			List<OrderProductTransfer> productList = orderProductMapper.selectOrderDetail(order.getId());

			for (OrderProductTransfer orderDetail : productList) {

				BigDecimal price = orderDetail.getPrice();

				BigDecimal num = new BigDecimal(orderDetail.getProductNum());

				orderDetail.setAmount(price.multiply(num));

				if (orderDetail.getProductUrl() != null && !"".equals(orderDetail.getProductUrl())) {

					String[] imageIds = orderDetail.getProductUrl().split(",");

					// 根据图片ID查询图片url
					ProductImage image = productImageMapper.findById(Long.parseLong(imageIds[0]));

					orderDetail.setProductUrl(image != null ? image.getUrl() : "");
				}
			}
			order.setGoodNum(productList.size());
			order.setProductDetails(productList);

		}

		PageInfo<Order> page = new PageInfo<Order>(list);

		return page;
	}

	@Override
	@Synchronized
	public Integer addSorting(StaffSorting staffSorting) {

		// 根据订单ID查询分拣单
		StaffSortingExample example = new StaffSortingExample();

		example.createCriteria().andOrderIdEqualTo(staffSorting.getOrderId());
		List<StaffSorting> list = staffSortingMapper.selectByExample(example);

		if (list.size() == 0) {
            staffSorting.setStatus("2");
			
			Integer sortingId = staffSortingMapper.insert(staffSorting);
			
			//修改订单状态
			OrderExample example1 = new OrderExample();
			
			example1.createCriteria().andIdEqualTo(staffSorting.getOrderId());
			List<Order> orderList  = orderMapper.selectByExample(example1);
			if(orderList.size()!=0){
				
				Order order = orderList.get(0);
				order.setStatus("2");
				orderMapper.updateByExample(order, example1);
			}
			return sortingId;
			
		} else {
			return 0;
		}
	}

	@Override
	public Integer updateStatusByOrderId(Integer orderId,Integer shopId) {

		// 根据订单ID查询分拣单
		StaffSortingExample example = new StaffSortingExample();

		example.createCriteria().andOrderIdEqualTo(orderId);

		List<StaffSorting> list = staffSortingMapper.selectByExample(example);
		
		StaffSorting staffSorting = list.get(0);
		
		
		staffSorting.setStatus("3");
		
		Integer num = staffSortingMapper.updateByExample(staffSorting, example);
		
		//修改订单状态
		OrderExample example1 = new OrderExample();
		
		example1.createCriteria().andIdEqualTo(staffSorting.getOrderId());
		List<Order> orderList  = orderMapper.selectByExample(example1);
		if(orderList.size()!=0){
			
			Order order = orderList.get(0);
			order.setStatus("3");
			orderMapper.updateByExample(order, example1);
		}

		return num;
	}
	
	@Override
	public Integer cancelOrder(Integer orderId,String cancelReason) {
		// 根据订单ID查询配送单
		StaffSortingExample example = new StaffSortingExample();

		example.createCriteria().andOrderIdEqualTo(orderId);

		List<StaffSorting> list = staffSortingMapper.selectByExample(example);

		StaffSorting staffSorting = list.get(0);

		staffSorting.setStatus("6");

		Integer num = staffSortingMapper.updateByExample(staffSorting, example);

		// 修改订单状态
		OrderExample example1 = new OrderExample();

		example1.createCriteria().andIdEqualTo(staffSorting.getOrderId());
		List<Order> orderList = orderMapper.selectByExample(example1);
		if (orderList.size() != 0) {

			Order order = orderList.get(0);
			order.setStatus("6");
			orderMapper.updateByExample(order, example1);
		}

		return num;
	}

	@Override
	public Order orderDetail(Integer orderId) {
		
		OrderExample example = new OrderExample();
		
	    example.createCriteria().andIdEqualTo(orderId);
		List<Order> list = orderMapper.selectByExample(example);

		Order order = list.get(0);
		
			// 根据orderId查询订单详情信息

			List<OrderProductTransfer> productList = orderProductMapper.selectOrderDetail(order.getId());

			for (OrderProductTransfer orderDetail : productList) {

				BigDecimal price = orderDetail.getPrice();

				BigDecimal num = new BigDecimal(orderDetail.getProductNum());

				orderDetail.setAmount(price.multiply(num));

				if (orderDetail.getProductUrl() != null && !"".equals(orderDetail.getProductUrl())) {

					String[] imageIds = orderDetail.getProductUrl().split(",");

					// 根据图片ID查询图片url
					ProductImage image = productImageMapper.findById(Long.parseLong(imageIds[0]));

					orderDetail.setProductUrl(image != null ? image.getUrl() : "");
				}
			}
			order.setGoodNum(productList.size());
			order.setProductDetails(productList);

		return order;
	}

}
