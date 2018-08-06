package com.cxgm.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cxgm.common.ResultDto;
import com.cxgm.domain.Order;
import com.cxgm.domain.StaffSorting;
import com.cxgm.service.SortingService;
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
@Api(description = "分拣 相关接口")
@RestController
@RequestMapping("/sorting")
public class SortingController {

	@Autowired
	private SortingService sortingService;
	
	@Autowired
	private CheckToken checkToken;
	

	@ApiOperation(value = "根据门店查询分拣列表", nickname = "根据门店查询分拣列表")
	@ApiImplicitParams({
        @ApiImplicitParam(name = "pageNum", value = "第几页，默认1", required = false, paramType = "query", dataType = "int"),
		@ApiImplicitParam(name = "pageSize", value = "每页多少条，默认10", required = false, paramType = "query", dataType = "int"),
        @ApiImplicitParam(name = "status", value = "待分拣1，分拣中2，已完成3，退单7", required = false, paramType = "query", dataType = "int"),
        @ApiImplicitParam(name = "shopId", required = false, paramType = "query", dataType = "int"),
        @ApiImplicitParam(name = "adminId", required = false, paramType = "query", dataType = "int")
    })
	@GetMapping("/findSorting")
	public ResultDto<PageInfo<Order>> findFirstCategory(HttpServletRequest request,
			@RequestParam(value = "pageNum", defaultValue = "1", required = false) Integer pageNum,
			@RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize,
			@RequestParam(value = "status", required = false) String status,
			@RequestParam(value = "shopId", required = false) Integer shopId,
			@RequestParam(value = "adminId", required = false) Integer adminId){
		
		boolean result = checkToken.checkAdmin(request.getHeader("token"));

		if (result == true) {

			PageInfo<Order> list = sortingService.orderList(pageNum, pageSize, shopId, status,adminId);

			return new ResultDto<>(200, "查询成功！", list);
		} else {
			return new ResultDto<>(403, "token失效请重新登录！");
		}
	}
	
	@ApiOperation(value = "根据订单查询分拣详情", nickname = "根据订单查询分拣详情")
	@ApiImplicitParams({
        @ApiImplicitParam(name = "orderId", required = false, paramType = "query", dataType = "int")
    })
	@GetMapping("/findSortingDetail")
	public ResultDto<Order> findFirstCategory(HttpServletRequest request,
			@RequestParam(value = "orderId", required = false) Integer orderId){
		
		boolean result = checkToken.checkAdmin(request.getHeader("token"));

		if (result == true) {

			Order order = sortingService.orderDetail(orderId);

			return new ResultDto<>(200, "查询成功！", order);
		} else {
			return new ResultDto<>(403, "token失效请重新登录！");
		}
	}
	
	@ApiOperation(value = "员工接受分拣单接口",nickname = "员工接分拣单接口")
    @ApiImplicitParam(name = "StaffSorting", value = "分拣单实体StaffSorting", required = true, dataType = "StaffSorting")
    @PostMapping("/addStaffSorting")
    public ResultDto<Integer> addStaffSorting(HttpServletRequest request, @RequestBody  StaffSorting staffSorting){
    	
		boolean result = checkToken.checkAdmin(request.getHeader("token"));
    	
    	if(result == true){
    	
    	Integer sortingId = sortingService.addSorting(staffSorting);
    	
        return new ResultDto<>(200,"接单成功！",sortingId);
    	}else{
    		return new ResultDto<>(403,"token失效请重新登录！");
    	}
    }
	
	@ApiOperation(value = "员工完成分拣单接口",nickname = "员工完成分拣单接口")
	@ApiImplicitParams({
        @ApiImplicitParam(name = "orderId", required = false, paramType = "query", dataType = "int"),
        @ApiImplicitParam(name = "shopId", required = false, paramType = "query", dataType = "int")
    })
    @PostMapping("/completeSorting")
    public ResultDto<Integer> completeSorting(HttpServletRequest request, 
    		@RequestParam(value = "orderId", required = false) Integer orderId,
    		@RequestParam(value = "shopId", required = false) Integer shopId){
    	
		boolean result = checkToken.checkAdmin(request.getHeader("token"));
    	
    	if(result == true){
    	
    	Integer sortingId = sortingService.updateStatusByOrderId(orderId, shopId);
    	
        return new ResultDto<>(200,"完成分拣！",sortingId);
    	}else{
    		return new ResultDto<>(403,"token失效请重新登录！");
    	}
    }
	
	@ApiOperation(value = "员工取消分拣单接口",nickname = "员工取消分拣单接口")
	@ApiImplicitParams({
        @ApiImplicitParam(name = "orderId", required = false, paramType = "query", dataType = "int"),
        @ApiImplicitParam(name = "cancelReason", required = false, paramType = "query", dataType = "string"),
    })
    @PostMapping("/cancelSorting")
    public ResultDto<Integer> cancelDistribution(HttpServletRequest request, 
    		@RequestParam(value = "orderId", required = false) Integer orderId,
    		@RequestParam(value = "cancelReason", required = false) String cancelReason){
    	
		boolean result = checkToken.checkAdmin(request.getHeader("token"));
    	
    	if(result == true){
    	
    	Integer distriId = sortingService.cancelOrder(orderId, cancelReason);
    	
        return new ResultDto<>(200,"取消配送成功！",distriId);
    	}else{
    		return new ResultDto<>(403,"token失效请重新登录！");
    	}
    }
}