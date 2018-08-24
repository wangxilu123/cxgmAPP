package com.cxgm.service;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.List;

import javax.xml.rpc.ServiceException;
import javax.xml.rpc.holders.StringHolder;
import javax.xml.soap.SOAPException;

import org.apache.axis.client.Service;
import org.springframework.context.annotation.Primary;
import org.tempuri.GOODS_DATA;
import org.tempuri.IHsMisWebSrvbindingStub;
import org.tempuri.RESULT;

import com.cxgm.common.XmlUtil;

import sun.misc.BASE64Decoder;

/**
 * 海信业务对接
 * User: CQL
 *
 */
@Primary
@org.springframework.stereotype.Service
public class ThirdPartyHaixinStockService  {
	
	/**
	  *海信获取实时库存接口
	  */
	  public static GOODS_DATA findGoodDetail (String orgCode,String barCode) throws SOAPException, ServiceException, UnsupportedEncodingException, IOException {
		  
		 String endpoint = "http://221.219.243.5:8099/HsMisWebSrv.dll/soap/IHsMisWebSrv";
		   
		   URL url = new URL(endpoint);
		   Service service = new Service();
		   
		   IHsMisWebSrvbindingStub cleant =  new IHsMisWebSrvbindingStub(url,service);
		   
		   
		   String pIntfCode = "2005";
		   StringBuilder sb = new StringBuilder();  
	       sb.append("<IMPORTDATA>");  
	       sb.append("<OPERATION>1</OPERATION>");
	       sb.append("<DEVBRAND>*</DEVBRAND>");
	       sb.append("<DEVNO>*</DEVNO>");
	       sb.append("<ORGCODE>"+orgCode+"</ORGCODE>");
	       sb.append("<BARCODE>"+barCode+"</BARCODE>");
	       sb.append("<CERSIGN>14438E0B7D0777FF9914FE36515A716E4B54C3A5E4BE1C65AEF848374C4A2B05AFA142249205DD67381E8E467DAE07D0CC9499060D46CD62D0BC6BF9A5B4450CB4D8054BEBA397235F588a4024632f059a4bc424e0d8e6450db</CERSIGN>");
	       sb.append("</IMPORTDATA>");
	       
	       String  pInData = sb.toString();
		  
	       StringHolder pOutData = new StringHolder();
	       
	       cleant.IHiOpenedIntf(pIntfCode, pInData, pOutData);
	       
	       String result = cleant._getCall().getOutputParams().values().toString().replace("[", "").replaceAll("]", "");
	       
	       BASE64Decoder decoder = new BASE64Decoder();
	       
	       String str = new String(decoder.decodeBuffer(result), "GBK");
	       
	       String json = XmlUtil.xml2json(str).replaceAll("@", "");
	       
	       RESULT result1 = XmlUtil.parseJsonWithGson(json, RESULT.class);

		   GOODS_DATA goodDetail = result1.getRESULT_DATA().getGOODS_DATA();
			
	   return goodDetail;
	   }
	  
	  public static void main(String[] args) throws UnsupportedEncodingException, SOAPException, ServiceException, IOException{
		  String orgCode="0002";
		  String barCode="104400";
		  System.out.println(findGoodDetail(orgCode,barCode));
	  } 
}
	
	
