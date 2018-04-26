package com.cxgm.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;

import com.cxgm.domain.Permission;
import com.cxgm.domain.PermissionAndRole;

public interface PermissionMapper {

	SqlSessionTemplate getSqlSessionTemplate();

	List<PermissionAndRole> findAllPermissions();

	List<Permission> findByRole(Long roleid);

	List<Permission> findByName(String name);
	
	List<Permission> findListType0();
	
	List<Permission> findListType1();
	
	List<Permission> findListByIds(List<Integer> list);
	
	List<Permission> findByPid(Long pid);
	
    List<Permission> findListAll();
	
	Permission findById(Long id);
	
	int insert(Permission parameter);
	
	int update(Permission parameter);
	
	int delete(Long id);
}