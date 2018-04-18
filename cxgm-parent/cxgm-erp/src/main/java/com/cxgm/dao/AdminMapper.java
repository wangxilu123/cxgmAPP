package com.cxgm.dao;

import java.util.List;

import com.cxgm.domain.Admin;

public interface AdminMapper{

	public Admin findByUserName(String username); 
	public Admin findByAdminName(String name); 
	
	public List<Admin> findListAll();
	
	public Admin findById(Long id);
	
	int insert(Admin parameter);
	
	int update(Admin parameter);
	
	int delete(Long id);
	
}