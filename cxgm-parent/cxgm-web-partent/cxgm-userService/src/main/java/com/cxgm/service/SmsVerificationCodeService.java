package com.cxgm.service;

import org.springframework.stereotype.Service;

@Service
public interface SmsVerificationCodeService {

	/***
     * 发送验证码
     * @param phoneName
     * @return
     */
    String sendMessage(String phoneName);

    /**
     * 判断验证码是否正确
     * @param phone
     * @param code
     * @return
     */
    boolean checkIsCorrectCode(String phone,String code);
	
}
