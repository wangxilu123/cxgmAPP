package com.cxgmerp.dao;

import java.util.List;

import com.cxgmerp.domain.SysPermission;
public interface SysPermissionDao {
	 public List<SysPermission> findAll();
	 public List<SysPermission> findByAdminUserId(int userId);
}