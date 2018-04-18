package com.cxgm.security;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cxgm.domain.Admin;
import com.cxgm.domain.Permission;
import com.cxgm.domain.Role;
import com.cxgm.service.AdminService;
import com.cxgm.service.PermissionService;
import com.cxgm.service.RoleService;

@Service
public class CustomUserService implements UserDetailsService {

	
	@Autowired
	RoleService roleService;
	
	@Autowired
	AdminService adminService;
	
	@Autowired
    PermissionService permissionService;
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Admin admin = adminService.findByUserName(username);//获取用户信息
		
		List<Role> roles = roleService.findByUserName(username);//获取用户对应的角色
		
		List<Permission> permissionSet = new ArrayList<>();
		
		List<Permission> menuPermissionList = new ArrayList<>();
		
		Set<Integer> permissionIDset = new HashSet<Integer>();//保存用户的所有权限
		if(roles.size()>0) {
			for(Role role:roles) {
				List<Permission> ps = permissionService.findByRole(role.getId());//根据角色获取对应的权限
				for(Permission p:ps) {
					permissionIDset.add(p.getId().intValue());
				}
			}
		}
		
		List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
		for(Role role : roles) {
			GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(role.getValue());
			grantedAuthorities.add(grantedAuthority);
		}
		admin.setAuthorities(grantedAuthorities);
		if(permissionIDset.size()==0) {
			return admin;
		}
		permissionSet = permissionService.findListByIds(new ArrayList<Integer>(permissionIDset));
		if(permissionSet.size()>0) {
			for(Permission p: permissionSet) {
				if(null!= p.getPid()&& p.getPid()==0) {
					menuPermissionList.add(p);
				}
			}
			for(Permission per:menuPermissionList) {
				for(Permission pe:permissionSet) {
					if(null!= pe.getPid() && pe.getPid() == per.getId().intValue() && null != pe.getType() && pe.getType() == 0) {
						per.getChildList().add(pe);
					}
				}
			}
			admin.setPermissionList(menuPermissionList);
			return admin;
		}
		throw new UsernameNotFoundException("admin: " + username + " do not exist!");
	}

}
