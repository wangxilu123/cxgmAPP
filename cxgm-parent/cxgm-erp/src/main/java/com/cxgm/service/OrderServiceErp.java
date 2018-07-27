package com.cxgm.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cxgm.common.WeixinReturnMonery;
import com.cxgm.dao.OrderMapper;
import com.cxgm.dao.OrderProductMapper;
import com.cxgm.dao.ProductImageMapper;
import com.cxgm.dao.ShopCartMapper;
import com.cxgm.dao.ShopMapper;
import com.cxgm.dao.UserAddressMapper;
import com.cxgm.domain.Order;
import com.cxgm.domain.OrderExample;
import com.cxgm.domain.OrderProductTransfer;
import com.cxgm.domain.ProductImage;
import com.cxgm.domain.Shop;
import com.cxgm.domain.UserAddress;
import com.cxgm.domain.UserAddressExample;


@Service
public class OrderServiceErp {
	
	@Autowired
	private OrderMapper orderDao;
	
	@Autowired
	private ProductImageMapper productImageMapper;
	
	@Autowired
	private OrderProductMapper orderProductMapper;
	
	@Autowired
	private UserAddressMapper userAddressMapper;
	
	@Autowired
	private ShopMapper shopMapper;
	
	public List<Order> findAllOrders(Integer storeId){
		OrderExample example = new OrderExample();
		example.createCriteria().andStoreIdEqualTo(storeId);
		example.setOrderByClause("order_time desc");
		return orderDao.selectByExample(example);
	}
	
	public List<Order> selectParseStatus(Integer storeId){
		return orderDao.selectParseStatus(storeId);
	}
	
	public List<Order> findOrdersWithParam(Map<String,Object> map){
		return orderDao.findOrdersWithParam(map);
	}

	public Long countByExample(OrderExample example) {
		return orderDao.countByExample(example);
	}
	
	public Integer updateOrderStatus(Integer id, Integer status) {
		OrderExample orderExample = new OrderExample();
		orderExample.createCriteria().andIdEqualTo(id);
		List<Order> orders = orderDao.selectByExample(orderExample);
		if(orders.size()>0) {
			Order order = orders.get(0);
			order.setStatus(String.valueOf(status));
			
			Integer num = orderDao.updateByExample(order, orderExample);
			
			if(num==1){
				
				WeixinReturnMonery.wechatRefund(order);//退款
				
			}
			
			return num;
		}
		return null;
	}
	
	public Order orderDetail(Integer orderId){
		
		OrderExample orderExample = new OrderExample();
		orderExample.createCriteria().andIdEqualTo(orderId);
		List<Order> orders = orderDao.selectByExample(orderExample);
		
		// 根据orderId查询订单详情信息
				List<OrderProductTransfer> productList = orderProductMapper.selectOrderDetail(orderId);

				for (OrderProductTransfer orderDetail : productList) {

					BigDecimal price = orderDetail.getPrice();

					BigDecimal num = new BigDecimal(orderDetail.getProductNum());

					orderDetail.setAmount(price.multiply(num));
					
					if(orderDetail.getProductUrl()!=null&&!"".equals(orderDetail.getProductUrl())){
						
						String[] imageIds = orderDetail.getProductUrl().split(",");
						
						//根据图片ID查询图片url
						ProductImage image  =productImageMapper.findById(Long.parseLong(imageIds[0]));
						
						orderDetail.setProductUrl(image!=null?image.getUrl():"");
					}

				}
		        if(orders.size()!=0){
		        	orders.get(0).setProductDetails(productList);
		        	
		        	//根据用户ID查询用户收货地址信息
		        	
		        	UserAddressExample example1 = new UserAddressExample();
		        	
		        	example1.createCriteria().andUserIdEqualTo(orders.get(0).getUserId()).andIdEqualTo(Integer.parseInt(orders.get(0).getAddressId()));
		        	List<UserAddress> addressList = userAddressMapper.selectByExample(example1);
		        	
		        	orders.get(0).setAddress(addressList.size()!=0?addressList.get(0):null);
		        	
		        	//根据门店ID查询门店信息
		        	Shop  shop = shopMapper.selectByPrimaryKey(orders.get(0).getStoreId());
		        	
		        	orders.get(0).setShopName(shop!=null?shop.getShopName():"");
		        	orders.get(0).setShopAddress(shop!=null?shop.getShopAddress():"");
		        	
		        	return orders.get(0);
		        }else{
		        	return null;
		        }
		
	}
}
