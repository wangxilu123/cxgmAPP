package com.cxgm.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cxgm.common.ResultDto;
import com.cxgm.domain.RegisterEntity;
import com.cxgm.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @Description 类说明:
 * @author 作者 E-mail: wangxilu
 */
@Api(description = "用户首页相关接口")
@RestController
@RequestMapping("/homePage")
public class HomePageController {

	@Autowired
	private UserService userService;
	
	@ApiOperation(value = "用户注册", nickname = "用户注册")
	@PostMapping("/register")
	public ResultDto<Integer> register(HttpServletRequest request, @RequestBody RegisterEntity registerEntity)
			throws InterruptedException {
		ResultDto<Integer> result = userService.addUser(registerEntity);
		return result;
	}

}