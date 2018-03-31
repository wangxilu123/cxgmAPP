package com.cxgmerp.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.cxgmerp.domain.UserConstatnt;

public class SecurityUser implements UserDetails {

	public SecurityUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
		super();
		this.username = username;
		this.password = password;
		this.authorities = authorities;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -6730651007408577440L;
	
	private Integer id;
    private String roleIds;//角色
    private String idcard;//身份证
    private String realname;//真实姓名
    private String peopletype;//人员类型
    private String mobile;//手机号
    private String username;//用户名
    private String password;//密码
    private boolean enabled = true;//是否启用 ，默认true
    private String locked;//是否锁定
    
    private Collection<? extends GrantedAuthority> authorities;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return UserConstatnt.ACLUSER_ISLOCK_NO.equals(this.locked);
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(String roleIds) {
		this.roleIds = roleIds;
	}

	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public String getPeopletype() {
		return peopletype;
	}

	public void setPeopletype(String peopletype) {
		this.peopletype = peopletype;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getLocked() {
		return locked;
	}

	public void setLocked(String locked) {
		this.locked = locked;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
}
