package com.cxgm.service.impl;

import org.springframework.stereotype.Service;

import com.cxgm.domain.AppUser;
import com.cxgm.domain.RegisterEntity;
import com.cxgm.service.UserService;

@Service
public class UserServiceImpl implements UserService{

	@Override
	public AppUser selectById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AppUser selectByUsername(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AppUser selectByMobile(String mobile) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer addUser(RegisterEntity register) {
		
		AppUser user = new AppUser();
		
		user.setMobile(register.getMobile());
		user.setUserName("");
		user.setUserPwd(register.getPassword());
		return null;
	}

}
