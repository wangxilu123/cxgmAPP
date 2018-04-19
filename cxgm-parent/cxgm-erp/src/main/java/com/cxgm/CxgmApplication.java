package com.cxgm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@SpringBootApplication
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 20000)
public class CxgmApplication {

	public static void main(String[] args) {
		SpringApplication.run(CxgmApplication.class, args);
	}
}
