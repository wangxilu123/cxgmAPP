package com.cxgm.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.cxgm.common.SMSUtils;
import com.cxgm.domain.RedisKeyDto;
import com.cxgm.service.SmsVerificationCodeService;

@Service
public class SmsVerificationCodeServiceImpl implements SmsVerificationCodeService{

	@Autowired
	RedisServiceImpl redisService;

	
	@Transactional
	@Override
	public String sendMessage(String phoneName) {
		SMSUtils smsu = new SMSUtils();
		String returnCode="";
		try {
			String checkCode = smsu.sendSms(phoneName);
			RedisKeyDto cc = new RedisKeyDto();
			cc.setKeys(phoneName);
			cc.setValues(checkCode);
			RedisKeyDto exsitCC = redisService.redisGet(cc);
			if(exsitCC != null){
				redisService.delete(exsitCC);
			}
			redisService.addRedisData(cc, 60000);
			returnCode = checkCode;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnCode;
	}

	@Override
	public boolean checkIsCorrectCode(String phone, String checkcode) {
		RedisKeyDto cc = new RedisKeyDto();
		cc.setKeys(phone);
		cc.setValues(checkcode);
		RedisKeyDto cc2 = redisService.redisGet(cc);
		if(cc2!=null && cc2.getValues().equals(checkcode)){
			return true;
		}
		return false;
	}
	
	
	
}
