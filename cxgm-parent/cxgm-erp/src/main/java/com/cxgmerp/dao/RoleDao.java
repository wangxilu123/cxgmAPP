package com.cxgmerp.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Component;

import com.cxgmerp.domain.Role;

@Component
public class RoleDao extends BaseDaoImpl<Role,Integer>{

	@Override
	public String getNameSpace() {
		return "sql.Role";
	}
	public SqlSessionTemplate getSqlSessionTemplate(){
		return super.getSqlSessionTemplate();
	}
	public List<Role> findByUserName(String username){
		return selectList(getNameSpace()+".findByUserName",username);
	}
    
}