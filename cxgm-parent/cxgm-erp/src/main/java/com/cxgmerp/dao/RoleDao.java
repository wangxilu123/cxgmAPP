package com.cxgmerp.dao;

import java.util.List;

import com.cxgmerp.domain.Role;

public interface RoleDao {
    
    public List<Role> findAll();
    
    public List<Role> findByUserName(String username);
}