package com.cxgm.service;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;

import javax.xml.rpc.ServiceException;
import javax.xml.rpc.holders.StringHolder;
import javax.xml.soap.SOAPException;

import org.apache.axis.client.Service;
import org.springframework.context.annotation.Primary;
import org.tempuri.IHsMisWebSrvbindingStub;

import com.cxgm.common.XmlUtil;
import com.cxgm.domain.HaixinResult;

import sun.misc.BASE64Decoder;

/**
 * 海信业务对接
 * User: CQL
 *
 */
@Primary
@org.springframework.stereotype.Service
public class ThirdPartyHaixinUplodOrderService  {
	/**
	  *上传订单到海信接口
	  */
	  public static String checkOrder() throws SOAPException, ServiceException, UnsupportedEncodingException, IOException {
		  
		 String endpoint = "http://221.219.243.5:8099/HsMisWebSrv.dll/soap/IHsMisWebSrv";
		   
		   URL url = new URL(endpoint);
		   Service service = new Service();
		   
		   IHsMisWebSrvbindingStub cleant =  new IHsMisWebSrvbindingStub(url,service);
		   
		   
		   String pIntfCode = "2000";
		   StringBuilder sb = new StringBuilder();  
	       sb.append("<IMPORTDATA>");  
	       sb.append("<OPERATION>1</OPERATION>");
	       sb.append("<DEVBRAND>*</DEVBRAND>");
	       sb.append("<DEVNO>*</DEVNO>");
	       sb.append("<BILLTYPE>PSYS</BILLTYPE>");
	       sb.append("<BILLNO>20180606142124kJx34J</BILLNO>");
	       sb.append("<CERTYPE>01</CERTYPE>");
	       sb.append("<CERSIGN>112556653728E050D8350900446C390E93B6CBA90DD8D4135C09CCBC3C9C05FEE241FC1CE3DAF0EE1D5499060D46CD62D0B04CA0C4300819BC755e164aa45664fded1b54ae2fdbd818e</CERSIGN>");
	       sb.append("</IMPORTDATA>");
	       
	       String  pInData = sb.toString();
		  
	       StringHolder pOutData = new StringHolder();
	       
	       cleant.IHiOpenedIntf(pIntfCode, pInData, pOutData);
	       
	       String result = cleant._getCall().getOutputParams().values().toString().replace("[", "").replaceAll("]", "");
	       
	       BASE64Decoder decoder = new BASE64Decoder();
	       
	       String str = new String(decoder.decodeBuffer(result), "GBK");
	       
	       String json = XmlUtil.xml2json(str);
	       
	       HaixinResult haixinResult = XmlUtil.parseJsonWithGson(json, HaixinResult.class);
	       
	       String code= haixinResult.getRESULT_CODE();
	       
	   return code;
	   }
	  public static void main(String[] args) throws UnsupportedEncodingException, SOAPException, ServiceException, IOException{
		  System.out.println(checkOrder());
	  } 
}
	
	