package com.cxgm.service;

import com.cxgm.common.ResultDto;
import com.cxgm.domain.AppUser;
import com.cxgm.domain.LoginEntity;
import com.cxgm.domain.RegisterEntity;


public interface UserService {

    /**
     * 用户注册
     * @param RegisterEntity
     * @return
     */
	ResultDto<AppUser> addUser(RegisterEntity register);
	
    /**
     * 用户登录
     * @param LoginEntity
     * @return
     */
    ResultDto<AppUser> login(LoginEntity user);
}
