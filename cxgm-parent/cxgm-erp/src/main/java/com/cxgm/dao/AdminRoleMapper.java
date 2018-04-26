package com.cxgm.dao;

import java.util.List;


import com.cxgm.domain.AdminRole;
public interface AdminRoleMapper {

	public List<AdminRole> findByRoles(Long roles);
	
	public int insertAdminRolesBatch(List<AdminRole> adminRole);
	public List<AdminRole> findByAdmins(Long admins);
	
	public int deleteByAdmins(Long admins);
	
    public List<AdminRole> findListAll();
	
	public AdminRole findById(Long id);
	
	int insert(AdminRole parameter);
	
	int update(AdminRole parameter);
	
	int delete(Long id);
}