package com.cxgm.service;

import com.cxgm.domain.AppUser;
import com.cxgm.domain.RegisterEntity;


public interface UserService {

    /**
     * 根据用户ID查询用户信息
     * @param id
     * @return
     */
    AppUser selectById(int id);
    
    /**
     * 根据用户名查询用户信息
     * @param username
     * @return
     */
    AppUser selectByUsername(String username);
    
    /**
     * 根据电话号码查询用户信息
     * @param mobile
     * @return
     */
    AppUser selectByMobile(String mobile);
    
    /**
     * 用户注册
     * @param RegisterEntity
     * @return
     */
    Integer addUser(RegisterEntity register);
}
