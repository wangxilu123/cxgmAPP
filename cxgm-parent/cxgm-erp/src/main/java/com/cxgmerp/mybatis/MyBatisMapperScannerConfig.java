package com.cxgmerp.mybatis;

import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class MyBatisMapperScannerConfig {
    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer() {
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
        //这里的BasePackage 多个目录使用,; 分割例如"com.ecochain.*.mapper,; com.ecochain.wallet.*.mapper"
        mapperScannerConfigurer.setBasePackage("com.cxgmerp.dao,;" );
        return mapperScannerConfigurer;
    }
}