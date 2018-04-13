package com.cxgmerp.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.cxgmerp.domain.AdminRole;
@Component
public class AdminRoleDao extends BaseDaoImpl<AdminRole,Long>{

	@Override
	public String getNameSpace() {
		return "sql.AdminRole";
	}
	
	public List<AdminRole> findByRoles(Long roles){
		return selectList(this.getNameSpace()+".findByRoles",roles);
	}
	
	public int insertAdminRolesBatch(List<AdminRole> adminRole) {
		return this.getSqlSessionTemplate().insert(this.getNameSpace()+".insertAdminRolesBatch", adminRole);
	}
	public List<AdminRole> findByAdmins(Long admins){
		return selectList(this.getNameSpace()+".findByAdmins",admins);
	}
	
	public int deleteByAdmins(Long admins) {
		return this.getSqlSessionTemplate().delete(this.getNameSpace()+".deleteByAdmins", admins);
	}
}