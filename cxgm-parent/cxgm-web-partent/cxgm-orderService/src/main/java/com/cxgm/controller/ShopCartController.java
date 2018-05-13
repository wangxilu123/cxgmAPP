package com.cxgm.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

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
import com.cxgm.domain.ShopCart;
import com.cxgm.service.ShopCartService;
import com.cxgm.service.impl.CheckToken;
import com.github.pagehelper.PageInfo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * @Description 类说明:
 */
@Api(description = "购物车相关接口")
@RestController
@RequestMapping("/shopCart")
@Validated
public class ShopCartController {

	@Autowired
	private ShopCartService shopCartService;
	
	@Autowired
	private CheckToken checkToken;

	@ApiOperation(value = "商品添加到购物车接口", nickname = "商品添加到购物车接口")
	@ApiImplicitParam(name = "shopCart", value = "购物车实体shopCart", required = true, dataType = "ShopCart")
	@PostMapping("/addCart")
	public ResultDto<Integer> addOrder(HttpServletRequest request, @Valid @RequestBody ShopCart shopCart) {

		AppUser appUser = checkToken.check(request.getHeader("token"));

		if (appUser != null) {
			
			shopCart.setUserId(appUser.getId());
			Integer result = shopCartService.addShopCart(shopCart);
			
			return new ResultDto<>(200, "添加成功！", result);
		} else {
			return new ResultDto<>(403, "token失效请重新登录！");
		}
	}
	
	@ApiOperation(value = "修改购物车接口", nickname = "修改购物车接口")
	@ApiImplicitParam(name = "shopCart", value = "购物车实体shopCart", required = true, dataType = "ShopCart")
	@PostMapping("/updateCart")
	public ResultDto<Integer> updateCart(HttpServletRequest request, @Valid @RequestBody ShopCart shopCart) {

		AppUser appUser = checkToken.check(request.getHeader("token"));

		if (appUser != null) {
			
			shopCart.setUserId(appUser.getId());
			Integer result = shopCartService.updateShopCart(shopCart);
			
			return new ResultDto<>(200, "添加成功！", result);
		} else {
			return new ResultDto<>(403, "token失效请重新登录！");
		}
	}

	@ApiOperation(value = "我的购物车列表", nickname = "我的购物车列表")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "pageNum", value = "第几页，默认1", required = false, paramType = "query", dataType = "int"),
			@ApiImplicitParam(name = "pageSize", value = "每页多少条，默认10", required = false, paramType = "query", dataType = "int"), })
	@GetMapping("/list")
	public ResultDto<PageInfo<ShopCart>> homeList(HttpServletRequest request,
			@RequestParam(value = "pageNum", defaultValue = "1", required = false) Integer pageNum,
			@RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize) {

		AppUser appUser = checkToken.check(request.getHeader("token"));

		if (appUser != null) {

			PageInfo<ShopCart> result = shopCartService.shopCartList(pageNum, pageSize, appUser.getId());

			return new ResultDto<>(200, "查询成功！", result);
		} else {
			return new ResultDto<>(403, "token失效请重新登录！");
		}
	}

	@ApiOperation(value = "购物车移除商品接口", nickname = "购物车移除商品接口")
	@ApiImplicitParam(name = "shopCartIds", value = "购物车商品ID", required = true, dataType = "Integer")
	@PostMapping("/deleteShopCart")
	public ResultDto<Integer> deleteOrder(HttpServletRequest request,
			@RequestParam(value = "shopCartIds", required = false) String shopCartIds) {

		AppUser appUser = checkToken.check(request.getHeader("token"));

		if (appUser != null) {

			Integer result = shopCartService.deleteShopCart(shopCartIds, appUser.getId());

			return new ResultDto<>(200, "删除成功！", result);
		} else {
			return new ResultDto<>(403, "token失效请重新登录！");
		}
	}

}
