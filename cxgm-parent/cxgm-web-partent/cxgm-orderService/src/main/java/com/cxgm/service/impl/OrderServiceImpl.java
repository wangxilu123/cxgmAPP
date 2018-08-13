package com.cxgm.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.cxgm.common.CodeUtil;
import com.cxgm.common.DateUtil;
import com.cxgm.dao.CouponCodeMapper;
import com.cxgm.dao.CouponMapper;
import com.cxgm.dao.OrderMapper;
import com.cxgm.dao.OrderProductMapper;
import com.cxgm.dao.ProductImageMapper;
import com.cxgm.dao.ProductMapper;
import com.cxgm.dao.ReceiptMapper;
import com.cxgm.dao.ShopCartMapper;
import com.cxgm.dao.ShopMapper;
import com.cxgm.dao.UserAddressMapper;
import com.cxgm.domain.CategoryAndAmount;
import com.cxgm.domain.CouponCode;
import com.cxgm.domain.CouponDetail;
import com.cxgm.domain.Order;
import com.cxgm.domain.OrderExample;
import com.cxgm.domain.OrderProduct;
import com.cxgm.domain.OrderProductTransfer;
import com.cxgm.domain.Product;
import com.cxgm.domain.ProductImage;
import com.cxgm.domain.ProductTransfer;
import com.cxgm.domain.Shop;
import com.cxgm.domain.ShopCartExample;
import com.cxgm.domain.UserAddress;
import com.cxgm.domain.UserAddressExample;
import com.cxgm.service.OrderService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Primary
@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderMapper orderMapper;

	@Autowired
	private OrderProductMapper orderProductMapper;

	@Autowired
	private ProductMapper productMapper;

	@Autowired
	private CouponMapper couponMapper;

	@Autowired
	private CouponCodeMapper couponCodeMapper;

	@Autowired
	private ReceiptMapper receiptMapper;

	@Autowired
	private ShopCartMapper shopCartMapper;
	
	@Autowired
	private UserAddressMapper userAddressMapper;
	
	@Autowired
	private ShopMapper shopMapper;
	
	@Autowired
	private ProductImageMapper productImageMapper;

	@Override
	public Integer addOrder(Order order) {
		//根据门店ID查询门店信息
		 Shop shop = shopMapper.selectByPrimaryKey(order.getStoreId());
		
		String orderNum = DateUtil.formatDateTime2() + CodeUtil.genCodes(6);
		order.setOrderTime(new Date());
		order.setStatus("0");
		order.setOrderNum(orderNum);
		order.setOrderResource("APP");
		order.setHaixinShopCode(shop.getHxShopId());
		orderMapper.insert(order);

		for (OrderProduct orderProduct : order.getProductList()) {

			orderProduct.setOrderId(order.getId());
			orderProduct.setCreateTime(new Date());
			orderProductMapper.insert(orderProduct);
			// 修改销量
			Product product = productMapper.findProductById(new Long(orderProduct.getProductId()));

			if (product != null) {
				product.setSales(product.getSales() != null ? product.getSales()
						: 0 + (orderProduct.getProductNum() != null ? orderProduct.getProductNum() : 0));
			}

			productMapper.update(product);

			// 从购物车里面移除商品
			ShopCartExample example = new ShopCartExample();

			example.createCriteria().andGoodCodeEqualTo(orderProduct.getGoodCode()).andShopIdEqualTo(order.getStoreId())
					.andUserIdEqualTo(order.getUserId());

			shopCartMapper.deleteByExample(example);

		}
		// 发票信息
		if (order.getReceipt() != null) {
			order.getReceipt().setCreateTime(new Date());
			receiptMapper.insert(order.getReceipt());
		}
		// 更改优惠券信息
		if (order.getCouponCodeId() != null) {
			CouponCode couponCode = couponCodeMapper.select((long) order.getCouponCodeId());

			if (couponCode != null) {
				couponCode.setStatus(1);

				couponCodeMapper.update(couponCode);
			}
		}
		return order.getId();
	}

	@Override
	public Integer updateOrder(Order order) {

		OrderExample example = new OrderExample();

		example.createCriteria().andUserIdEqualTo(order.getUserId()).andIdEqualTo(order.getId());

		return orderMapper.updateByExample(order, example);
	}

	@Override
	public Integer returnMoney(Integer orderId) {

		/*
		 * //根据
		 * 
		 * OrderExample example = new OrderExample();
		 * 
		 * example.createCriteria().andUserIdEqualTo(order.getUserId()).
		 * andIdEqualTo(order.getId());
		 */

		return null;
	}

	@Override
	public PageInfo<Order> orderList(Integer pageNum, Integer pageSize, Integer userId, String status) {

		PageHelper.startPage(pageNum, pageSize);

		OrderExample example = new OrderExample();
		if ("".equals(status) == false && status != null) {
			if(status.equals("1")){
				List<String> list= new ArrayList<>();
				list.add("1");
				list.add("2");
				list.add("3");
				example.createCriteria().andUserIdEqualTo(userId).andStatusIn(list);
			}
			else if(status.equals("7")){
				List<String> list1= new ArrayList<>();
				list1.add("6");
				list1.add("7");
				example.createCriteria().andUserIdEqualTo(userId).andStatusIn(list1);
			}else{
				example.createCriteria().andUserIdEqualTo(userId).andStatusEqualTo(status);
			}
		} else {
			example.createCriteria().andUserIdEqualTo(userId);
		}
		example.setOrderByClause("order_time desc");
		List<Order> list = orderMapper.selectByExample(example);

		for (Order order : list) {
			// 根据orderId查询订单详情信息

			List<OrderProductTransfer> productList = orderProductMapper.selectOrderDetail(order.getId());

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

			order.setProductDetails(productList);

		}

		PageInfo<Order> page = new PageInfo<Order>(list);

		return page;
	}

	@Override
	public Order findById(Integer orderId) {

		OrderExample example = new OrderExample();

		example.createCriteria().andIdEqualTo(orderId);
		List<Order> list = orderMapper.selectByExample(example);
		if (list.size() != 0) {
			return list.get(0);
		} else {
			return null;
		}
	}

	@Override
	public List<CouponDetail> checkCoupons(Integer userId, List<OrderProduct> productList,
			List<CategoryAndAmount> amountList, BigDecimal totalAmount) {
		// 根据商品ID查询商品分类
		List<CouponDetail> newList = new ArrayList<CouponDetail>();

		for (OrderProduct orderProduct : productList) {
			ProductTransfer productTransfer = productMapper.findById((long) orderProduct.getProductId());

			// 该商品的总价
			BigDecimal amount = new BigDecimal(0);
			if(productTransfer!=null){
				if (orderProduct.getProductNum() != null && productTransfer.getPrice() != null) {
					amount = productTransfer.getPrice().multiply(new BigDecimal(orderProduct.getProductNum()));
				}
				// 根据商品ID查询优惠券

				HashMap<String, Object> map = new HashMap<String, Object>();

				map.put("userId", userId);
				map.put("amount", amount);
				map.put("productId", orderProduct.getProductId());
				List<CouponDetail> list = couponMapper.findCouponsByProduct1(map);

				newList.addAll(list);
			}
			
		}

		// 根据商品二级分类及金额查询优惠券

		HashMap<String, Object> map1 = new HashMap<String, Object>();

		map1.put("userId", userId);
		map1.put("amountList", amountList);
		if (amountList != null && amountList.size() != 0) {
			List<CouponDetail> list1 = couponMapper.findCouponsByProduct(map1);

			newList.addAll(list1);
		}

		// 根据全品类查询优惠券

		HashMap<String, Object> map2 = new HashMap<String, Object>();

		map2.put("userId", userId);
		map2.put("totalAmount", totalAmount);
		List<CouponDetail> list2 = couponMapper.findCouponsByProduct2(map2);

		newList.addAll(list2);
		newList = new ArrayList<CouponDetail>(new LinkedHashSet<>(newList));

		return newList;
	}

	

	@Override
	public Order orderDetail(Integer userId, Integer orderId) {

		OrderExample example = new OrderExample();
		
		example.createCriteria().andUserIdEqualTo(userId).andIdEqualTo(orderId);
		
		List<Order> orders = orderMapper.selectByExample(example);

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
        	
        	example1.createCriteria().andUserIdEqualTo(userId).andIdEqualTo(Integer.parseInt(orders.get(0).getAddressId()));
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

	@Override
	public List<Order> findOrders() {
		
		OrderExample example = new OrderExample();
		
		example.createCriteria().andStatusEqualTo("0");
		
		example.setOrderByClause("order_time desc");
		
		List<Order> list = orderMapper.selectByExample(example);
		return list;
	}

}
