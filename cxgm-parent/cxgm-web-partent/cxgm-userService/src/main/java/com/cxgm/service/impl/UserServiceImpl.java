package com.cxgm.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.cxgm.common.ResultDto;
import com.cxgm.common.TokenUtils;
import com.cxgm.dao.UserLoginMapper;
import com.cxgm.dao.UserMapper;
import com.cxgm.domain.AppUser;
import com.cxgm.domain.AppUserExample;
import com.cxgm.domain.LoginEntity;
import com.cxgm.domain.RegisterEntity;
import com.cxgm.domain.UserLogin;
import com.cxgm.domain.UserLoginExample;
import com.cxgm.service.RedisService;
import com.cxgm.service.UserService;

@Primary
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private UserLoginMapper userLoginMapper;

	@Autowired
	private RedisService redisService;

	@Override
	public ResultDto<Integer> addUser(RegisterEntity register) {

		AppUserExample example = new AppUserExample();

		example.createCriteria().andUserNameEqualTo(register.getUserAccount());

		List<AppUser> userList = userMapper.selectByExample(example);
		
		if(userList.size()!=0){
			return new ResultDto<>(203,"该用户已存在，请重试！");
		}else{
			// 校验短信验证码
			String code = (String) redisService.get(register.getUserAccount());

			if ("".equals(code) == false && code.equals(register.getMobileValidCode())) {
				return new ResultDto<>(204,"验证码有误请重新输入！");
			} else {
				// 删除键值对
				redisService.remove(register.getUserAccount());
				
				AppUser user = new AppUser();

				user.setMobile(register.getUserAccount());
				user.setUserName("");
				user.setUserPwd(register.getPassword());
				
				userMapper.insert(user);
				
				return  new ResultDto<>(200,"注册成功！",user.getId());
			}
		}
	}

	@Override
	public ResultDto<AppUser> login(LoginEntity loginUser) {

		AppUserExample example = new AppUserExample();

		example.createCriteria().andUserNameEqualTo(loginUser.getUserAccount());

		List<AppUser> userList = userMapper.selectByExample(example);

		if (userList.size() != 0) {

			AppUser appUser = userList.get(0);
			if (appUser.getUserPwd().equals(loginUser.getPassword())) {
				//根据用户名查询用户登录信息
				
				UserLoginExample example1 = new UserLoginExample();
				
				example1.createCriteria().andUserAccountEqualTo(loginUser.getUserAccount());
				
				List<UserLogin> userLogins = userLoginMapper.selectByExample(example1);
				
				UserLogin userLogin = userLogins.get(0);
				
				String newToken = TokenUtils.createToken(loginUser);
				if (userLogins.size() != 0){
					userLogin.setToken(newToken);
					userLogin.setLastLogin(new Date());
					//token保鲜
					userLoginMapper.updateByPrimaryKey(userLogin);
				}else{
					userLogin = new UserLogin();
					userLogin.setToken(newToken);
					userLogin.setUserAccount(loginUser.getUserAccount());
					userLogin.setLastLogin(new Date());
					
					userLoginMapper.insert(userLogin);
				}

				return new ResultDto<>(200, "登录成功！", appUser);
			} else {
				return new ResultDto<>(202, "登录密码错误！", appUser);
			}

		} else {
			return new ResultDto<>(201, "用户名错误！", null);
		}
	}
}
