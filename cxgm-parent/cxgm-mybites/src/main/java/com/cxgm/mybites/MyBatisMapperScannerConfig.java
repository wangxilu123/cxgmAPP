package com.cxgm.mybites;

import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * Created by Athos on 2016-10-06.
 */
@Configuration
//TODO 注意，由于MapperScannerConfigurer执行的比较早，所以必须有下面的注解
@AutoConfigureAfter(MyBatisConfig.class)
public class MyBatisMapperScannerConfig {
    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer() {
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
        //这里的BasePackage 不能写cn.com.gomefinance,估计跟spring 的扫描冲突,会实例化两个service,应该需要重构目录
        mapperScannerConfigurer.setBasePackage("com.cxgm.*.mapper,;com.cxgm.dao,;" );
        //        Properties properties = new Properties();
        //        properties.setProperty("mappers", Mapper.class.getName());
        //        properties.setProperty("notEmpty", "false");
        //        properties.setProperty("IDENTITY", "MYSQL");
        //        mapperScannerConfigurer.setProperties(properties);
        return mapperScannerConfigurer;
    }
}