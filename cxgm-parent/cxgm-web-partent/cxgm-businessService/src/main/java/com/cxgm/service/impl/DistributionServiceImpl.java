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
import com.cxgm.dao.StaffDistributionMapper;
import com.cxgm.dao.UserAddressMapper;
import com.cxgm.domain.DistributionOrder;
import com.cxgm.domain.Order;
import com.cxgm.domain.OrderExample;
import com.cxgm.domain.OrderProductTransfer;
import com.cxgm.domain.ProductImage;
import com.cxgm.domain.StaffDistribution;
import com.cxgm.domain.StaffDistributionExample;
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

	@Autowired
	private OrderProductMapper orderProductMapper;

	@Autowired
	private ProductImageMapper productImageMapper;

	@Override
	public PageInfo<DistributionOrder> orderList(Integer pageNum, Integer pageSize, Integer shopId, String status) {

		// 根据门店ID和状态
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

		for (Order order : list) {

			// 根据addressID查询地址信息
			UserAddressExample example1 = new UserAddressExample();

			example1.createCriteria().andIdEqualTo(Integer.parseInt(order.getAddressId()));
			List<UserAddress> userAddressList = userAddressMapper.selectByExample(example1);

			if (userAddressList.size() != 0) {
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
		}
		PageInfo<DistributionOrder> page = new PageInfo<DistributionOrder>(distributionOrderList);
		return page;
	}

	@Override
	public Integer addDistribution(StaffDistribution distribution) {

		// 根据订单ID查询配送单
		StaffDistributionExample example = new StaffDistributionExample();

		example.createCriteria().andOrderIdEqualTo(distribution.getOrderId());

		List<StaffDistribution> list = distributionMapper.selectByExample(example);

		if (list.size() != 0) {
			return 0;
		} else {

			distribution.setStatus("4");

			Integer distributionId = distributionMapper.insert(distribution);

			// 修改订单状态
			OrderExample example1 = new OrderExample();

			example1.createCriteria().andIdEqualTo(distribution.getOrderId());
			List<Order> orderList = orderMapper.selectByExample(example1);
			if (orderList.size() != 0) {

				Order order = orderList.get(0);
				order.setStatus("4");
				orderMapper.updateByExample(order, example1);
			}
			return distributionId;
		}
	}

	@Override
	public Integer updateStatusByOrderId(Integer orderId) {
		// 根据订单ID查询配送单
		StaffDistributionExample example = new StaffDistributionExample();

		example.createCriteria().andOrderIdEqualTo(orderId);

		List<StaffDistribution> list = distributionMapper.selectByExample(example);

		StaffDistribution staffDistribution = list.get(0);

		staffDistribution.setStatus("5");

		Integer num = distributionMapper.updateByExample(staffDistribution, example);

		// 修改订单状态
		OrderExample example1 = new OrderExample();

		example1.createCriteria().andIdEqualTo(staffDistribution.getOrderId());
		List<Order> orderList = orderMapper.selectByExample(example1);
		if (orderList.size() != 0) {

			Order order = orderList.get(0);
			order.setStatus("5");
			orderMapper.updateByExample(order, example1);
		}

		return num;
	}

	@Override
	public Integer cancelOrder(Integer orderId, String cancelReason) {
		// 根据订单ID查询配送单
		StaffDistributionExample example = new StaffDistributionExample();

		example.createCriteria().andOrderIdEqualTo(orderId);

		List<StaffDistribution> list = distributionMapper.selectByExample(example);

		StaffDistribution staffDistribution = list.get(0);

		staffDistribution.setStatus("6");

		Integer num = distributionMapper.updateByExample(staffDistribution, example);

		// 修改订单状态
		OrderExample example1 = new OrderExample();

		example1.createCriteria().andIdEqualTo(staffDistribution.getOrderId());
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

		// 根据addressID查询地址信息
		UserAddressExample example1 = new UserAddressExample();

		example1.createCriteria().andIdEqualTo(Integer.parseInt(order.getAddressId()));
		List<UserAddress> userAddressList = userAddressMapper.selectByExample(example1);

		order.setAddress(userAddressList.size() != 0 ? userAddressList.get(0) : null);

		return order;
	}
}
