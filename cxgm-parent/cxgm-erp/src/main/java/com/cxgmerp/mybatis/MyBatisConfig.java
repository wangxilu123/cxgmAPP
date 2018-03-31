package com.cxgmerp.mybatis;

import com.github.pagehelper.PageHelper;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.PlatformTransactionManager;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Configuration
public class MyBatisConfig {

    @Value("${spring.datasource.readSize}")
    private String dataSourceSize;

    @Resource(name = "MASTER")
    private DataSource master;

    @Resource(name = "SLAVE")
    private DataSource slave;


    @Bean
    public AbstractRoutingDataSource dataSourceProxy() {
        DynamicDataSource proxy = new DynamicDataSource();
        Map<Object, Object> dataSourceMap = new HashMap<Object, Object>();

        dataSourceMap.put(DataSourceType.MASTER, master);
        dataSourceMap.put(DataSourceType.SLAVE, slave);

        proxy.setDefaultTargetDataSource(master);
        proxy.setTargetDataSources(dataSourceMap);
        return proxy;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSourceProxy());
    }

    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactorys() {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSourceProxy());
        bean.setTypeAliasesPackage("com.cxgmerp");

        //分页插件
        PageHelper pageHelper = new PageHelper();
        Properties properties = new Properties();
        properties.setProperty("reasonable", "true");
        properties.setProperty("supportMethodsArguments", "true");
        properties.setProperty("returnPageInfo", "check");
        properties.setProperty("params", "count=countSql");
        pageHelper.setProperties(properties);

        //添加插件
        bean.setPlugins(new Interceptor[]{pageHelper});

        //添加XML目录
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        try {
            bean.setMapperLocations(resolver.getResources("classpath:mappers/*.xml"));
            return bean.getObject();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplate() {
        return new SqlSessionTemplate(sqlSessionFactorys());
    }
}
