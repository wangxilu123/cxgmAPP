package com.cxgmerp.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Component;

import com.cxgmerp.domain.Permission;
import com.cxgmerp.domain.PermissionAndRole;
@Component
public class PermissionDao extends BaseDaoImpl<Permission,Integer>{

	@Override
	public String getNameSpace() {
		return "sql.Permission";
	}
	
	public SqlSessionTemplate getSqlSessionTemplate(){
		return super.getSqlSessionTemplate();
	}
	
	 public List<PermissionAndRole> findAllPermissions(){
		 return this.getSqlSessionTemplate().selectList(getNameSpace()+".findAllPermissions");
	 }
	    
	 public List<Permission> findByRole(Long roleid){
		return this.getSqlSessionTemplate().selectList(getNameSpace()+".findByRole",roleid);
	 }
}