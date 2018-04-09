package com.cxgmerp.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Component;

import com.cxgmerp.domain.PermissionRole;
@Component
public class PermissionRoleDao extends BaseDaoImpl<PermissionRole,Integer>{

	@Override
	public String getNameSpace() {
		return "sql.PermissionRole";
	}
	
	public SqlSessionTemplate getSqlSessionTemplate(){
		return super.getSqlSessionTemplate();
	}
}