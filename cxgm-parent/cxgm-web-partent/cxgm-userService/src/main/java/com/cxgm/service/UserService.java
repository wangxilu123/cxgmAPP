package com.cxgm.service;

import com.cxgm.domain.AppUser;
import com.cxgm.domain.LoginEntity;


public interface UserService {

    /**
     * 用户注册
     * @param RegisterEntity
     * @return
     */
/*	ResultDto<Integer> addUser(RegisterEntity register);
*/	
    /**
     * 用户登录
     * @param LoginEntity
     * @return
     */
    AppUser login(LoginEntity user);
}
