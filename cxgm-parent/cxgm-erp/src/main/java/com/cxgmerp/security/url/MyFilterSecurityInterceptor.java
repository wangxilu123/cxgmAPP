package com.cxgmerp.security.url;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;


@Component
public class MyFilterSecurityInterceptor extends AbstractSecurityInterceptor implements Filter {

	@Autowired
	private FilterInvocationSecurityMetadataSource securityMetadataSource;
	
	@Autowired  
    private MyAccessDecisionManager myAccessDecisionManager; 
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@PostConstruct  
    public void init(){  
		super.setAuthenticationManager(authenticationManager); 
        super.setAccessDecisionManager(myAccessDecisionManager);
    }  
	
	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		FilterInvocation fi = new FilterInvocation(request,response,chain);
		invoke(fi);

	}
	
	
	public void invoke(FilterInvocation fi)  throws IOException, ServletException {
		/*
		 * 	//fi里面有一个被拦截的url
			//里面调用MyInvocationSecurityMetadataSource的getAttributes(Object object)这个方法获取fi对应的所有权限
			//再调用MyAccessDecisionManager的decide方法来校验用户的权限是否足够
		 * 
		 * 
		 * 它首先会调用MyInvocationSecurityMetadataSource类的getAttributes方法获取被拦截url所需的权限
		 * */
		InterceptorStatusToken token = super.beforeInvocation(fi);
		try {
			//执行下一个拦截器  
			fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
		} finally {
			super.afterInvocation(token, null);
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}

	
	@Override
	public Class<?> getSecureObjectClass() {
		return FilterInvocation.class;
	}

	@Override
	public SecurityMetadataSource obtainSecurityMetadataSource() {
		return this.securityMetadataSource;
	}

}
