package com.cxgm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cxgm.common.ResultDto;
import com.cxgm.service.impl.SmsVerificationCodeServiceImpl;


/**
 * 手机短信发送服务
 * @author wangxilu
 */
@RestController
public class SmsController {

	@Autowired
	private SmsVerificationCodeServiceImpl smsVerificationCodeService;
	
	@RequestMapping(value = "/sendSMS", method = RequestMethod.POST)
    @ResponseBody
	public ResultDto<String> sendSMSMessage(@RequestParam(value = "phone", required = false) String phone){
    		
		String msgCode = smsVerificationCodeService.sendMessage(phone);
    		
		return new ResultDto<>(200,"发送成功！",msgCode);
	}
	
	@RequestMapping(value = "/validaSMS", method = RequestMethod.POST)
    @ResponseBody
	public ResultDto<Boolean> validaSMSMessage(@RequestParam(value = "phone", required = false) String phone,
			@RequestParam(value = "checkcode", required = false) String checkcode){
		
		Boolean result = smsVerificationCodeService.checkIsCorrectCode(phone, checkcode);
		
		return new ResultDto<>(200,"验证成功！",result);
	}

}
