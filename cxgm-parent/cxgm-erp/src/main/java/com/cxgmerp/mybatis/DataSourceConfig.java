package com.cxgmerp.mybatis;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class DataSourceConfig {

	@Value("${spring.datasource.type}")
	private Class<? extends DataSource> dataSourceType;

	@Bean(name = "MASTER")
	@Primary
	@ConfigurationProperties(prefix = "spring.datasource.MASTER")
	public DataSource masterDataSource() {
		return DataSourceBuilder.create().type(dataSourceType).build();
	}

	@Bean(name = "SLAVE")
	@ConfigurationProperties(prefix = "spring.datasource.SLAVE")
	public DataSource slaveDataSourceOne() {
		return DataSourceBuilder.create().type(dataSourceType).build();
	}
}
