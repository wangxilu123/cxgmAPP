package com.cxgm.common;

import java.util.SortedMap;
import java.util.TreeMap;

import com.cxgm.domain.Order;



public class WeixinReturnMonery {
	
	// 微信退款业务逻辑
		public static void wechatRefund(Order order) {
			String currTime = TenpayUtil.getCurrTime();  
	        //8位日期  
			String strTime = currTime.substring(8, currTime.length());  
	       //四位随机数  
	       String strRandom = TenpayUtil.buildRandom(4) + "";  
	       //10位序列号,可以自行调整。  
	       String strReq = strTime + strRandom;
			
			String out_refund_no = order.getOrderNum();// 退款单号
			String out_trade_no = order.getOrderNum();// 订单号
			String total_fee = "0.01";// 总金额
			String refund_fee = "0.01";// 退款金额
			String nonce_str = strReq;// 随机字符串
			String appid = "wxd2f7d73babd9de68";
			String appsecret = "c15e1da71829aa86776f9b4fc40514d0";
			String partnerkey = "9a72407e88e9786148906a3400f9a44a";// 商户平台上的那个KEY
			String mch_id = "1505765861";
			
			
			SortedMap<String, String> packageParams = new TreeMap<String, String>();
			packageParams.put("appid", appid);
			packageParams.put("mch_id", mch_id);
			packageParams.put("nonce_str", nonce_str);
			packageParams.put("out_trade_no", out_refund_no);
			packageParams.put("out_refund_no", out_trade_no);
			packageParams.put("total_fee", total_fee);
			packageParams.put("refund_fee", refund_fee);

			RequestHandler reqHandler = new RequestHandler(null, null);
			reqHandler.init(appid, appsecret, partnerkey);

			String sign = reqHandler.createSign(packageParams);
			String xml = "<xml>" + "<appid>" + appid + "</appid>" + "<mch_id>" + mch_id + "</mch_id>" + "<nonce_str>"
					+ nonce_str + "</nonce_str>" + "<sign><![CDATA[" + sign + "]]></sign>" + "<out_trade_no>" + out_trade_no
					+ "</out_trade_no>" + "<out_refund_no>" + out_refund_no + "</out_refund_no>" + "<total_fee>" + total_fee
					+ "</total_fee>" + "<refund_fee>" + refund_fee + "</refund_fee>" + "</xml>";
			String createOrderURL = "https://api.mch.weixin.qq.com/secapi/pay/refund";
			try {
				String s = ClientCustomSSL.doRefund(createOrderURL, xml);
				System.out.println(s);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

}
