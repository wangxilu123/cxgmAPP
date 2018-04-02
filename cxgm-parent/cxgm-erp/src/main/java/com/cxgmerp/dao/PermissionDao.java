package com.cxgmerp.dao;

import java.util.List;

import com.cxgmerp.domain.Permission;
import com.cxgmerp.domain.PermissionAndRole;

public interface PermissionDao {
	
    List<PermissionAndRole> findAll();
    
    List<Permission> findByRole(Long roleid);
}