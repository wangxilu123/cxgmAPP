package com.cxgmerp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cxgmerp.dao.PermissionRoleDao;

@Service
public class PermissionRoleService {

	
	@Autowired
	PermissionRoleDao permissionRoleDao;
	
}
