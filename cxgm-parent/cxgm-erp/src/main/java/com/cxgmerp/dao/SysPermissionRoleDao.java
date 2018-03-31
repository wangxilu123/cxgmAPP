package com.cxgmerp.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;

import com.cxgmerp.domain.SysPermissionRole;
import com.cxgmerp.domain.SysPermissionRoleExample;

public interface SysPermissionRoleDao {
    long countByExample(SysPermissionRoleExample example);

    int deleteByExample(SysPermissionRoleExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SysPermissionRole record);

    int insertSelective(SysPermissionRole record);

    List<SysPermissionRole> selectByExample(SysPermissionRoleExample example);

    SysPermissionRole selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SysPermissionRole record, @Param("example") SysPermissionRoleExample example);

    int updateByExample(@Param("record") SysPermissionRole record, @Param("example") SysPermissionRoleExample example);

    int updateByPrimaryKeySelective(SysPermissionRole record);

    int updateByPrimaryKey(SysPermissionRole record);
}