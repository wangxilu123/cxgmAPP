package com.cxgm.security.login;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
public class CustomProvider extends DaoAuthenticationProvider {

	Logger log = LoggerFactory.getLogger(getClass());
	 @Override
	protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication)
	            throws AuthenticationException {
	        log.debug("=======additionalAuthenticationChecks======");
	        super.additionalAuthenticationChecks(userDetails, authentication);
	    }
	 
	 @Override
	 public Authentication authenticate(Authentication authentication) throws AuthenticationException {
	   return  super.authenticate(authentication);
	 }
	
}
