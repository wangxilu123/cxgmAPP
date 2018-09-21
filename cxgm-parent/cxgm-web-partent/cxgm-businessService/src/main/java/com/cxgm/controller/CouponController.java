package com.cxgm.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cxgm.common.ResultDto;
import com.cxgm.domain.AppUser;
import com.cxgm.domain.CouponDetail;
import com.cxgm.service.CouponService;
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
@Api(description = "优惠券相关接口")
@RestController
@RequestMapping("/coupon")
public class CouponController {

	@Autowired
	private CouponService couponService;
	
	@Autowired
	private CheckToken checkToken;
	

	@ApiOperation(value = "根据用户查询优惠券", nickname = "根据用户查询优惠券")
	@ApiImplicitParams({
        @ApiImplicitParam(name = "pageNum", value = "第几页，默认1", required = false, paramType = "query", dataType = "int"),
		@ApiImplicitParam(name = "pageSize", value = "每页多少条，默认10", required = false, paramType = "query", dataType = "int"),
        @ApiImplicitParam(name = "status", value = "0可用，1不可用", required = false, paramType = "query", dataType = "int")
    })
	@GetMapping("/findCoupons")
	public ResultDto<PageInfo<CouponDetail>> findFirstCategory(HttpServletRequest request,
			@RequestParam(value = "pageNum", defaultValue = "1", required = false) Integer pageNum,
			@RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize,
			@RequestParam(value = "status", required = false) Integer status){
		
		AppUser appUser = checkToken.check(request.getHeader("token"));

		if (appUser != null) {

			PageInfo<CouponDetail> result = couponService.findCouponByUserId(appUser.getId(), pageNum, pageSize,status);

			return new ResultDto<>(200, "查询成功！", result);
		} else {
			return new ResultDto<>(403, "token失效请重新登录！");
		}
	}
	
	@ApiOperation(value = "优惠券兑换", nickname = "优惠券兑换")
	@ApiImplicitParams({
        @ApiImplicitParam(name = "couponCode", value = "兑换码", required = false, paramType = "query", dataType = "string"),
    })
	@GetMapping("/exchangeCoupons")
	public ResultDto<CouponDetail> exchangeCoupons(HttpServletRequest request,
			@RequestParam(value = "couponCode", required = false) String couponCode){
		
		AppUser appUser = checkToken.check(request.getHeader("token"));

		if (appUser != null) {

			CouponDetail result = couponService.exchangeCoupons(appUser.getId(), couponCode);

			return new ResultDto<>(200, "查询成功！", result);
		} else {
			return new ResultDto<>(403, "token失效请重新登录！");
		}
	}
	
	@ApiOperation(value = "优惠券领取", nickname = "优惠券领取")
	@ApiImplicitParams({
        @ApiImplicitParam(name = "couponIds", value = "优惠券ID", required = false, paramType = "query", dataType = "string"),
    })
	@GetMapping("/getCoupons")
	public ResultDto getCoupons(HttpServletRequest request,
			@RequestParam(value = "couponIds", required = false) String couponIds){
		
		AppUser appUser = checkToken.check(request.getHeader("token"));

		if (appUser != null) {

			couponService.getCoupons(appUser.getId(), couponIds);

			return new ResultDto<>(200, "领取成功！");
		} else {
			return new ResultDto<>(403, "token失效请重新登录！");
		}
	}
	
	@ApiOperation(value = "验证是否领取", nickname = "验证是否领取")
	@GetMapping("/checkGet")
	public ResultDto checkGet(HttpServletRequest request){
		
		AppUser appUser = checkToken.check(request.getHeader("token"));

		if (appUser != null) {

			AppUser user = couponService.getAppUser(appUser.getId());

			return new ResultDto<>(200, "验证成功！",user);
		} else {
			return new ResultDto<>(403, "token失效请重新登录！");
		}
	}
}