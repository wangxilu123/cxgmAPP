package com.cxgmerp.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Component;

import com.cxgmerp.domain.PermissionRole;
@Component
public class PermissionRoleDao extends BaseDaoImpl<PermissionRole,Long>{

	@Override
	public String getNameSpace() {
		return "sql.PermissionRole";
	}
	
	public SqlSessionTemplate getSqlSessionTemplate(){
		return super.getSqlSessionTemplate();
	}
	
	public int insertPermissionRolesBatch(List<PermissionRole> permissionRole) {
		return this.getSqlSessionTemplate().insert(this.getNameSpace()+".insertPermissionRolesBatch", permissionRole);
	}
	
	public int deleteByRoles(Long roleId) {
		return this.getSqlSessionTemplate().delete(this.getNameSpace()+".deleteByRoles", roleId);
	}
	
	public List<PermissionRole> findByPermissions(Long permissions){
		return selectList(this.getNameSpace()+".findByPermissions",permissions);
	}
}