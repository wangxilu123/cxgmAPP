package com.cxgm.common;

public class SMSConstants {

	 //公共参数：正式环境请求地址http格式  
    public static String URL="http://gw.api.taobao.com/router/rest";  
    //公共参数：正式环境请求地址https格式    
    //public static String URL2 = "https://eco.taobao.com/router/rest";   
    //TOP分配给应用的AppKey,即阿里大于账户创建的应用  
    public static String APP_KEY="24329772";
    
    //公共参数：签名的摘要算法，可选值为：hmac，md5。  
    public static String SIGN_METHOD_MD5="md5";  
    //公共参数：签名的摘要算法，可选值为：hmac，md5。  
    public static String SIGN_METHOD_HMAC="hmac";  
    //请求参数：短信签名，传入的短信签名必须是在阿里大于“管理中心-短信签名管理”中的可用签名  
    public static String SMS_SIGN="思贝思";  
    //请求参数：公共回传参数  
    public static String EXTEND="123456";  
    //请求参数：短信类型，传入值请填写normal   
    public static String SMS_TYPE="normal";  
    //请求参数：短信模板ID，传入的模板必须是在阿里大于“管理中心-短信模板管理”中的可用模板。示例：SMS_585014
    //验证码模板
    public static String SMS_TEMPLATE_CODE="SMS_121240007";  
    
    //短信通知模板
    public static String SMS_TEMPLATE_CODE_ACK="SMS_121240007"; 
    
    //请求参数：签名的App Secret值  
    public static String SECRET = "d3f593c2f87022e6c3701ad3ad587487";    
    //返回信息编码为utf-8  
    public static String CHARSET_UTF8="utf-8";  
    //短信通知模板类型：1验证码类2通知类  
    //千匠用户注册发送短信验证码  
    public static final String SMS_MODEL_TYPE1="1";  
    //千匠用户下发订单发送订单通知  
    public static final String SMS_MODEL_TYPE2="2";  
    //千匠用户商品价格变动通知1  
    public static final String SMS_MODEL_TYPE21="21";  
    //千匠用户商品价格变动通知2  
    public static final String SMS_MODEL_TYPE211="211";  
    //千匠用户商品到货通知1  
    public static final String SMS_MODEL_TYPE212="212";  
    //千匠用户商品到货通知2  
    public static final String SMS_MODEL_TYPE213="213";     
}
