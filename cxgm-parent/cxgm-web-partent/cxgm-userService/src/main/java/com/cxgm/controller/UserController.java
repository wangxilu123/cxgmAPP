package com.cxgm.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cxgm.common.ResultDto;
import com.cxgm.domain.AppUser;
import com.cxgm.domain.LoginEntity;
import com.cxgm.domain.RegisterEntity;
import com.cxgm.domain.Shop;
import com.cxgm.domain.ShopResponse;
import com.cxgm.domain.UserAddress;
import com.cxgm.service.ShopService;
import com.cxgm.service.UserAddressService;
import com.cxgm.service.UserService;
import com.cxgm.service.impl.SmsVerificationCodeServiceImpl;

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
	private SmsVerificationCodeServiceImpl smsVerificationCodeService;

	/*@ApiOperation(value = "用户注册", nickname = "用户注册")
	@PostMapping("/register")
	public ResultDto<Integer> register(HttpServletRequest request, @RequestBody RegisterEntity registerEntity)
			throws InterruptedException {
		ResultDto<Integer> result = userService.addUser(registerEntity);
		return result;
	}*/

	@ApiOperation(value = "用户登录接口", nickname = "用户登录接口")
	@PostMapping("/login")
	public ResultDto<?> login(HttpServletRequest request,HttpServletResponse response, @RequestBody LoginEntity user) {

		Boolean result = smsVerificationCodeService.checkIsCorrectCode(user.getUserAccount(), user.getMobileValidCode());
		
		if(result==true){
			
			AppUser appUser = userService.login(user);
			
			return new ResultDto<>(200,"登录成功！",appUser);
		}else{
			return new ResultDto<>(201,"验证码输入有误！");
		}
	}
	
	@ApiOperation(value = "新增用户地址接口", nickname = "新增用户地址接口")
	@PostMapping("/addAddress")
	public ResultDto<Integer> addAddress(HttpServletRequest request, @RequestBody UserAddress address) {

		Integer addressId = addressService.addAddress(address);
		
		return new ResultDto<>(200,"添加成功！",addressId);
	}
	
	@ApiOperation(value = "判断用户配送地址是否在配送范围", nickname = "判断用户配送地址是否在配送范围")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "longitude", value = "经度", required = false, paramType = "query", dataType = "String"),
        @ApiImplicitParam(name = "dimension", value = "维度", required = false, paramType = "query", dataType = "String"),
    })
	@PostMapping("/checkAddress")
	public ResultDto<List<ShopResponse>> checkAddress(HttpServletRequest request,
			@RequestParam(value = "longitude", required = false) String longitude,
            @RequestParam(value = "dimension", required = false) String dimension) {

		List<ShopResponse> list= shopService.findShopByPoint(longitude,dimension);
		
		return new ResultDto<>(200,"成功！",list);
	}
	
	@ApiOperation(value = "修改用户地址接口", nickname = "修改用户地址接口")
	
	@PostMapping("/updateAddress")
	public ResultDto<Integer> updateAddress(HttpServletRequest request, @RequestBody UserAddress address) {

		Integer num = addressService.updateAddress(address);
		
		return new ResultDto<>(200,"修改成功！",num);
	}
	
	@ApiOperation(value = "根据用户ID和地址ID删除用户地址接口", nickname = "根据用户ID和地址ID删除用户地址接口")
	@ApiImplicitParams({
        
        @ApiImplicitParam(name = "userId", value = "用户ID", required = false, paramType = "query", dataType = "Integer"),
        @ApiImplicitParam(name = "addressId", value = "用户地址ID", required = false, paramType = "query", dataType = "Integer"),
    })
	@PostMapping("/deleteAddress")
	public ResultDto<Integer> deleteAddress(HttpServletRequest request, 
			@RequestParam(value = "userId", required = false) Integer userId,
            @RequestParam(value = "addressId", required = false) Integer addressId) {

		Integer num = addressService.deleteAddress(addressId, userId);
		
		return new ResultDto<>(200,"添加成功！",num);
	}

	@ApiOperation(value = "版本控制接口", nickname = "版本控制接口")
	@PostMapping("/visionControl")
	public ResultDto visionControl(HttpServletRequest request, @RequestParam String visionCode) {

		return ResultDto.ok("200");
	}
}