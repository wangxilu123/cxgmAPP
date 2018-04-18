package com.cxgm.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cxgm.common.DateKit;
import com.cxgm.dao.PermissionMapper;
import com.cxgm.dao.PermissionRoleMapper;
import com.cxgm.domain.Permission;
import com.cxgm.domain.PermissionAndRole;
import com.cxgm.domain.PermissionRole;
import com.cxgm.exception.TipException;

@Service
public class PermissionService {

	@Autowired
	PermissionMapper permissionDao;
	
	@Autowired
	PermissionRoleMapper permissionRoleDao;

	public List<Permission> findFatherResource() {
		return this.permissionDao.findListType0();
	}

	public List<Permission> findActionResource() {
		return this.permissionDao.findListType1();
	}

	public List<Permission> findListByIds(List<Integer> list) {
		return this.permissionDao.findListByIds(list);
	}

	public List<PermissionAndRole> findAllPermissions() {
		return permissionDao.findAllPermissions();
	}

	public List<Permission> findByRole(Long roleid) {
		return permissionDao.findByRole(roleid);
	}

	public List<Permission> findListAll() {
		return permissionDao.findListAll();
	}

	public List<Permission> findByName(String name) {
		return permissionDao.findByName(name);
	}

	public Permission findById(Long id) {
		return permissionDao.findById(id);
	}

	@Transactional
	public void insert(Integer pid, String description, String name, Integer type, String url) {
		List<Permission> permissions = permissionDao.findByName(name);
		if (permissions.size()>0) {
			throw new TipException("资源名已经存在");
		}
		Permission permission = new Permission();
		permission.setCreationDate(DateKit.dateFormat(DateKit.dateFormat(new Date())));
		permission.setDeleteFlag(false);
		if (pid != 0) {
			Permission p = permissionDao.findById(pid.longValue());
			permission.setDescription(p.getName() + " (" + description + ") ");
		} else {
			permission.setDescription(name + " (" + description + ") ");
		}
		permission.setName(name);
		permission.setUrl(url);
		permission.setPid(pid);
		permission.setType(type);
		permission.setValue("Admin");
		permissionDao.insert(permission);
	}

	@Transactional
	public void update(Long id, Integer pid, String description, String name, Integer type, String url) {
		Permission permission = permissionDao.findById(id);
		if (null == permission) {
			throw new TipException("资源不存在");
		}
		permission.setLastUpdatedDate(DateKit.dateFormat(DateKit.dateFormat(new Date())));
		if (permission.getPid() == 0) {
			throw new TipException("资源是一级资源,不能添为此添加父类资源");
		}
		Permission p = permissionDao.findById(pid.longValue());
		if (description.contains(p.getName())) {
			permission.setDescription(description);
		} else {
			permission.setDescription(p.getName() + " (" + description + ") ");
		}
		permission.setName(name);
		permission.setUrl(url);
		permission.setPid(pid);
		permission.setType(type);
		permissionDao.update(permission);
	}
	
	@Transactional
	public int delete(String[] resourceIds) {
		int resultDelete = 0;
		if (resourceIds != null && resourceIds.length > 0) {
			for(String resourceId : resourceIds) {
				List<PermissionRole> prs = permissionRoleDao.findByPermissions(Long.valueOf(resourceId));
				if(prs.size()>0) {
					return Integer.valueOf(resourceId);
				}else {
					permissionDao.delete(Long.valueOf(resourceId));
				}
			}
		}
		resultDelete = 1;
		return resultDelete;
	}
}
