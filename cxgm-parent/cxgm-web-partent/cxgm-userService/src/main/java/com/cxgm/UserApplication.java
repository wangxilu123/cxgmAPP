package com.cxgm;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;


/**
* @Description 类说明: 
* @author wangxilu
*/
@SpringBootApplication
@EnableRedisHttpSession//session的默认时间是在CustomUsernamePasswordAuthenticationFilter里设置的
@EnableEurekaClient
@MapperScan(value = "com.cxgm.**")
@EnableAsync
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
public class UserApplication {
    
    private static Logger log = LoggerFactory.getLogger(UserApplication.class);
    
    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }
}
