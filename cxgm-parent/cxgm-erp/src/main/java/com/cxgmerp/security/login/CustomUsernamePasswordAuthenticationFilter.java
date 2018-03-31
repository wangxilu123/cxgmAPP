package com.cxgmerp.security.login;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class CustomUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		String username = obtainUsername(request);
		String password = obtainPassword(request);

		String token = request.getParameter("token");// 自定义参数
		UsernamePasswordAuthenticationToken authRequest = new CustomUsernamePasswordAuthenticationToken(username,
				password);

		return this.getAuthenticationManager().authenticate(authRequest);
	}

}
