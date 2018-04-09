package com.cxgmerp.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cxgmerp.common.WebPage;
import com.cxgmerp.dao.RoleDao;
import com.cxgmerp.domain.Role;

@Service
public class RoleService implements BaseService<RoleService, Integer> {

	@Autowired
	RoleDao roleDao;
	public List<Role> findByUserName(String username){
		return roleDao.findByUserName(username);
	}
	
	@Override
	public Integer insert(RoleService t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer update(RoleService t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer delete(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RoleService findById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<RoleService> findListAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WebPage<RoleService> findPage(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<RoleService> findListAllWithMap(Map<String, Object> paramsMap) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean existsEntity(Map<String, Object> paramsMap) {
		// TODO Auto-generated method stub
		return false;
	}

}
