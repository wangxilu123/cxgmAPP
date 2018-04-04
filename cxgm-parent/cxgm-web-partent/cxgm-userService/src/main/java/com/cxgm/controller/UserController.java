package com.cxgm.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cxgm.common.ResultDto;
import com.cxgm.domain.AppUser;
import com.cxgm.domain.RegisterEntity;
import com.cxgm.service.RedisService;
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

	@Autowired
	private RedisService redisService;

	@ApiOperation(value = "用户注册", nickname = "用户注册")
	@PostMapping("/register")
	public ResultDto register(HttpServletRequest request, @RequestBody RegisterEntity registerEntity)
			throws InterruptedException {

		AppUser user = userService.selectByMobile(registerEntity.getMobile());
		if (user != null) {
			return ResultDto.error("该手机号已注册，请重试！");
		}

		// 校验短信验证码
		String code = (String) redisService.get(registerEntity.getMobile());

		if ("".equals(code) == false && code.equals(registerEntity.getMobileValidCode())) {
			return ResultDto.error("验证码有误，请重新输入！");
		} else {
			// 删除键值对
			redisService.remove(registerEntity.getMobile());
		}
		userService.addUser(registerEntity);
		return ResultDto.ok("注册成功！");
	}
}