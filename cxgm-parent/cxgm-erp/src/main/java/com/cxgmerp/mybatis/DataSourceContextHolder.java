package com.cxgmerp.mybatis;

import org.springframework.util.Assert;

public class DataSourceContextHolder {
	// 线程本地环境
    private static final ThreadLocal<DataSourceType> contextHolder = new ThreadLocal<DataSourceType>();

    // 设置数据源类型
    public static void setDataSourceType(DataSourceType dataSourceType) {
        Assert.notNull(dataSourceType, "DataSourceType cannot be null");
        contextHolder.set(dataSourceType);
    }

    // 获取数据源类型
    public static DataSourceType getDataSourceType() {
        return (DataSourceType) contextHolder.get();
    }

    // 清除数据源类型
    public static void clearDataSourceType() {
        contextHolder.remove();
    }
}
