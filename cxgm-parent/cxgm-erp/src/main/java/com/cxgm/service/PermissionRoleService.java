package com.cxgm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cxgm.dao.PermissionRoleMapper;

@Service
public class PermissionRoleService {

	
	@Autowired
	PermissionRoleMapper permissionRoleDao;
	
}
