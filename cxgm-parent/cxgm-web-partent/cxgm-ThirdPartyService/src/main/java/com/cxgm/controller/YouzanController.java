package com.cxgm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.cxgm.service.YouzanOrderService;

/**
 * @Description 类说明:定时任务
 */
@Configuration
@EnableScheduling
public class YouzanController {

	@Autowired
	private YouzanOrderService youzanOrderService;
	
	@Scheduled(cron = "0 */120 * * * ?") // 每10分钟执行一次
	public void getOrders() {

		youzanOrderService.findYouZanOrder();

		System.out.println("1111111111111111111111111+定时任务启动+11111111111111111111111");
	}
	
}
