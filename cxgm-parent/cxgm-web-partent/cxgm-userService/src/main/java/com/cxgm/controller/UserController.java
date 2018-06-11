package com.cxgm.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cxgm.common.ResultDto;
import com.cxgm.domain.AppUser;
import com.cxgm.domain.LoginEntity;
import com.cxgm.domain.PsfwTransfer;
import com.cxgm.domain.ShopResponse;
import com.cxgm.domain.UserAddress;
import com.cxgm.service.ShopService;
import com.cxgm.service.UserAddressService;
import com.cxgm.service.UserService;
import com.cxgm.service.impl.CheckToken;
import com.cxgm.service.impl.SmsVerificationCodeServiceImpl;
import com.github.pagehelper.PageInfo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * @Description 类说明:
 * @author 作者 E-mail: wangxilu
 */
@Api(description = "用户登录注册相关接口")
@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private UserAddressService addressService;

	@Autowired
	private ShopService shopService;

	@Autowired
	private CheckToken checkToken;

	@Autowired
	private SmsVerificationCodeServiceImpl smsVerificationCodeService;

	/*
	 * @ApiOperation(value = "用户注册", nickname = "用户注册")
	 * 
	 * @PostMapping("/register") public ResultDto<Integer>
	 * register(HttpServletRequest request, @RequestBody RegisterEntity
	 * registerEntity) throws InterruptedException { ResultDto<Integer> result =
	 * userService.addUser(registerEntity); return result; }
	 */

	@ApiOperation(value = "用户登录接口", nickname = "用户登录接口")
	@PostMapping("/login")
	public ResultDto<?> login(HttpServletRequest request, HttpServletResponse response, @RequestBody LoginEntity user) {

		Boolean result = smsVerificationCodeService.checkIsCorrectCode(user.getUserAccount(),
				user.getMobileValidCode());

		if (result == true) {

			AppUser appUser = userService.login(user);

			return new ResultDto<>(200, "登录成功！", appUser);
		} else {
			return new ResultDto<>(201, "验证码输入有误！");
		}
	}

	@ApiOperation(value = "新增用户地址接口", nickname = "新增用户地址接口")
	@PostMapping("/addAddress")
	public ResultDto<Integer> addAddress(HttpServletRequest request, @RequestBody UserAddress address) {

		AppUser appUser = checkToken.check(request.getHeader("token"));

		if (appUser != null) {

			address.setUserId(appUser.getId());
			Integer addressId = addressService.addAddress(address);

			return new ResultDto<>(200, "添加成功！", addressId);
		} else {
			return new ResultDto<>(403, "token失效请重新登录！");
		}
	}

	@ApiOperation(value = "判断用户配送地址是否在配送范围", nickname = "判断用户配送地址是否在配送范围")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "longitude", value = "经度", required = false, paramType = "query", dataType = "String"),
			@ApiImplicitParam(name = "dimension", value = "维度", required = false, paramType = "query", dataType = "String"), })
	@PostMapping("/checkAddress")
	public ResultDto<List<ShopResponse>> checkAddress(HttpServletRequest request,
			@RequestParam(value = "longitude", required = false) String longitude,
			@RequestParam(value = "dimension", required = false) String dimension) {

		List<ShopResponse> list = shopService.findShopByPoint(longitude, dimension);

		return new ResultDto<>(200, "成功！", list);
	}

	@ApiOperation(value = "查询所有门店列表", nickname = "查询所有门店列表")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "pageNum", value = "第几页，默认1", required = false, paramType = "query", dataType = "int"),
			@ApiImplicitParam(name = "pageSize", value = "每页多少条，默认10", required = false, paramType = "query", dataType = "int"), })
	@GetMapping("/shopList")
	public ResultDto<PageInfo<ShopResponse>> shopList(HttpServletRequest request,
			 @RequestParam(value = "pageNum", defaultValue = "1" , required = false) Integer pageNum,
	         @RequestParam(value = "pageSize", defaultValue = "10" , required = false) Integer pageSize) {

		PageInfo<ShopResponse> page = shopService.findShopListByPage(pageNum, pageSize);

		return new ResultDto<>(200, "成功！", page);
	}

	@ApiOperation(value = "查看所有配送范围接口", nickname = "查看所有配送范围接口")
	@ApiImplicitParam(name = "shopId", value = "门店ID", required = false, paramType = "query", dataType = "int")
	@GetMapping("/findAllPsfw")
	public ResultDto<List<PsfwTransfer>> checkAddress(HttpServletRequest request,
			@RequestParam(value = "shopId", required = false) Integer shopId) {

		List<PsfwTransfer> list = shopService.findPsfw(shopId);

		return new ResultDto<>(200, "成功！", list);
	}

	@ApiOperation(value = "修改用户地址接口", nickname = "修改用户地址接口")

	@PostMapping("/updateAddress")
	public ResultDto<Integer> updateAddress(HttpServletRequest request, @RequestBody UserAddress address) {

		AppUser appUser = checkToken.check(request.getHeader("token"));

		if (appUser != null) {

			address.setUserId(appUser.getId());
			Integer num = addressService.updateAddress(address);

			return new ResultDto<>(200, "修改成功！", num);
		} else {
			return new ResultDto<>(403, "token失效请重新登录！");
		}
	}

	@ApiOperation(value = "根据用户ID和地址ID删除用户地址接口", nickname = "根据用户ID和地址ID删除用户地址接口")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "addressId", value = "用户地址ID", required = false, paramType = "query", dataType = "Integer"), })
	@PostMapping("/deleteAddress")
	public ResultDto<Integer> deleteAddress(HttpServletRequest request,
			@RequestParam(value = "addressId", required = false) Integer addressId) {

		AppUser appUser = checkToken.check(request.getHeader("token"));

		if (appUser != null) {
			Integer num = addressService.deleteAddress(addressId, appUser.getId());

			return new ResultDto<>(200, "删除成功！", num);
		} else {
			return new ResultDto<>(403, "token失效请重新登录！");
		}
	}

	@ApiOperation(value = "根据用户ID查询用户地址接口", nickname = "根据用户ID查询用户地址接口")
	@GetMapping("/addressList")
	public ResultDto<List<UserAddress>> addressList(HttpServletRequest request) {

		AppUser appUser = checkToken.check(request.getHeader("token"));

		if (appUser != null) {

			List<UserAddress> result = addressService.addressList(appUser.getId());

			return new ResultDto<>(200, "查询成功！", result);
		} else {
			return new ResultDto<>(403, "token失效请重新登录！");
		}
	}

	/*
	 * @ApiOperation(value = "版本控制接口", nickname = "版本控制接口")
	 * 
	 * @PostMapping("/visionControl") public ResultDto
	 * visionControl(HttpServletRequest request, @RequestParam String
	 * visionCode) {
	 * 
	 * return ResultDto.ok("200"); }
	 */
}