package com.cxgmerp.dao;

import org.springframework.stereotype.Component;

import com.cxgmerp.domain.AdminRole;
@Component
public class AdminRoleDao extends BaseDaoImpl<AdminRole,Integer>{

	@Override
	public String getNameSpace() {
		return "sql.AdminRole";
	}
}