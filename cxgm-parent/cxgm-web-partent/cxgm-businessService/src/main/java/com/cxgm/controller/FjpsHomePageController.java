package com.cxgm.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cxgm.common.ResultDto;
import com.cxgm.domain.DistributionOrder;
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
@Api(description = "分拣配送首页相关接口")
@RestController
@RequestMapping("/fjpsHomePage")
public class FjpsHomePageController {

	@Autowired
	private DistributionService distributionService;
	
	@Autowired
	private CheckToken checkToken;
	

	@ApiOperation(value = "分拣配送首页接口", nickname = "分拣配送首页接口")
	@ApiImplicitParams({
        @ApiImplicitParam(name = "adminId",required = false, paramType = "query", dataType = "int"),
        @ApiImplicitParam(name = "shopId", required = false, paramType = "query", dataType = "int")
    })
	@GetMapping("/fjpsHomePage")
	public ResultDto<PageInfo<DistributionOrder>> findDistribution(HttpServletRequest request,
			@RequestParam(value = "adminId", required = false) Integer adminId,
			@RequestParam(value = "shopId", required = false) Integer shopId){
		
		boolean result = checkToken.checkAdmin(request.getHeader("token"));
		return null;

	}
	
	
}