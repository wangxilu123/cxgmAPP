package com.cxgm.security.login;


import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class CustomUsernamePasswordAuthenticationToken extends UsernamePasswordAuthenticationToken{

	private static final long serialVersionUID = 6973619630633057575L;

	public CustomUsernamePasswordAuthenticationToken(String principal, String credentials) {
		super(principal, credentials);
	}

}
