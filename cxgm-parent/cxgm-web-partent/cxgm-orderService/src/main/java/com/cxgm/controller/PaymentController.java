package com.cxgm.controller;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.cxgm.common.GetWxOrderno;
import com.cxgm.common.RequestHandler;
import com.cxgm.common.ResultDto;
import com.cxgm.common.Sha1Util;
import com.cxgm.common.TenpayUtil;
import com.cxgm.common.XmltoJsonUtil;
import com.cxgm.domain.AppUser;
import com.cxgm.domain.Order;
import com.cxgm.service.OrderService;
import com.cxgm.service.impl.CheckToken;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * 支付管理
 * 
 * @author wangxilu
 * @version 1.0
 */
@Component
@Api(description = "支付相关接口")
@RequestMapping("/payments")
public class PaymentController {
	
	@Autowired
    private OrderService orderService;
	
	@Autowired
	private CheckToken checkToken;

	// 微信统一下单接口路径
	public static final String createOrderURL="https://api.mch.weixin.qq.com/pay/unifiedorder"; 

	public static final String appid = "wxd2f7d73babd9de68";//在微信开发平台登记的app应用 
	
	public static final String partner = "1505765861";//商户号
	// 微信回调地址
	private static final String NOTIFYURL = "http://47.104.226.173:41203/payments/notify";
	
	public static final String partnerkey ="9a72407e88e9786148906a3400f9a44a";//不是商户登录密码，是商户在微信平台设置的32位长度的api秘钥，  
	
	public static final String appsecret = "c15e1da71829aa86776f9b4fc40514d0"; 
	
	public static final String alipay_appId = "2018051860090884";//支付宝APP应用ID
	
	public static final String alipay_private_key = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDF77bqV1fffI53PAQVP5rM9sHojfFv72AIHHEHuc42nnFqWCJ7DTk1HW2r2T9JDJuSqSXIo1u6ai1WufijSNvqlikWArktk0K7SnLoOXDPEvXv+b1xFSGWR2xRPQTySIXSpz0N2jgD/V3z38PxG9LKOJvHg1+QwzarXqNfxo2VSsACyqNPBiJx+1bW0CfjcO2zYceVq50p0pRKNwFLrsPhfHhSn+q7I0ZlBjKzSjBNVPV2ubfYV9vpzhWCGcRKFKFdywiiGwz1BXY7xs4tc6dgZSRSSgEPqiOdwOUeLYC+jJNMVTqnZ/3BVJsQ/nPS7sdEOYgu1CW8sT7wAtbFvmeNAgMBAAECggEBAL1289Pkv0xBYe1oGVBtjbPcD0iXdh/7NiUNdHQ8utM1XsK4kmjPA4+AF9aDITy/QLtmBbDEDa2mrLdzVDiYKUmnarSS7sZdS2cToGIBeaX9NET8/QUdgQTkYR5yFiYEnXRCeQKlezD2edRSwLAuPdsrzJFxgyyCb/YhpKis2iGntccm3F3vIj6/hqQhxUppTIptGZKv8fv7JC1k8bcIXnVIYu6sLvUR5aUGkrGDf9WPHXmnvFQhErFQJNSdsjWdFRSSLnJV9E9krZEhi7Er21t9xBvmuI6dyxz89MDu9H50eq7kl6WbNvXO85GoqdC3o0gnbESwC2kRmpFa6W5ZigECgYEA/LULMEXzF8/rXaDloSB9io8iXbgP8wCsB8Ps5wYvYQC8Es3nIPhLqjzwfXodEZbQEHhheXbDzYU8o5/eQ0gzwrfd0vclvwSViPD7Xyt1foKCUG8ZnA4ujPmMDdE0FlBo/spMJ9pnKX12D9Idt6w+YL2/cmU/RHLdZgP0lzwF1W0CgYEAyIP4uuulM1ou5pIXvIez2DKnkr2GwG0Zr3iGNYPtFvvSCpF5WUV5l6fdERuB9qrV+hBcXYsIuXPRpwNoWWB949DgYinOR9yExJtas2h5Fsyk2W48kefxgG4km8Lx2ih/1dplohQlMgXImYKBOpYsCLL/AUCLl3P8m78Hl9vZJqECgYBWKzmsdT2mEfzUyUdjwNtuAs6aHGg72S/P2gwO3roLHqAC3XkBcgkC/JKYhqog1EcQRqweKdQsYVFDDgFQ+e7EZ8PY6vNRBjQjJODiG8SNpdzfpnKaFu/inDkYgQx8y2Bx+PMs57kjmadHT8NYXQBMJoeAb2u6YFyEK7ccVpg6pQKBgB4Dsy23rEjdjPuyjp8H6SHJo0/R0uPm8NtVPTGxh8HS/APc9r8oKSVMWUkARxn3RIRkAo92Cl5IOe+4ENygFvgNAilvfJNBLRaI8rC8SLPi+iIZ4DMu9vV0h0JRkgZH3arTQDdcsJymRPrFkDaG2ByNPqKfWMXY9M0TcT2ICR9BAoGAEKnFMBLh2vEcnNPQ1ehgdesoX5l8KZm/5dIge8Ghbo5ceFnBLkdyR78p84OxOMAoA7VcTvY9pxrHAWOl2Ov0GaoLAO9gpLX/etMGS1c62vdUefbySj0gNr8oqR6mm6YDBTWund999cdwIo9bsWFPnCh81LOAGn6VlTG7sMRf2bQ=";//支付宝私钥
	
