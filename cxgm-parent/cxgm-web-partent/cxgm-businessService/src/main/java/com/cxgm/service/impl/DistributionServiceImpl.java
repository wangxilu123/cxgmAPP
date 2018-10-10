package com.cxgm.service.impl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.xml.rpc.ServiceException;
import javax.xml.soap.SOAPException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cxgm.dao.OrderMapper;
import com.cxgm.dao.OrderProductMapper;
import com.cxgm.dao.ProductImageMapper;
import com.cxgm.dao.ProductMapper;
import com.cxgm.dao.StaffDistributionMapper;
import com.cxgm.dao.UserAddressMapper;
import com.cxgm.domain.Order;
import com.cxgm.domain.OrderExample;
import com.cxgm.domain.OrderProductTransfer;
import com.cxgm.domain.Product;
import com.cxgm.domain.ProductImage;
import com.cxgm.domain.StaffDistribution;
import com.cxgm.domain.StaffDistributionExample;
import com.cxgm.domain.UserAddress;
import com.cxgm.domain.UserAddressExample;
import com.cxgm.service.DistributionService;
import com.cxgm.service.ThirdPartyHaixinUplodGoodsService;
import com.cxgm.service.ThirdPartyHaixinUplodOrderService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import lombok.Synchronized;

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
	
	@Autowired
	private ProductMapper productMapper;
	
	@Autowired
	private ThirdPartyHaixinUplodGoodsService thirdPartyHaixinUplodGoodsService;
	
	@Autowired
	private ThirdPartyHaixinUplodOrderService thirdPartyHaixinUplodOrderService;

	@Override
	public PageInfo<Order> orderList(Integer pageNum, Integer pageSize, Integer shopId, String status ,Integer adminId) {
        
		// 根据门店ID和状态
		
		OrderExample example = new OrderExample();
		if ("3".equals(status)) {
			PageHelper.startPage(pageNum, pageSize);
			
			OrderExample.Criteria criteria =example.createCriteria();
			
			criteria.andStoreIdEqualTo(shopId).andStatusEqualTo(status).andOrderResourceTo("APP").andExtractionTypeEqualTo("配送");
			
			OrderExample.Criteria criteria2 =example.createCriteria();
			
			criteria2.andStoreIdEqualTo(shopId).andStatusEqualTo(status).andOrderResourceTo("APP").andExtractionTypeIsNull();

            example.or(criteria2);
		} else {
			PageHelper.startPage(pageNum, pageSize);
			//根据当前登录者查询订单
			StaffDistributionExample example2 = new StaffDistributionExample();
			example2.createCriteria().andAdminIdEqualTo(adminId).andStatusEqualTo(status);
			example2.setOrderByClause("create_time asc");
			List<StaffDistribution> staffList = distributionMapper.selectByExample(example2);
			
			List<Integer> orderIds = new ArrayList<>();
			if(staffList.size()!=0){
				for(StaffDistribution staffDistribution : staffList){
					orderIds.add(staffDistribution.getOrderId());
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

			// 根据addressID查询地址信息
			UserAddressExample example1 = new UserAddressExample();

			example1.createCriteria().andIdEqualTo(Integer.parseInt(order.getAddressId()));
			List<UserAddress> userAddressList = userAddressMapper.selectByExample(example1);

			if (userAddressList.size() != 0) {
				UserAddress userAddress = userAddressList.get(0);

				order.setAddress(userAddress);
			}
		}
		PageInfo<Order> page = new PageInfo<Order>(list);
		return page;
	}

	@Override
	@Synchronized
	@Transactional
	public Integer addDistribution(StaffDistribution distribution) {

		// 根据订单ID查询配送单
		StaffDistributionExample example = new StaffDistributionExample();

		example.createCriteria().andOrderIdEqualTo(distribution.getOrderId());

		List<StaffDistribution> list = distributionMapper.selectByExample(example);

		if (list.size() == 0) {

			Integer distributionId =0;

			// 修改订单状态
			OrderExample example1 = new OrderExample();

			example1.createCriteria().andIdEqualTo(distribution.getOrderId());
			List<Order> orderList = orderMapper.selectByExample(example1);
			if (orderList.size() != 0) {

				Order order = orderList.get(0);
				order.setStatus("4");
				Integer upId=orderMapper.updateByExample(order, example1);
				if(upId>0){
					distribution.setStatus("4");
					distributionId = distributionMapper.insert(distribution);
				}
			}
			return distributionId;
			
		} else {
			return 0;
		}
	}

	@Override
	@Synchronized
	@Transactional
	public Integer updateStatusByOrderId(Integer orderId) throws UnsupportedEncodingException, SOAPException, ServiceException, IOException {
		// 根据订单ID查询配送单
		StaffDistributionExample example = new StaffDistributionExample();

		example.createCriteria().andOrderIdEqualTo(orderId);

		List<StaffDistribution> list = distributionMapper.selectByExample(example);

		StaffDistribution staffDistribution = list.get(0);

		Integer num = 0;

		// 修改订单状态
		OrderExample example1 = new OrderExample();

		example1.createCriteria().andIdEqualTo(staffDistribution.getOrderId());
		List<Order> orderList = orderMapper.selectByExample(example1);
		if (orderList.size() != 0) {

			Order order = orderList.get(0);
			order.setStatus("5");
			Integer upId = orderMapper.updateByExample(order, example1);
			
			if(upId>0){
				staffDistribution.setStatus("5");
				
				num = distributionMapper.updateByExample(staffDistribution, example);
			}
			
			
			//修改海信库存
			List<OrderProductTransfer> productList = orderProductMapper.selectOrderDetail(order.getId());

			for (OrderProductTransfer orderDetail : productList) {

				BigDecimal price = orderDetail.getPrice();

				BigDecimal productNum = new BigDecimal(orderDetail.getProductNum());

				orderDetail.setAmount(price.multiply(productNum));
				
				if(orderDetail.getProductUrl()!=null&&!"".equals(orderDetail.getProductUrl())){
					
					String[] imageIds = orderDetail.getProductUrl().split(",");
					
					//根据图片ID查询图片url
					ProductImage image  =productImageMapper.findById(Long.parseLong(imageIds[0]));
					
					orderDetail.setProductUrl(image!=null?image.getUrl():"");
				}
				orderDetail.setHaixinNum(String.valueOf(orderDetail.getProductNum()));
				//根据商品ID查询商品详情
				
				Product product = productMapper.findProductById(Long.valueOf(orderDetail.getProductId()));
				
				if("".equals(product.getUnit())||product.getUnit()==null||product.getUnit().indexOf("g")!=-1){
					if(!"".equals(product.getWeight()) && product.getWeight()!=null){
						if(product.getWeight().indexOf("Kg")!=-1){
							Double weight = Double.parseDouble(product.getWeight().replace("Kg",""));
							
							orderDetail.setHaixinNum(String.valueOf(orderDetail.getProductNum()*weight));
							
							Double newprice = orderDetail.getPrice().doubleValue();
							orderDetail.setPrice(new BigDecimal(newprice/weight).setScale(2,BigDecimal.ROUND_HALF_DOWN));
						}else{
							if(product.getWeight().indexOf("g")==-1){
								orderDetail.setHaixinNum(String.valueOf(orderDetail.getProductNum()));
							}else{
								Double weight = Double.parseDouble(product.getWeight().replace("g",""));
								
								orderDetail.setHaixinNum(String.valueOf(orderDetail.getProductNum()*weight/1000));
								
								Double newprice = orderDetail.getPrice().doubleValue();
								orderDetail.setPrice(new BigDecimal(newprice/weight/1000).setScale(2,BigDecimal.ROUND_HALF_DOWN));
							}
						}
					}
				}
			}

			order.setProductDetails(productList);
			
			String code = thirdPartyHaixinUplodOrderService.checkOrder(order.getOrderNum());
			if("1".equals(code)){
				thirdPartyHaixinUplodGoodsService.upload(order);
			}
			
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
