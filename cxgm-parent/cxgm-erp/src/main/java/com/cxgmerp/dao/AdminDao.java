package com.cxgmerp.dao;

import org.springframework.stereotype.Component;

import com.cxgmerp.domain.Admin;
@Component
public class AdminDao extends BaseDaoImpl<Admin,Long> {

	@Override
	public String getNameSpace() {
		return "sql.Admin";
	}
	
	public Admin findByName(String username) {
        return selectOne(getNameSpace() + ".findByUserName", username); 
    }
	public Admin findByAdminName(String name) {
        return selectOne(getNameSpace() + ".findByName", name); 
    }
	
}