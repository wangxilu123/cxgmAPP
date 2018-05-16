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

import com.cxgm.dao.CouponMapper;
import com.cxgm.dao.OrderMapper;
import com.cxgm.dao.OrderProductMapper;
import com.cxgm.dao.ProductMapper;
import com.cxgm.domain.CouponDetail;
import com.cxgm.domain.Order;
import com.cxgm.domain.OrderExample;
import com.cxgm.domain.OrderProduct;
import com.cxgm.domain.OrderProductTransfer;
import com.cxgm.domain.Product;
import com.cxgm.domain.ProductTransfer;
import com.cxgm.service.OrderService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Primary
@Service
public class OrderServiceImpl implements OrderService{

    @Autowired
    private OrderMapper orderMapper;
    
    @Autowired
    private OrderProductMapper orderProductMapper;
    
    @Autowired
    private ProductMapper productMapper;
    
    @Autowired
    private CouponMapper couponMapper;

	@Override
	public Integer addOrder(Order order) {
		
		order.setOrderTime(new Date());
		order.setStatus("0");
		orderMapper.insert(order);
		
		for(OrderProduct orderProduct : order.getProductList()){
			
			orderProduct.setOrderId(order.getId());
			orderProduct.setCreateTime(new Date());
			orderProductMapper.insert(orderProduct);
			//修改销量
			Product product = productMapper.findProductById((long)orderProduct.getProductId());
			
			product.setSales(product.getSales()+orderProduct.getProductNum());
			
			productMapper.update(product);
			
		}
		
		return order.getId();
	}

	@Override
	public Integer updateOrder(Order order) {
		
		return null;
	}

	@Override
	public Integer deleteOrder(Integer orderId, Integer userId) {
		
        OrderExample example = new OrderExample();
		
		example.createCriteria().andUserIdEqualTo(userId).andIdEqualTo(orderId);
		
		return orderMapper.deleteByExample(example);
	}

	@Override
	public PageInfo<Order> orderList(Integer pageNum, Integer pageSize,Integer userId,String status) {
		
		PageHelper.startPage(pageNum, pageSize);
		
		OrderExample example = new OrderExample();
		if(status.equals("")==false&&status!=null){
			example.createCriteria().andUserIdEqualTo(userId).andStatusEqualTo(status);
		}else{
			example.createCriteria().andUserIdEqualTo(userId);
		}
		List<Order> list = orderMapper.selectByExample(example);
		
		for(Order order : list){
			//根据orderId查询订单详情信息
			
			List<OrderProductTransfer> productList=orderProductMapper.selectOrderDetail(order.getId());
			
			for(OrderProductTransfer orderDetail :productList){
				
				BigDecimal price = orderDetail.getPrice();
				
				BigDecimal num = new BigDecimal(orderDetail.getProductNum());
				
				orderDetail.setAmount(price.multiply(num));
				
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
		if(list.size()!=0){
			return list.get(0);
		}else{
			return null;
		}
	}

	@Override
	public List<CouponDetail> checkCoupons(Integer userId, List<OrderProduct> productList) {
		//根据商品ID查询商品分类
		List<CouponDetail> newList= new ArrayList<CouponDetail>();
		
		for(OrderProduct orderProduct : productList){
			ProductTransfer productTransfer = productMapper.findById((long)orderProduct.getProductId());
			
			List<Integer>  ids = new ArrayList<>();
	        
	        if(productTransfer.getProductCategoryId()!=null){
	        	ids.add(productTransfer.getProductCategoryId().intValue());
	        }
	        
	        if(productTransfer.getProductCategoryTwoId()!=null){
	        	ids.add(productTransfer.getProductCategoryTwoId().intValue());
	        }
	        
	        if(productTransfer.getProductCategoryThirdId()!=null){
	        	ids.add(productTransfer.getProductCategoryThirdId().intValue());
	        }
	        
	        //根据商品类型ID或商品ID查询优惠券
	        
	        HashMap<String,Object> map = new HashMap<String,Object>();
	        
	        map.put("userId", userId);
	        map.put("categoryIds", ids);
	        map.put("productId", orderProduct.getProductId());
	        
	        List<CouponDetail> list = couponMapper.findCouponsByProduct(map);
	        
	        newList.addAll(list);
		}
		
		newList = new ArrayList<CouponDetail>(new LinkedHashSet<>(newList));
        
		return newList;
	}

}
