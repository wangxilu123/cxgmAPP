package com.cxgm.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;

/**
 * @Description:短信验证工具类
 */
public class SMSUtils {

	//请求地址    
    private static String URL = SMSConstants.URL;    
    //TOP分配给应用的AppKey    
    private static String APP_KEY = SMSConstants.APP_KEY;    
    //短信签名AppKey对应的secret值  
    private static String SECRET = SMSConstants.SECRET;    
    //短信类型，传入值请填写normal     
    private static String SMS_TYPE = SMSConstants.SMS_TYPE;    
    //阿里大于账户配置的短信签名    
    private static String SMS_SIGN = SMSConstants.SMS_SIGN;    
    //阿里大于账户配置的短信模板ID    
    private static String SMS_TEMPLATE_CODE = SMSConstants.SMS_TEMPLATE_CODE;   
      
    /** 
     * @param phone 必填参数，手机号码 
     * @return 
     * @throws Exception 
     */  
    public String sendMsgCode(String phone) throws Exception {    
         //随机生成6位数字作为验证码  
        int code = getCode(1,999999);    
        //获得第三方阿里云短信通知接口  
        TaobaoClient client = new DefaultTaobaoClient(URL, APP_KEY, SECRET);  
        //获得短信通知请求头  
        AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();  
        //短信通知类型  
        req.setSmsType(SMS_TYPE);  
        //短信通知签名  
        req.setSmsFreeSignName(SMS_SIGN);  
        //短信接收号码:传入号码为11位手机号码不能加0或+86,最多传入200个号码,多个号码以逗号分隔  
        req.setRecNum(phone);  
        //短信通知参数json格式  
        SmsParam smsParamVo = new SmsParam();  
        smsParamVo.setCode(String.valueOf(code));   
        String smsParam = JSONObject.toJSONString(smsParamVo);  
        System.out.println("短信通知参数smsParam:"+smsParam);  
        //短信模板变量，传参规则{"key":"value"}，key的名字须和申请模板中的变量名一致，多个变量之间以逗号隔开    
        req.setSmsParamString(smsParam);  
        //短信模板ID    
        req.setSmsTemplateCode(SMS_TEMPLATE_CODE);    
        AlibabaAliqinFcSmsNumSendResponse rsp = client.execute(req);  
        JSONObject json = JSON.parseObject(rsp.getBody());    
        String jsonStr = json.getString("alibaba_aliqin_fc_sms_num_send_response");    
        if (jsonStr!=null&&!jsonStr.isEmpty() ) {    
            json = JSON.parseObject(jsonStr);    
            String result = json.getString("result");    
            if (result!=null && !result.isEmpty()) {    
                json = JSON.parseObject(result);    
                System.out.println("json:"+json);  
                String errorCode = json.getString("err_code");    
                if ("0".equals(errorCode)) {  
                    //发送成功    
                    return String.valueOf(code);  
                } else {  
                    //发送失败  
                    return null;  
                }    
            }    
        }  
        //发送失败  
        return null;  
    }  
      
        
    /** 
     * 随机生成6位数字作为验证码 
     * @return 
     */  
    public static int getCode(int min, int max){  
    	int randNum = min + (int)(Math.random() * ((max - min) + 1));
        return randNum;
    }  
}