package com.cxgmerp.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cxgmerp.common.WebPage;
import com.cxgmerp.dao.PermissionDao;
import com.cxgmerp.domain.Permission;
import com.cxgmerp.domain.PermissionAndRole;

@Service
public class PermissionService implements BaseService<Permission, Integer> {

	@Autowired
	PermissionDao permissionDao;
	
	public List<Permission> findListType0(){
		return this.permissionDao.getSqlSessionTemplate().selectList(this.permissionDao.getStatement("findListType0"));
	}
	
	public List<Permission> findListType1(){
		return this.permissionDao.getSqlSessionTemplate().selectList(this.permissionDao.getStatement("findListType1"));
	}
	
	public List<Permission> findListByIds(List<Integer> list){
		return this.permissionDao.getSqlSessionTemplate().selectList(this.permissionDao.getNameSpace() + ".findListByIds", list);
	}
	
	public List<PermissionAndRole> findAllPermissions(){
		 return permissionDao.findAllPermissions();
	 }
	    
	 public List<Permission> findByRole(Long roleid){
		return permissionDao.findByRole(roleid);
	 }
	@Override
	public Integer insert(Permission t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer update(Permission t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer delete(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Permission findById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Permission> findListAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WebPage<Permission> findPage(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Permission> findListAllWithMap(Map<String, Object> paramsMap) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean existsEntity(Map<String, Object> paramsMap) {
		// TODO Auto-generated method stub
		return false;
	}

}
