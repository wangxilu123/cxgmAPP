package com.cxgm.controller;


import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cxgm.common.ResultDto;
import com.cxgm.domain.AppUser;
import com.cxgm.domain.CouponDetail;
import com.cxgm.domain.Order;
import com.cxgm.domain.OrderProduct;
import com.cxgm.service.OrderService;
import com.cxgm.service.impl.CheckToken;
import com.github.pagehelper.PageInfo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
* @Description 类说明: 
*/
@Api(description = "订单相关接口")
@RestController
@RequestMapping("/order")
@Validated
public class OrderController {

    @Autowired
    private OrderService orderService;
    
    @Autowired
	private CheckToken checkToken;
    
    @ApiOperation(value = "用户下单接口",nickname = "用户下单接口")
    @ApiImplicitParam(name = "order", value = "用户实体order", required = true, dataType = "Order")
    @PostMapping("/addOrder")
    public ResultDto<Integer> addOrder(HttpServletRequest request, @RequestBody  Order order){
    	
        AppUser appUser = checkToken.check(request.getHeader("token"));
    	
    	if(appUser!=null){
    	
    	order.setUserId(appUser.getId());
    		
    	Integer orderId = orderService.addOrder(order);
    	
        return new ResultDto<>(200,"下单成功！",orderId);
    	}else{
    		return new ResultDto<>(403,"token失效请重新登录！");
    	}
    }
    
    @ApiOperation(value = "我的订单列表",nickname = "我的订单列表")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "pageNum", value = "第几页，默认1", required = false, paramType = "query", dataType = "int"),
        @ApiImplicitParam(name = "pageSize", value = "每页多少条，默认10", required = false, paramType = "query", dataType = "int"),
        @ApiImplicitParam(name = "status", value = "订单状态0待支付，1待配送（已支付），2配送中，3已完成，4退货", required = false, paramType = "query", dataType = "string"),
    })
    @GetMapping("/list")
    public ResultDto<PageInfo<Order>> orderList(HttpServletRequest request,
            @RequestParam(value = "pageNum", defaultValue = "1" , required = false) Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = "10" , required = false) Integer pageSize,
            @RequestParam(value = "status", required = false) String status){
    	
    	AppUser appUser = checkToken.check(request.getHeader("token"));
    	
    	if(appUser!=null){
    		
    		PageInfo<Order> result = orderService.orderList(pageNum , pageSize, appUser.getId(),status);
    		
    		return new ResultDto<>(200,"查询成功！",result);
    	}else{
    		return new ResultDto<>(403,"token失效请重新登录！");
    	}
    }
    
    @ApiOperation(value = "删除订单接口",nickname = "删除订单接口")
    @ApiImplicitParam(name = "orderId", value = "订单ID", required = true, dataType = "Integer")
    @PostMapping("/deleteOrder")
    public ResultDto<Integer> deleteOrder(HttpServletRequest request, 
    		@RequestParam(value = "orderId", required = false) Integer orderId){
    	
        AppUser appUser = new CheckToken().check(request.getHeader("token"));
    	
    	if(appUser!=null){
    		
    		Integer result = orderService.deleteOrder(orderId, appUser.getId());
    		
    		return new ResultDto<>(200,"删除成功！",result);
    	}else{
    		return new ResultDto<>(403,"token失效请重新登录！");
    	}
    }
    
    @ApiOperation(value = "根据用户ID和所选商品类别查询可用优惠券", nickname = "根据用户ID和所选商品类别查询可用优惠券")
	@ApiImplicitParams({
        @ApiImplicitParam(name = "productList", value = "订单商品列表", required = false, paramType = "query", dataType = "string"),
    })
	@PostMapping("/checkCoupon")
	public ResultDto<List<CouponDetail>> exchangeCoupons(HttpServletRequest request,@RequestBody Order  order){
		
		AppUser appUser = checkToken.check(request.getHeader("token"));

		if (appUser != null) {
  
			List<CouponDetail> list = orderService.checkCoupons(appUser.getId(), order.getProductList());

			return new ResultDto<>(200, "查询成功！",list);
		} else {
			return new ResultDto<>(403, "token失效请重新登录！");
		}
	}

}
