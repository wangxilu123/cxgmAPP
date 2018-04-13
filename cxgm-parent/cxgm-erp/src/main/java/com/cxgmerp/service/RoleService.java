package com.cxgmerp.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cxgmerp.common.DateKit;
import com.cxgmerp.dao.AdminRoleDao;
import com.cxgmerp.dao.PermissionRoleDao;
import com.cxgmerp.dao.RoleDao;
import com.cxgmerp.domain.AdminRole;
import com.cxgmerp.domain.PermissionRole;
import com.cxgmerp.domain.Role;
import com.cxgmerp.exception.TipException;

@Service
public class RoleService {

	@Autowired
	RoleDao roleDao;
	@Autowired
	PermissionRoleDao permissionRoleDao;
	@Autowired
	AdminRoleDao adminRoleDao;
	
	public List<Role> findByUserName(String username){
		return roleDao.findByUserName(username);
	}
	public List<Role> findListAll() {
		return roleDao.findListAll();
	}
	
	@Transactional
	public void insert(String name,String value,String description,String[] resourceIds) {
		Role role = roleDao.selectByName(name);
		if(role!=null) {
			throw new TipException("角色名字已经存在");
		}
		role = new Role();
		role.setIsSystem(false);
		role.setCreationDate(DateKit.dateFormat(DateKit.dateFormat(new Date())));
		role.setDeleteFlag(false);
		role.setName(name);
		role.setValue(value);
		role.setDescription(description);
		roleDao.insert(role);
		if (resourceIds != null && resourceIds.length > 0) {
			List<PermissionRole> prs = new ArrayList<>();
			for(String resourceId : resourceIds){
				PermissionRole pr = new PermissionRole();
				pr.setPermissions(Long.valueOf(resourceId));
				pr.setRoles(role.getId());
				prs.add(pr);
			}
			permissionRoleDao.insertPermissionRolesBatch(prs);
		}
	}

	public Role selectById(Long id) {
		Role role = roleDao.findById(id);
		return role;
	}
	
	@Transactional
	public void update(String name,String value,String description,String[] resourceIds,Long id) {
		Role role = roleDao.findById(id);
		if(role==null) {
			throw new TipException("角色不存在");
		}
		if(role.getIsSystem()) {
			throw new TipException("系统内置角色不允许修改!");
		}
		if (resourceIds != null && resourceIds.length > 0) {
			permissionRoleDao.deleteByRoles(role.getId());
			for(String resouce : resourceIds) {
				PermissionRole pr = new PermissionRole();
				pr.setPermissions(Long.valueOf(resouce));
				pr.setRoles(role.getId());
				permissionRoleDao.insert(pr);
			}
		}
		role.setLastUpdatedDate(DateKit.dateFormat(DateKit.dateFormat(new Date())));
		role.setName(name);
		role.setValue(value);
		role.setDescription(description);
		roleDao.update(role);
	}
	
	
	public Role selectByName(String name) {
		return  roleDao.selectByName(name);
	}
	
	@Transactional
	public int delete(String[] roleIds) {
		int resultDelete = 0;
		for(String roleId : roleIds) {
			List<AdminRole> adminRoles = adminRoleDao.findByRoles(Long.valueOf(roleId));
			if(adminRoles.size()>0 ) {
				return Integer.valueOf(roleId);
			}
			permissionRoleDao.deleteByRoles(Long.valueOf(roleId));
			roleDao.delete(Long.valueOf(roleId));
		}
		resultDelete = 1;
		return resultDelete;
	}
	
}