	public static final String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAxe+26ldX33yOdzwEFT+azPbB6I3xb+9gCBxxB7nONp5xalgiew05NR1tq9k/SQybkqklyKNbumotVrn4o0jb6pYpFgK5LZNCu0py6DlwzxL17/m9cRUhlkdsUT0E8kiF0qc9Ddo4A/1d89/D8RvSyjibx4NfkMM2q16jX8aNlUrAAsqjTwYicftW1tAn43Dts2HHlaudKdKUSjcBS67D4Xx4Up/quyNGZQYys0owTVT1drm32Ffb6c4VghnEShShXcsIohsM9QV2O8bOLXOnYGUkUkoBD6ojncDlHi2AvoyTTFU6p2f9wVSbEP5z0u7HRDmILtQlvLE+8ALWxb5njQIDAQAB";//支付宝公钥

	/**
	 * 微信统一下单
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@ApiOperation(value = "微信支付接口",nickname = "微信支付接口")
	@RequestMapping(value = "/weixinPay", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	@ApiImplicitParam(name = "orderId", value = "订单ID", required = true, dataType = "Integer")
	@ResponseBody
    public void topay(HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		AppUser appUser = checkToken.check(request.getHeader("token"));
		
		JSONObject retMsgJson=new JSONObject();
		if(appUser!=null){
			  request.setCharacterEncoding("UTF-8");  
			    response.setCharacterEncoding("UTF-8");  
			    response.setContentType("text/html;charset=UTF-8");  
			    String orderId= request.getParameter("orderId");  
			    PrintWriter out = response.getWriter();  
			    String json=null;  
			       
			    Order order=orderService.findById(Integer.parseInt(orderId));//获取订单数据  
			    String money = "0.01";//获取订单金额  
			    //金额转化为分为单位  
			    float sessionmoney = Float.parseFloat(money);  
			    String finalmoney = String.format("%.2f", sessionmoney);  
			    finalmoney = finalmoney.replace(".", "");  
			            String currTime = TenpayUtil.getCurrTime();  
			            //8位日期  
			            String strTime = currTime.substring(8, currTime.length());  
			            //四位随机数  
			            String strRandom = TenpayUtil.buildRandom(4) + "";  
			            //10位序列号,可以自行调整。  
			            String strReq = strTime + strRandom;          
			            //商户号  
			            String mch_id = partner;  
			            //子商户号  非必输  
			            //String sub_mch_id="";  
			            //设备号   非必输  
			            String device_info="";  
			            //随机数   
			            String nonce_str = strReq;  
			            String body = "test";  
			            //商户订单号  
			            String out_trade_no = order.getOrderNum();//订单编号加时间戳  
			            int intMoney = Integer.parseInt(finalmoney);              
			            //总金额以分为单位，不带小数点  
			            String total_fee = String.valueOf(intMoney);  
			            //订单生成的机器 IP  
			            String spbill_create_ip =request.getRemoteAddr();  
			            String notify_url =NOTIFYURL;//微信异步通知地址           
			            String trade_type = "APP";//app支付必须填写为APP  
			                        //对以下字段进行签名  
			            SortedMap<String, String> packageParams = new TreeMap<String, String>();  
			            packageParams.put("appid", appid);    
			            packageParams.put("body", body);    
			            packageParams.put("mch_id", mch_id);      
			            packageParams.put("nonce_str", nonce_str);    
			            packageParams.put("notify_url", notify_url);    
			            packageParams.put("out_trade_no", out_trade_no);      
			            packageParams.put("spbill_create_ip", spbill_create_ip);   
			            packageParams.put("total_fee", total_fee);  
			            packageParams.put("trade_type", trade_type);    
			            RequestHandler reqHandler = new RequestHandler(request, response);  
			            reqHandler.init(appid, appsecret, partnerkey);        
			            String sign = reqHandler.createSign(packageParams);//获取签名  
			            String xml="<xml>"+  
			                    "<appid>"+appid+"</appid>"+  
			                    "<body><![CDATA["+body+"]]></body>"+  
			                    "<mch_id>"+mch_id+"</mch_id>"+  
			                    "<nonce_str>"+nonce_str+"</nonce_str>"+  
			                    "<notify_url>"+notify_url+"</notify_url>"+  
			                    "<out_trade_no>"+out_trade_no+"</out_trade_no>"+  
			                    "<spbill_create_ip>"+spbill_create_ip+"</spbill_create_ip>"+  
			                    "<total_fee>"+total_fee+"</total_fee>"+  
			                    "<trade_type>"+trade_type+"</trade_type>"+  
			                    "<sign>"+sign+"</sign>"+  
			                    "</xml>";  
			            String allParameters = "";  
			            try {  
			                allParameters =  reqHandler.genPackage(packageParams);  
			             } catch (Exception e) {  
			                // TODO Auto-generated catch block  
			                e.printStackTrace();  
			            }  
			            String prepay_id="";  
			            try {  
			                prepay_id = new GetWxOrderno().getPayNo(createOrderURL, xml);  
			                if(prepay_id.equals("")){  
			                retMsgJson.put("msg", "error");  
			                json=retMsgJson.toString();                   
			                out.write(json);  
			                out.close();  
			                return;  
			                 }  
			            } catch (Exception e1) {  
			                // TODO Auto-generated catch block  
			                e1.printStackTrace();  
			             }  
			                        //获取到prepayid后对以下字段进行签名最终发送给app  
			                       SortedMap<String, String> finalpackage = new TreeMap<String, String>();  
			            String timestamp = Sha1Util.getTimeStamp();  
			            finalpackage.put("appid", appid);    
			            finalpackage.put("timestamp", timestamp);    
			            finalpackage.put("noncestr", nonce_str);    
			            finalpackage.put("partnerid", partner);   
			            finalpackage.put("package", "Sign=WXPay");                
			            finalpackage.put("prepayid", prepay_id);    
			            String finalsign = reqHandler.createSign(finalpackage);  
			                    retMsgJson.put("msg", "ok");  
			                    retMsgJson.put("appid", appid);  
			                    retMsgJson.put("timestamp", timestamp);  
			                    retMsgJson.put("noncestr", nonce_str);  
			                    retMsgJson.put("partnerid", partner);  
			                    retMsgJson.put("prepayid", prepay_id);  
			                    retMsgJson.put("package", "Sign=WXPay");  
			                    retMsgJson.put("sign", finalsign);  
			                    json=retMsgJson.toString();                   
			                    out.write(json);  
			                    out.close();  
		}else{
			 retMsgJson.put("msg", 403);  
		     retMsgJson.put("body", "token失效请重新登录！"); 
		}
	}
	
	/**  
     * 微信订单回调接口  
     *   
     * @return  
     */  
	@ApiOperation(value = "微信回调接口",nickname = "微信回调接口") 
    @RequestMapping(value = "/notify", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")  
    @ResponseBody  
    public void notify(HttpServletRequest request,HttpServletResponse response){  
    	ResultDto result = new ResultDto();// 返回数据结果集合  
        try{  
            request.setCharacterEncoding("UTF-8");    
            response.setCharacterEncoding("UTF-8");    
            response.setContentType("text/html;charset=UTF-8");    
            response.setHeader("Access-Control-Allow-Origin", "*");     
            InputStream in=request.getInputStream();    
            ByteArrayOutputStream out=new ByteArrayOutputStream();    
            byte[] buffer =new byte[1024];    
            int len=0;    
            while((len=in.read(buffer))!=-1){    
                out.write(buffer, 0, len);    
            }    
            out.close();    
            in.close();    
            String content=new String(out.toByteArray(),"utf-8");//xml数据    
              
            JSONObject jsonObject = JSONObject.parseObject(XmltoJsonUtil.xml2JSON(content)) ;    
            JSONObject result_xml = jsonObject.getJSONObject("xml");  
            JSONArray result_code =  result_xml.getJSONArray("result_code");  
            String code = (String)result_code.get(0);  
              
            if(code.equalsIgnoreCase("FAIL")){  
                result.setMsg("微信统一订单下单失败");  
                result.setCode(-1);               
              
                response.getWriter().write(setXml("SUCCESS", "OK"));  
                  
                  
            }else if(code.equalsIgnoreCase("SUCCESS")){  
                result.setMsg("微信统一订单下单成功");  
                result.setCode(1);    
                  
                JSONArray out_trade_no = result_xml.getJSONArray("out_trade_no");//订单编号  
                
                //修改订单状态。。。。。。
                //根据订单号查询订单信息
                
                Order order = orderService.findById((Integer)out_trade_no.get(0));
                
                order.setStatus("1");
                orderService.updateOrder(order);
                  
                response.getWriter().write(setXml("SUCCESS", "OK"));  
            }             
        }catch(Exception e){  
            result.setMsg(e.getMessage());  
            result.setCode(-1);  
            return;  
        }  
          
    }
	
	
	/**
	 * 修改订单状态
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@ApiOperation(value = "修改订单状态接口",nickname = "修改订单状态接口")
	@RequestMapping(value = "/updateStatus", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	@ApiImplicitParams({
	@ApiImplicitParam(name = "orderId", value = "订单ID", required = true, dataType = "Integer"),
	@ApiImplicitParam(name = "payType", value = "支付方式,微信wx，支付宝zfb", required = true, dataType = "String")
	})
	@ResponseBody
    public void updateStatus(HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		AppUser appUser = checkToken.check(request.getHeader("token"));
		
		JSONObject retMsgJson=new JSONObject();
		if(appUser!=null){
			  request.setCharacterEncoding("UTF-8");  
			    response.setCharacterEncoding("UTF-8");  
			    response.setContentType("text/html;charset=UTF-8");  
			    String orderId= request.getParameter("orderId");  
			    String payType= request.getParameter("payType");
			    
                //根据订单号查询订单信息
                
                Order order = orderService.findById(Integer.parseInt(orderId));
                
                order.setStatus("1");
                order.setPayType(payType);
                orderService.updateOrder(order);
                
                PrintWriter out = response.getWriter();
                
                retMsgJson.put("msg", "ok");  
                out.write(retMsgJson.toString());
			   
		}else{
			 retMsgJson.put("msg", 403);  
		     retMsgJson.put("body", "token失效请重新登录！"); 
		}
	}
	
	

	// 返回用IP地址
	public String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader(" x-forwarded-for ");
		if (ip == null || ip.length() == 0 || " unknown ".equalsIgnoreCase(ip)) {
			ip = request.getHeader(" Proxy-Client-IP ");
		}
		if (ip == null || ip.length() == 0 || " unknown ".equalsIgnoreCase(ip)) {
			ip = request.getHeader(" WL-Proxy-Client-IP ");
		}
		if (ip == null || ip.length() == 0 || " unknown ".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	// 随机字符串生成
	public static String getRandomString(int length) { // length表示生成字符串的长度
		String base = "abcdefghijklmnopqrstuvwxyz0123456789";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}

	// 拼接xml 请求路径
	public static String getRequestXML(SortedMap<Object, Object> parame) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("<xml>");
		Set set = parame.entrySet();
		Iterator iterator = set.iterator();
		while (iterator.hasNext()) {
			Map.Entry entry = (Map.Entry) iterator.next();
			String key = (String) entry.getKey();
			String value = (String) entry.getValue();
			// 过滤相关字段sign
			if ("sign".equalsIgnoreCase(key)) {
				buffer.append("<" + key + ">" + "<![CDATA[" + value + "]]>" + "</" + key + ">");
			} else {
				buffer.append("<" + key + ">" + value + "</" + key + ">");
			}
		}
		buffer.append("</xml>");
		return buffer.toString();
	}

	//返回微信服务  
    public static String setXml(String return_code,String return_msg){    
           return "<xml><return_code><![CDATA["+return_code+"]]></return_code><return_msg><![CDATA["+return_msg+"]]></return_msg></xml>";    
   } 
    
    
    /**
	 * 支付宝统一下单
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@ApiOperation(value = "支付宝支付接口",nickname = "支付宝支付接口")
	@RequestMapping(value = "/aliPay", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	@ApiImplicitParam(name = "orderId", value = "订单ID", required = true, dataType = "Integer")
	@ResponseBody
    public String aliPay(HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		AppUser appUser = checkToken.check(request.getHeader("token"));
		
		JSONObject retMsgJson=new JSONObject();
		if(appUser!=null){
			    request.setCharacterEncoding("UTF-8");  
			    response.setCharacterEncoding("UTF-8");  
			    response.setContentType("text/html;charset=UTF-8");  
			    String orderId= request.getParameter("orderId");  
			       
			    Order order=orderService.findById(!"".equals(orderId)?Integer.parseInt(orderId):null);//获取订单数据  
			    String money = "0.01";//获取订单金额  
			    //金额转化为分为单位  
			    float sessionmoney = Float.parseFloat(money);  
			    String finalmoney = String.format("%.2f", sessionmoney);  
			    finalmoney = finalmoney.replace(".", "");
			    
			    //实例化客户端
		        AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do", alipay_appId, alipay_private_key , "json", "UTF-8", alipay_public_key, "RSA2");
			    
		        //实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
		        AlipayTradeAppPayRequest aliRequest = new AlipayTradeAppPayRequest();
		        //SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
		        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
		        model.setPassbackParams("测试数据");;  //描述信息  添加附加数据
		        model.setSubject("魅格"); //商品标题
		        model.setOutTradeNo(order!=null?order.getOrderNum():""); //商家订单编号
		        model.setTimeoutExpress("30m"); //超时关闭该订单时间
		        model.setTotalAmount("0.01");  //订单总金额
		        model.setProductCode("QUICK_MSECURITY_PAY"); //销售产品码，商家和支付宝签约的产品码，为固定值QUICK_MSECURITY_PAY
		        aliRequest.setBizModel(model);
		        aliRequest.setNotifyUrl("https://api.xx.com/receive_notify.htm");  //回调地址
		        String orderStr = "";
		        try {
		                //这里和普通的接口调用不同，使用的是sdkExecute
		                AlipayTradeAppPayResponse aliResponse = alipayClient.sdkExecute(aliRequest);
		                orderStr = aliResponse.getBody();
		                System.out.println(orderStr);//就是orderString 可以直接给客户端请求，无需再做处理。
		            } catch (AlipayApiException e) {
		                e.printStackTrace();
		        }
		        return orderStr;
			            
		}else{
			 retMsgJson.put("msg", 403);  
		     retMsgJson.put("body", "token失效请重新登录！"); 
		     return null;
		}
	}

}
