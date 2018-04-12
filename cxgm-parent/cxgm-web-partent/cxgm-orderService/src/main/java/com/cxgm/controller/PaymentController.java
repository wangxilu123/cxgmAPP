package com.cxgm.controller;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cxgm.common.CheckToken;
import com.cxgm.common.CodeUtil;
import com.cxgm.common.DateUtil;
import com.cxgm.common.HttpUtil;
import com.cxgm.common.MyMd5Util;
import com.cxgm.common.ResultDto;
import com.cxgm.common.XmltoJsonUtil;
import com.cxgm.domain.AppUser;
import com.cxgm.domain.Order;
import com.cxgm.service.OrderService;

import io.swagger.annotations.ApiOperation;

/**
 * 支付管理
 * 
 * @author wangxilu
 * @version 1.0
 */
@Component
@Path("/payments")
public class PaymentController {
	
	@Autowired
    private OrderService orderService;

	// 微信统一下单接口路径
	private static final String UNIFORMORDER = "https://api.mch.weixin.qq.com/pay/unifiedorder";

	// 微信商户号：*****
	private static final String MCHID = "********";
	// 微信回调地址
	private static final String NOTIFYURL = "*********";
	// 微信交易类型
	private static final String TRADETYPE = "APP";
	// 微信APIKEY
	private static final String APIKEY = "************";

