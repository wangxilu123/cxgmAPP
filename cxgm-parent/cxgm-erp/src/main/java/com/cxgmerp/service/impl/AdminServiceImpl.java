package com.cxgmerp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.cxgmerp.dao.AdminDao;
import com.cxgmerp.domain.Admin;
import com.cxgmerp.service.AdminService;

public class AdminServiceImpl implements AdminService {

	@Autowired
	AdminDao adminDao;
	@Override
	public Admin findByUserName(String username) {
		
		return adminDao.findByUserName(username);
	}

	
}
