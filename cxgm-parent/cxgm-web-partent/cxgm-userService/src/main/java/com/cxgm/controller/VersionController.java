package com.cxgm.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cxgm.common.ResultDto;
import com.cxgm.domain.Version;
import com.cxgm.service.VersionService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @Description 类说明:
 * @author 作者 E-mail: wangxilu
 */
@Api(description = "版本控制相关接口")
@RestController
@RequestMapping("/version")
public class VersionController {

	@Autowired
	private VersionService versionService;

	@ApiOperation(value = "版本控制接口", nickname = "版本控制接口")
	@GetMapping("/getVersion")
	public ResultDto<Version> getVersion(HttpServletRequest request) {

		Version version = versionService.getVersion();
		return new ResultDto<>(200, "查询成功！", version);
	}
}