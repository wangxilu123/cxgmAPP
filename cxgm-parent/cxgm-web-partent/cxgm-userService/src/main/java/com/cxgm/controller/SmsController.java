package com.cxgm.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.cxgm.common.ResultDto;
import com.cxgm.common.ValidationCodeUtil;
import com.cxgm.domain.HttpSender;
import com.cxgm.domain.SmsSendRequest;
import com.cxgm.service.RedisService;

/**
 * 手机短信发送服务
 * @author Lucas
 */
@RestController
public class SmsController {

    public static final String charset = "utf-8";
    // 用户平台API账号(非登录账号,示例:N1234567)
    public static String account = "N2136353";
    // 用户平台API密码(非登录密码)
    public static String pswd = "Ps9ae8cf";

    private final static Logger logger = LoggerFactory.getLogger(SmsController.class);


    @Autowired
    private RedisService redisService;

    @Autowired
    private HttpServletRequest request;

    @GetMapping("/sms")
    public ResponseEntity sendMessage(@RequestParam(value = "phone") String phone){

        logger.info(" sendMessage---- "+phone);

        //请求地址请登录253云通讯自助通平台查看或者询问您的商务负责人获取
        String smsSingleRequestServerUrl = "http://zapi.253.com/msg/";

        logger.info(" sendMessage--smsSingleRequestServerUrl--"+smsSingleRequestServerUrl);

        String key = phone;

        //同一个用户一个小时只能发15次，24小时之内只能发30次。
        int countHours = redisService.get(key+"_counts_1_hours")==null?0:(Integer)redisService.get(key+"_counts_1_hours");

        int count24Hours = redisService.get(key+"_counts_24_hours")==null?0:(Integer)redisService.get(key+"_counts_24_hours");

        if(count24Hours<=30){
            redisService.set(key+"_counts_24_hours",count24Hours+1,new Long(24*60*60));
        }else {
            return ResultDto.INTERNAL_SERVER_ERROR("同一个用户24小时内只能发送30次信息。");
        }

        if (countHours <= 15){
            redisService.set(key+"_counts_1_hours",countHours+1,new Long(60*60));
        }else {
            return ResultDto.INTERNAL_SERVER_ERROR("同一个用户一小时只能发送15次信息。");
        }

//        redisService.set(key+"_counts_hours",1,new Long(60*60));

        //有效期（分钟）
        int interval = 3;
        //生成验证码，三分钟有效
        String validCode = ValidationCodeUtil.createCode(phone,interval).getVc().getCode();



        logger.info(" sendMessage--redis--key--"+key);
        //放入redis
        redisService.set(key,validCode,new Long(interval*60));



        // 短信内容
        String msg = "【小画智能】"+validCode+"(验证码),"+interval+"分钟内有效,请勿向任何人泄露。";


//        String code = (String) redisService.get(key);

//        logger.info(" sendMessage--"+msg+"____"+code);
        //手机号码
//        String phone = "15300068838";
        //状态报告
        String report= "true";

        SmsSendRequest smsSingleRequest = new SmsSendRequest(account, pswd, msg, phone,report);

        String requestJson = JSON.toJSONString(smsSingleRequest);

        logger.info("before request string is: " + requestJson);


//        String response = ChuangLanSmsUtil.sendSmsByPost(smsSingleRequestServerUrl, requestJson);

        String response ="";
        try {
            response = HttpSender.batchSend(smsSingleRequestServerUrl,account,pswd,phone,msg,true,"");
        }catch (Exception e){
            logger.info(e.getMessage());
            return ResultDto.BAD_REQUEST(e.getMessage());
        }



        logger.info("response after request result is :" + response);

//        SmsSendResponse smsSingleResponse = JSON.parseObject(response, SmsSendResponse.class);
//
//
//        logger.info("response  toString is :" + smsSingleResponse);

        return ResultDto.OK();

    }


}
