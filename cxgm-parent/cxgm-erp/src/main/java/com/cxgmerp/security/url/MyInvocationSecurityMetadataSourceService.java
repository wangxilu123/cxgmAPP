package com.cxgmerp.security.url;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Service;

import com.cxgmerp.dao.PermissionDao;
import com.cxgmerp.domain.PermissionAndRole;

@Service
public class MyInvocationSecurityMetadataSourceService implements FilterInvocationSecurityMetadataSource {

	@Autowired
    private PermissionDao permissionDao;
	
	private static List<PermissionAndRole> pars;
	
	//此方法是为了判定用户请求的url 是否在权限表中，如果在权限表中，则返回给 decide 方法，用来判定用户是否有此权限。如果不在权限表中则放行。
	@Override
	public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
		pars = permissionDao.findAllPermissions();
		//object 中包含用户请求的request信息
		HttpServletRequest request = ((FilterInvocation)object).getHttpRequest();
		//匹配所有的url，并对角色去重  
        Set<String> roles = new HashSet<String>();  
		AntPathRequestMatcher matcher;
		for(PermissionAndRole permissionAndRole : pars) {
			matcher = new AntPathRequestMatcher(permissionAndRole.getPurl());
			if(matcher.matches(request)) {
				roles.add(permissionAndRole.getRvalue());
			}
		}
		Collection<ConfigAttribute> cas = new ArrayList<ConfigAttribute>();   
        for(String role : roles){  
            ConfigAttribute ca = new SecurityConfig(role);  
            cas.add(ca);   
        }  
        return cas;  
	}

	@Override
	public Collection<ConfigAttribute> getAllConfigAttributes() {
		return null;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return true;
	}

}
