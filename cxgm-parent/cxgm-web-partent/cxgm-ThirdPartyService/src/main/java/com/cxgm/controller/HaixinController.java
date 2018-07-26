package com.cxgm.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.xml.rpc.ServiceException;
import javax.xml.soap.SOAPException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cxgm.domain.Order;
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
	
	@PostMapping("/upload")
	public void upload(HttpServletRequest request, @RequestBody  Order order) throws UnsupportedEncodingException, SOAPException, ServiceException, IOException {

		haixinGoodService.upload(order);

	}
	
}
