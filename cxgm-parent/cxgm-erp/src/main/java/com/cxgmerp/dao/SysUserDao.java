package com.cxgmerp.dao;

import com.cxgmerp.domain.SysUser;

public interface SysUserDao {
	public SysUser findByUserName(String username);
}