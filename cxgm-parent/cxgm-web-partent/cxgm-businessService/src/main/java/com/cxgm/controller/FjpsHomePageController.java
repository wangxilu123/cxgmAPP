package com.cxgm.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cxgm.common.ResultDto;
import com.cxgm.domain.FjpsHomePage;
import com.cxgm.service.FjpsHomePageService;
import com.cxgm.service.impl.CheckToken;

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
@RequestMapping("/homePage")
public class FjpsHomePageController {

	@Autowired
	private FjpsHomePageService fjpsHomePageService;
	
	@Autowired
	private CheckToken checkToken;
	

	@ApiOperation(value = "分拣配送首页接口", nickname = "分拣配送首页接口")
	@ApiImplicitParams({
        @ApiImplicitParam(name = "adminId",required = false, paramType = "query", dataType = "int"),
        @ApiImplicitParam(name = "shopId", required = false, paramType = "query", dataType = "int")
    })
	@GetMapping("/fjpsHomePage")
	public ResultDto<FjpsHomePage> findDistribution(HttpServletRequest request,
			@RequestParam(value = "adminId", required = false) Integer adminId,
			@RequestParam(value = "shopId", required = false) Integer shopId){
		
		boolean result = checkToken.checkAdmin(request.getHeader("token"));
		
		if (result == true) {

			FjpsHomePage fjpsHomePage = fjpsHomePageService.findHomePageNum(adminId,shopId);
			return new ResultDto<>(200, "查询成功！", fjpsHomePage);
		} else {
			return new ResultDto<>(403, "token失效请重新登录！");
		}
	}
	
	
}