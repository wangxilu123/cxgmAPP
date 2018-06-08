package com.cxgm.service.impl;

import java.math.BigDecimal;
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
	public PageInfo<Order> orderList(Integer pageNum, Integer pageSize, Integer shopId, String status) {

		PageHelper.startPage(pageNum, pageSize);

		OrderExample example = new OrderExample();
		if ("".equals(status) == false && status != null) {
			example.createCriteria().andStoreIdEqualTo(shopId).andStatusEqualTo(status);
		} else {
			example.createCriteria().andStoreIdEqualTo(shopId);
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

			order.setProductDetails(productList);

		}

		PageInfo<Order> page = new PageInfo<Order>(list);

		return page;
	}

	@Override
	public Integer addSorting(StaffSorting staffSorting) {

		// 根据订单ID查询分拣单
		StaffSortingExample example = new StaffSortingExample();

		example.createCriteria().andOrderIdEqualTo(staffSorting.getOrderId())
				.andShopIdEqualTo(staffSorting.getShopId());

		List<StaffSorting> list = staffSortingMapper.selectByExample(example);

		if (list.size() != 0) {
			return 0;
		} else {
			return staffSortingMapper.insert(staffSorting);
		}
	}

	@Override
	public Integer updateStatusByOrderId(Integer orderId, String status,Integer shopId) {

		// 根据订单ID查询分拣单
		StaffSortingExample example = new StaffSortingExample();

		example.createCriteria().andOrderIdEqualTo(orderId)
				.andShopIdEqualTo(shopId);

		List<StaffSorting> list = staffSortingMapper.selectByExample(example);
		
		StaffSorting staffSorting = list.get(0);
		
		
		staffSorting.setStatus(status);

		return staffSortingMapper.updateByExample(staffSorting, example);
	}

	@Override
	public Order orderDetail(Integer orderId) {
		// TODO Auto-generated method stub
		return null;
	}

}
