package com.cxgm.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.xml.rpc.ServiceException;
import javax.xml.soap.SOAPException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cxgm.service.HaixinGoodService;

/**
 * @Description 类说明:海信同步商品数据
 */
@Configuration
@EnableScheduling
@RequestMapping("/haixin")
public class HaixinController {

	@Autowired
	private HaixinGoodService haixinGoodService;
	
	@GetMapping("/goodList")
	public void getGoods() throws UnsupportedEncodingException, SOAPException, ServiceException, IOException {

		haixinGoodService.findHaiXingoods();

	}
	
}
