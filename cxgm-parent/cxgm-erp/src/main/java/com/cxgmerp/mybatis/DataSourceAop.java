package com.cxgmerp.mybatis;


import java.lang.reflect.Method;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

//@Aspect
//@Component
public class DataSourceAop {
	
//	@Before("execution(* com.demo.security.service.impl.*.*(..))")  
//    public void switchDataSource(JoinPoint pjp) throws Throwable{  
//        Signature signature = pjp.getSignature();      
//        MethodSignature methodSignature = (MethodSignature)signature;    
//        Method targetMethod = methodSignature.getMethod();  
//        Method realMethod = pjp.getTarget().getClass().getDeclaredMethod(signature.getName(), targetMethod.getParameterTypes());  
//            //首先判断方法级别  
//        DataSource cds=realMethod.getAnnotation(DataSource.class);  
//            if(cds==null){  //默认库  
//                DataSourceContextHolder.setDataSourceType(DataSourceType.MASTER);;  
//                return;  
//            }  
//            DataSourceType dataSourceName=cds.value(); //获取注解的值  
//               if(dataSourceName!=null)  //通过数据源路由类切换数据源  
//                  DataSourceContextHolder.setDataSourceType(DataSourceType.SLAVE);;  
//    }  
}
