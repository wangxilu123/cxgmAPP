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
import com.cxgm.domain.DistributionOrder;
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
        @ApiImplicitParam(name = "status", value = "", required = false, paramType = "query", dataType = "int"),
        @ApiImplicitParam(name = "shopId", required = false, paramType = "query", dataType = "int")
    })
	@GetMapping("/findDistribution")
	public ResultDto<PageInfo<DistributionOrder>> findDistribution(HttpServletRequest request,
			@RequestParam(value = "pageNum", defaultValue = "1", required = false) Integer pageNum,
			@RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize,
			@RequestParam(value = "status", required = false) String status,
			@RequestParam(value = "shopId", required = false) Integer shopId){
		
		boolean result = checkToken.checkAdmin(request.getHeader("token"));

		if (result == true) {

			PageInfo<DistributionOrder> list = distributionService.orderList(pageNum, pageSize, shopId, status);

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
	
}