package com.cxgm.eureka.register;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class ServiceRegistryApplication {
    private static Logger log = LoggerFactory.getLogger(ServiceRegistryApplication.class);
    public static void main(String[] args) {
        SpringApplication.run(ServiceRegistryApplication.class, args);
        log.info("ServiceRegistryApplication started");
    }
    
}
