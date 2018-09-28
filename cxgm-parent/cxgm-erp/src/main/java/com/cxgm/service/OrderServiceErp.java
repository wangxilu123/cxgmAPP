package com.cxgm.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cxgm.common.WeixinReturnMonery;
import com.cxgm.dao.AdminMapper;
import com.cxgm.dao.OrderMapper;
import com.cxgm.dao.OrderProductMapper;
import com.cxgm.dao.ProductImageMapper;
import com.cxgm.dao.ShopMapper;
import com.cxgm.dao.StaffDistributionMapper;
import com.cxgm.dao.StaffSortingMapper;
import com.cxgm.dao.UserAddressMapper;
import com.cxgm.domain.Admin;
import com.cxgm.domain.Order;
import com.cxgm.domain.OrderAdmin;
import com.cxgm.domain.OrderExample;
import com.cxgm.domain.OrderProduct;
import com.cxgm.domain.OrderProductExample;
import com.cxgm.domain.OrderProductTransfer;
import com.cxgm.domain.ProductImage;
import com.cxgm.domain.Shop;
import com.cxgm.domain.StaffDistribution;
import com.cxgm.domain.StaffDistributionExample;
import com.cxgm.domain.StaffSorting;
import com.cxgm.domain.StaffSortingExample;
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
	AdminMapper adminDao;
	
	@Autowired
	private UserAddressMapper userAddressMapper;
	
	@Autowired
	private ShopMapper shopMapper;
	
	@Autowired
	private StaffSortingMapper staffSortingMapper;
	
	@Autowired
	private StaffDistributionMapper distributionMapper;
	
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
	
	public List<Order> findOrders(Map<String,Object> map){
		
		List<Order> orders = orderDao.findOrdersWithParam(map);

        for(Order order:orders){
        	// 根据orderId查询订单详情信息
        	OrderProductExample example = new OrderProductExample();
        	example.createCriteria().andOrderIdEqualTo(order.getId());
        	List<OrderProduct> productList = orderProductMapper.selectByExample(example);

        	StringBuilder sb = new StringBuilder();  
        	for (OrderProduct orderDetail : productList) {
        		if(orderDetail.getProductName()!=null){
        			sb.append(orderDetail.getProductName()+"×"+orderDetail.getProductNum()+",");
        		}
        		
        	}
        	
        	order.setOrderProducts(sb.toString());
        }		
		return orders;
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
	
	
	public Integer updateOrderPick(Integer id, Integer status) {
		OrderExample orderExample = new OrderExample();
		orderExample.createCriteria().andIdEqualTo(id);
		List<Order> orders = orderDao.selectByExample(orderExample);
		if(orders.size()>0) {
			Order order = orders.get(0);
			order.setStatus(String.valueOf(status));
			
			Integer num = orderDao.updateByExample(order, orderExample);
			
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
                    if(price!=null){
                    	orderDetail.setAmount(price.multiply(num));
                    }
					
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
		        	if(orders.get(0).getUserId()!=null){
		        		example1.createCriteria().andUserIdEqualTo(orders.get(0).getUserId()).andIdEqualTo(Integer.parseInt(orders.get(0).getAddressId()));
		        	}
		        	
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
	
	public OrderAdmin getAdminName(String type,int orderId) {
		if(type.equals("sorting")) {
			StaffSortingExample example = new StaffSortingExample();
			example.createCriteria().andOrderIdEqualTo(orderId);
			List<StaffSorting> list = staffSortingMapper.selectByExample(example);
			if(list.size()>0) {
				Admin admin = adminDao.findById(Long.valueOf(list.get(0).getAdminId()));
				
				OrderAdmin orderAdmin = new OrderAdmin();
				orderAdmin.setSortingName(admin.getName());
				orderAdmin.setSortingPhone(admin.getPhone());
				return orderAdmin;
			}
			
		}else if(type.equals("distribution")) {
			
			StaffDistributionExample example = new StaffDistributionExample();

			example.createCriteria().andOrderIdEqualTo(orderId);

			List<StaffDistribution> list = distributionMapper.selectByExample(example);
			if(list.size()>0) {
				Admin admin = adminDao.findById(Long.valueOf(list.get(0).getAdminId()));
				OrderAdmin orderAdmin = new OrderAdmin();
				orderAdmin.setDistributionName(admin.getName());
				orderAdmin.setDistributionPhone(admin.getPhone());
				return orderAdmin;
			}
		}
		return null;
	}
}
