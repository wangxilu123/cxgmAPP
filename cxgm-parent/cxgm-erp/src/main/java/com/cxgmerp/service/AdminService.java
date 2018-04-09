package com.cxgmerp.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cxgmerp.common.WebPage;
import com.cxgmerp.dao.AdminDao;
import com.cxgmerp.domain.Admin;

@Service
public class AdminService implements BaseService<Admin,Integer> {

	@Autowired
	AdminDao adminDao;
	
	
	public Admin findByUserName(String username) {
		return adminDao.findByName(username);
	}
	
	@Override
	public Integer insert(Admin t) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Integer update(Admin t) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Integer delete(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Admin findById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<Admin> findListAll() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public WebPage<Admin> findPage(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<Admin> findListAllWithMap(Map<String, Object> paramsMap) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public boolean existsEntity(Map<String, Object> paramsMap) {
		// TODO Auto-generated method stub
		return false;
	}

	
}