	/**
	 * 微信统一下单
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@ApiOperation(value = "用户下单接口",nickname = "用户下单接口")
	@RequestMapping(value = "/uniformorder", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public ResultDto uniformorder(HttpServletRequest request) throws UnsupportedEncodingException {
		
		ResultDto result = new ResultDto();// 返回数据结果集合
		
        AppUser appUser = new CheckToken().check(request.getHeader("token"));
    	
    	if(appUser!=null){
    		
    	String orderNumb=DateUtil.formatDateTime2()+CodeUtil.genCodes(6);
    	
    	BigDecimal orderAmount = new BigDecimal(request.getParameter("orderAmount") == null ? null : request.getParameter("orderAmount").trim());
    	
    	String remarks = request.getParameter("remarks") == null ? null : request.getParameter("remarks").trim();
    	
    	Integer storeId = Integer.parseInt(request.getParameter("storeId") == null ? null : request.getParameter("storeId").trim());
    	
    	Order order = new Order();	
    	order.setOrderAmount(orderAmount);
		order.setOrderNum(orderNumb);
		order.setOrderTime(new Date());
		order.setStatus("0");
    	order.setPayType("weixin");
    	order.setRemarks(remarks);
    	order.setStoreId(storeId);
    	order.setUserId(appUser.getId());
    		
    	orderService.addOrder(order);
		
		request.setCharacterEncoding("UTF-8");

		try {
			// APP ID
			String appid = request.getParameter("appid") == null ? null
					: request.getParameter("appid").trim().toUpperCase();
			// 用户访问令牌
			String accessToken = request.getParameter("accessToken") == null ? null
					: request.getParameter("accessToken").trim();
			// 订单编号
			String orderNum = orderNumb;// 订单编号
			// 消费金额
			String money = request.getParameter("money") == null ? null : request.getParameter("money").trim();// 消费金额
			// 消费主体
			String subject = request.getParameter("subject") == null ? null : request.getParameter("subject").trim();// 消费主体

			if (StringUtils.isEmpty(appid)) {
				result.setMsg("参数：appid 为空");
				result.setCode(-1);
				return result;
			}
			if (StringUtils.isEmpty(accessToken)) {
				result.setMsg("参数：accessToken 为空");
				result.setCode(-1);
				return result;
			}

			if (StringUtils.isEmpty(orderNum)) {
				result.setMsg("参数：orderNum 为空");
				result.setCode(-1);
				return result;
			}
			if (StringUtils.isEmpty(money)) {
				result.setMsg("参数：money 为空");
				result.setCode(-1);
				return result;
			}
			if (StringUtils.isEmpty(subject)) {
				result.setMsg("参数：subject 为空");
				result.setCode(-1);
				return result;
			}

			SortedMap<Object, Object> parame = new TreeMap<Object, Object>();
			parame.put("appid", "");
			parame.put("mch_id", MCHID);// 商家账号。
			String randomStr = getRandomString(18).toUpperCase();
			parame.put("nonce_str", randomStr);// 随机字符串
			parame.put("body", subject);// 商品描述
			parame.put("out_trade_no", orderNum);// 商户订单编号
			// 支付金额

			parame.put("total_fee", BigDecimal.valueOf(Long.parseLong(money)).multiply(new BigDecimal(100)).toString());// 消费金额
			String ip = getIpAddr(request);
			if (StringUtils.isEmpty(ip)) {
				parame.put("spbill_create_ip", "127.0.0.1");// 消费IP地址
			} else {
				parame.put("spbill_create_ip", ip);// 消费IP地址
			}
			parame.put("notify_url", NOTIFYURL);// 回调地址
			parame.put("trade_type", TRADETYPE);// 交易类型APP
			String sign = createSign(parame);
			parame.put("sign", sign);// 数字签证

			String xml = getRequestXML(parame);

			String content = HttpUtil.sendPost(UNIFORMORDER, xml);
			System.out.println(content);
			JSONObject jsonObject = JSONObject.parseObject(XmltoJsonUtil.xml2JSON(content));
			JSONObject result_xml = jsonObject.getJSONObject("xml");
			JSONArray result_code = result_xml.getJSONArray("result_code");
			String code = (String) result_code.get(0);

			List<String> data = new ArrayList<String>();
			if (code.equalsIgnoreCase("FAIL")) {
				result.setMsg("微信统一订单下单失败");
				result.setCode(-1);
				result.setData(data);
			} else if (code.equalsIgnoreCase("SUCCESS")) {
				JSONArray prepay_id = result_xml.getJSONArray("prepay_id");
				String prepayId = (String) prepay_id.get(0);
				data.add(prepayId);
				result.setMsg("微信统一订单下单成功");
				result.setCode(1);
				result.setData(data);
			}
			return result;

		} catch (Exception e) {
			result.setMsg(e.getMessage());
			result.setCode(-1);
			return result;
		}
    	}else{
    		result.setCode(403);
    		result.setMsg("token失效请重新登录！");
    		return result;
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
                
                
                /*Map<String,Object> map = new HashMap<String,Object>();  
                map.put("orderNum", (String)out_trade_no.get(0));  
                map.put("consumState", 1);  
                accountWalletService.updateAccountOrderState(map);*/  
                  
                response.getWriter().write(setXml("SUCCESS", "OK"));  
            }             
        }catch(Exception e){  
            result.setMsg(e.getMessage());  
            result.setCode(-1);  
            return;  
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

	// 创建md5 数字签证
	public static String createSign(SortedMap<Object, Object> parame) {
		StringBuffer buffer = new StringBuffer();
		Set set = parame.entrySet();
		Iterator iterator = set.iterator();
		while (iterator.hasNext()) {
			Map.Entry entry = (Map.Entry) iterator.next();
			String key = (String) entry.getKey();
			Object value = (String) entry.getValue();
			if (null != value && !"".equals(value) && !"sign".equals(key) && !"key".equals(key)) {
				buffer.append(key + "=" + value + "&");
			}
		}
		buffer.append("key=" + APIKEY);
		String sign = MyMd5Util.md5(buffer.toString()).toUpperCase();
		System.out.println("签名参数：" + sign);
		return sign;
	}
	
	//返回微信服务  
    public static String setXml(String return_code,String return_msg){    
           return "<xml><return_code><![CDATA["+return_code+"]]></return_code><return_msg><![CDATA["+return_msg+"]]></return_msg></xml>";    
   } 

}
