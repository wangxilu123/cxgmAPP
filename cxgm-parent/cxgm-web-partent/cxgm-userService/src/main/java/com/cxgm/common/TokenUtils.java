package com.cxgm.common;

import com.cxgm.domain.LoginEntity;

public class TokenUtils
{

    public static String createToken(LoginEntity user)
    {
        long expires = System.currentTimeMillis() + 1000L * 60 * 60;

        StringBuilder tokenBuilder = new StringBuilder();
        tokenBuilder.append(user.getUserAccount());
        tokenBuilder.append(":");
        tokenBuilder.append(expires);

        String token = Md5.encode(tokenBuilder.toString());
        
        return token;
    }
}