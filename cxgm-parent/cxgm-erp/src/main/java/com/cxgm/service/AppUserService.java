package com.cxgm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cxgm.dao.UserMapper;
import com.cxgm.domain.AppUser;
import com.cxgm.domain.AppUserExample;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
public class AppUserService {

    @Autowired
	private UserMapper userMapper;
    
	public PageInfo<AppUser> findUserByPage(Integer pageNum,Integer pageSize) {
		
		PageHelper.startPage(pageNum, pageSize);
		
		AppUserExample example = new AppUserExample();
		
		example.setOrderByClause("id desc");
		
		List<AppUser> userList = userMapper.selectByExample(example);
		
		PageInfo<AppUser> page = new PageInfo<>(userList);
		return page;
	}
}
