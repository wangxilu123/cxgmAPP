package com.cxgm.controller;

import javax.servlet.http.HttpServletRequest;

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
import com.cxgm.service.UserService;

import io.swagger.annotations.Api;
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

	@ApiOperation(value = "用户注册", nickname = "用户注册")
	@PostMapping("/register")
	public ResultDto<AppUser> register(HttpServletRequest request, @RequestBody RegisterEntity registerEntity)
			throws InterruptedException {
		ResultDto<AppUser> result = userService.addUser(registerEntity);
		return result;
	}

	@ApiOperation(value = "用户登录接口", nickname = "用户登录接口")
	@PostMapping("/login")
	public ResultDto<AppUser> login(HttpServletRequest request, @RequestBody LoginEntity user) {

		ResultDto<AppUser> result = userService.login(user);
		
		return result;
	}

	@ApiOperation(value = "版本控制接口", nickname = "版本控制接口")
	@PostMapping("/visionControl")
	public ResultDto visionControl(HttpServletRequest request, @RequestParam String visionCode) {

		return ResultDto.ok("200");
	}
}