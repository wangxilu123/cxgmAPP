package com.cxgmerp.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cxgmerp.common.DateKit;
import com.cxgmerp.dao.AdminDao;
import com.cxgmerp.dao.AdminRoleDao;
import com.cxgmerp.domain.Admin;
import com.cxgmerp.domain.AdminRole;
import com.cxgmerp.exception.TipException;

@Service
public class AdminService {

	@Autowired
	AdminDao adminDao;
	@Autowired
	AdminRoleDao adminRoleDao;

	public Admin findByUserName(String username) {
		return adminDao.findByName(username);
	}
	public Admin findByName(String name) {
		return adminDao.findByAdminName(name);
	}

	public List<Admin> findListAll() {
		return adminDao.findListAll();
	}

	public Admin findById(Long id) {
		return adminDao.findById(id);
	}

	@Transactional
	public void insert(String username, String password, String name, Boolean isAccountEnabled, String email,
			String department, String[] roleIds) {
		Admin admin = adminDao.findByName(username);
		if (admin != null) {
			throw new TipException("用户名已经存在");
		}
		admin = new Admin();
		admin.setUsername(username);
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		admin.setPassword(encoder.encode(password.trim()));
		admin.setName(name);
		admin.setIsEnabled(isAccountEnabled);
		admin.setEmail(email);
		admin.setDepartment(department);
		admin.setDeleteFlag(false);
		admin.setIsLocked(false);
		admin.setCreationDate(DateKit.dateFormat(DateKit.dateFormat(new Date())));
		adminDao.insert(admin);
		if (roleIds != null && roleIds.length > 0) {
			List<AdminRole> ars = new ArrayList<>();
			for (String roleId : roleIds) {
				AdminRole ar = new AdminRole();
				ar.setAdmins(admin.getId());
				ar.setRoles(Long.valueOf(roleId));
				ars.add(ar);
			}
			adminRoleDao.insertAdminRolesBatch(ars);
		}
	}

	@Transactional
	public int delete(String[] adminIds) {
		int resultDelete = 0;
		if (adminIds != null && adminIds.length > 0) {
			for(String adminId : adminIds) {
				adminRoleDao.deleteByAdmins(Long.valueOf(adminId));
				adminDao.delete(Long.valueOf(adminId));
			}
		}
		resultDelete = 1;
		return resultDelete;
	}

	@Transactional
	public void update(String username, String password, String name, Boolean isAccountEnabled, String email,
			String department, String[] roleIds,Long id) {
		Admin admin = adminDao.findById(id);
		if(admin==null) {
			throw new TipException("用户不存在");
		}
		if(admin.getName().equals("admin")) {
			throw new TipException("系统内置用户不允许修改!");
		}
		
		if(null != roleIds && roleIds.length>0) {
			adminRoleDao.deleteByAdmins(Long.valueOf(id));
			for(String roleId : roleIds) {
				AdminRole ar = new AdminRole();
				ar.setAdmins(id);
				ar.setRoles(Long.valueOf(roleId));
				adminRoleDao.insert(ar);
			}
		}
		admin.setUsername(username);
		if(null != password && !"".equals(password)) {
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			admin.setPassword(encoder.encode(password.trim()));
		}
		admin.setIsEnabled(isAccountEnabled);
		admin.setEmail(email);
		admin.setDepartment(department);
		admin.setLastUpdatedDate(DateKit.dateFormat(DateKit.dateFormat(new Date())));
		adminDao.update(admin);
	}
}
