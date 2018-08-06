package com.cxgm.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.xml.rpc.ServiceException;
import javax.xml.soap.SOAPException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cxgm.common.ResultDto;
import com.cxgm.domain.DistributionOrder;
import com.cxgm.domain.Order;
import com.cxgm.domain.StaffDistribution;
import com.cxgm.service.DistributionService;
import com.cxgm.service.impl.CheckToken;
import com.github.pagehelper.PageInfo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * @Description 类说明:
 * @author 作者 E-mail: wangxilu
 */
@Api(description = "配送相关接口")
@RestController
@RequestMapping("/distribution")
public class DistributionController {

	@Autowired
	private DistributionService distributionService;
	
	@Autowired
	private CheckToken checkToken;
	

	@ApiOperation(value = "根据门店查询配送列表", nickname = "根据门店查询配送列表")
	@ApiImplicitParams({
        @ApiImplicitParam(name = "pageNum", value = "第几页，默认1", required = false, paramType = "query", dataType = "int"),
		@ApiImplicitParam(name = "pageSize", value = "每页多少条，默认10", required = false, paramType = "query", dataType = "int"),
        @ApiImplicitParam(name = "status", value = "待配送3，配送中3，配送完成5，退单7", required = false, paramType = "query", dataType = "int"),
        @ApiImplicitParam(name = "shopId", required = false, paramType = "query", dataType = "int"),
        @ApiImplicitParam(name = "adminId", required = false, paramType = "query", dataType = "int")
    })
	@GetMapping("/findDistribution")
	public ResultDto<PageInfo<DistributionOrder>> findDistribution(HttpServletRequest request,
			@RequestParam(value = "pageNum", defaultValue = "1", required = false) Integer pageNum,
			@RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize,
			@RequestParam(value = "status", required = false) String status,
			@RequestParam(value = "shopId", required = false) Integer shopId,
			@RequestParam(value = "adminId", required = false) Integer adminId){
		
		boolean result = checkToken.checkAdmin(request.getHeader("token"));

		if (result == true) {

			PageInfo<DistributionOrder> list = distributionService.orderList(pageNum, pageSize, shopId, status,adminId);

			return new ResultDto<>(200, "查询成功！", list);
		} else {
			return new ResultDto<>(403, "token失效请重新登录！");
		}
	}
	
	@ApiOperation(value = "员工接受配送单接口",nickname = "员工接受配送单接口")
    @ApiImplicitParam(name = "StaffDistribution", value = "配送单实体StaffDistribution", required = true, dataType = "StaffDistribution")
    @PostMapping("/addStaffDistribution")
    public ResultDto<Integer> addStaffDistribution(HttpServletRequest request, @RequestBody  StaffDistribution distribution){
    	
		boolean result = checkToken.checkAdmin(request.getHeader("token"));
    	
    	if(result == true){
    	
    	Integer id = distributionService.addDistribution(distribution);
    	
        return new ResultDto<>(200,"接单成功！",id);
    	}else{
    		return new ResultDto<>(403,"token失效请重新登录！");
    	}
    }
	
	@ApiOperation(value = "员工完成配送单接口",nickname = "员工完成配送单接口")
	@ApiImplicitParams({
        @ApiImplicitParam(name = "orderId", required = false, paramType = "query", dataType = "int"),
    })
    @PostMapping("/completeDistribution")
    public ResultDto<Integer> completeDistribution(HttpServletRequest request, 
    		@RequestParam(value = "orderId", required = false) Integer orderId) throws UnsupportedEncodingException, SOAPException, ServiceException, IOException{
    	
		boolean result = checkToken.checkAdmin(request.getHeader("token"));
    	
    	if(result == true){
    	
    	Integer distriId = distributionService.updateStatusByOrderId(orderId);
    	
        return new ResultDto<>(200,"完成配送！",distriId);
    	}else{
    		return new ResultDto<>(403,"token失效请重新登录！");
    	}
    }
	
	@ApiOperation(value = "员工取消配送单接口",nickname = "员工取消配送单接口")
	@ApiImplicitParams({
        @ApiImplicitParam(name = "orderId", required = false, paramType = "query", dataType = "int"),
        @ApiImplicitParam(name = "cancelReason", required = false, paramType = "query", dataType = "string"),
    })
    @PostMapping("/cancelDistribution")
    public ResultDto<Integer> cancelDistribution(HttpServletRequest request, 
    		@RequestParam(value = "orderId", required = false) Integer orderId,
    		@RequestParam(value = "cancelReason", required = false) String cancelReason){
    	
		boolean result = checkToken.checkAdmin(request.getHeader("token"));
    	
    	if(result == true){
    	
    	Integer distriId = distributionService.cancelOrder(orderId, cancelReason);
    	
        return new ResultDto<>(200,"取消配送成功！",distriId);
    	}else{
    		return new ResultDto<>(403,"token失效请重新登录！");
    	}
    }
	
	@ApiOperation(value = "根据订单查询配送详情", nickname = "根据订单查询配送详情")
	@ApiImplicitParams({
        @ApiImplicitParam(name = "orderId", required = false, paramType = "query", dataType = "int")
    })
	@GetMapping("/findDistributionDetail")
	public ResultDto<Order> findDistributionDetail(HttpServletRequest request,
			@RequestParam(value = "orderId", required = false) Integer orderId){
		
		boolean result = checkToken.checkAdmin(request.getHeader("token"));

		if (result == true) {

			Order order = distributionService.orderDetail(orderId);

			return new ResultDto<>(200, "查询成功！", order);
		} else {
			return new ResultDto<>(403, "token失效请重新登录！");
		}
	}
	
}