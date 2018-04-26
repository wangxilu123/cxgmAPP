package com.cxgm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lucaslee
 */
@SpringBootApplication
@EnableRedisHttpSession//session的默认时间是在CustomUsernamePasswordAuthenticationFilter里设置的
@EnableEurekaClient
@RestController
@EnableFeignClients(basePackages = "com.cxgm.**")
@EnableAsync
@RefreshScope
public class BusinessServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(BusinessServiceApplication.class, args);
	}
}