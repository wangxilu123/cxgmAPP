package com.cxgmerp.security;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cxgmerp.dao.AdminDao;
import com.cxgmerp.dao.PermissionDao;
import com.cxgmerp.dao.RoleDao;
import com.cxgmerp.domain.Admin;
import com.cxgmerp.domain.Permission;
import com.cxgmerp.domain.Role;

@Service
public class CustomUserService implements UserDetailsService {

	
	@Autowired
	RoleDao roleDao;
	
	@Autowired
	AdminDao adminDao;
	
	@Autowired
    private PermissionDao permissionDao;
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Admin admin = adminDao.findByUserName(username);//获取用户信息
		
		List<Role> roles = roleDao.findByUserName(username);//获取用户对应的角色
		
		Set<Permission> permissionset = new HashSet<Permission>();//保存用户的所有权限
		Iterator<Permission> it = permissionset.iterator();
		Permission perm;
		if(roles.size()>0) {
			for(Role role:roles) {
				List<Permission> ps = permissionDao.findByRole(role.getId());//根据角色获取对应的权限
				for(Permission p:ps) {
					while(it.hasNext()) {
						perm = it.next();
						if(perm.getId()!=p.getId()) {
							permissionset.add(p);
						}
					}
				}
			}
		}
		if(permissionset.size()>0) {
			List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
			for(Permission permission : permissionset) {
				GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(permission.getUrl());
				grantedAuthorities.add(grantedAuthority);
			}
			admin.setAuthorities(grantedAuthorities);
			admin.setRoles(roles);
			admin.setPermission(permissionset);
			return admin;
		}else {
			throw new UsernameNotFoundException("admin: " + username + " do not exist!");
		}
	}

}
