package com.cxgmerp.dao;

import com.cxgmerp.domain.Admin;

public interface AdminDao {
	Admin findByUserName(String username);
}