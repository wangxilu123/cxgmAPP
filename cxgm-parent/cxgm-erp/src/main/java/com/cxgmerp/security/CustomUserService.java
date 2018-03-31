package com.cxgmerp.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cxgmerp.dao.SysPermissionDao;
import com.cxgmerp.dao.SysUserDao;
import com.cxgmerp.domain.SysPermission;
import com.cxgmerp.domain.SysUser;

@Service
public class CustomUserService implements UserDetailsService {

	
	@Autowired
	SysUserDao sysUserDao;
	
	@Autowired
	SysPermissionDao sysPermissionDao;
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		SysUser user = sysUserDao.findByUserName(username);
		
		if(null != user) {
			List<SysPermission> permissions = sysPermissionDao.findByAdminUserId(user.getId());
			List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
			for(SysPermission permission : permissions) {
				if(null != permission.getName()) {
					GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(permission.getName());
					grantedAuthorities.add(grantedAuthority);
				}
			}
			return new SecurityUser(user.getUsername(),user.getPassword(),grantedAuthorities);
		}else {
			throw new UsernameNotFoundException("admin: " + username + " do not exist!");
		}
	}

}
