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
import org.tempuri.IHsMisWebSrvbindingStub;

import com.cxgm.common.XmlUtil;
import com.cxgm.domain.GOODS_DATA;
import com.cxgm.domain.ThirdGood;

import sun.misc.BASE64Decoder;

/**
 * 海信业务对接
 * User: CQL
 *
 */
@Primary
@org.springframework.stereotype.Service
public class ThirdPartyHaixinGoodService  {
	
	/**
	  *获取门店商品信息接口
	  */
	  public static List<GOODS_DATA> findGoods () throws SOAPException, ServiceException, UnsupportedEncodingException, IOException {
		  
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
	       sb.append("<CERTYPE>01</CERTYPE>");
	       sb.append("<CERSIGN>14438E0B7D0777FF9914FE36515A716E4B54C3A5E4BE1C65AEF848374C4A2B05AFA142249205DD67381E8E467DAE07D0CC9499060D46CD62D0BC6BF9A5B4450CB4D8054BEBA397235F588a4024632f059a4bc424e0d8e6450db</CERSIGN>");
	       sb.append("</IMPORTDATA>");
	       
	       String  pInData = sb.toString();
		  
	       StringHolder pOutData = new StringHolder();
	       
	       cleant.IHiOpenedIntf(pIntfCode, pInData, pOutData);
	       
	       String result = cleant._getCall().getOutputParams().values().toString().replace("[", "").replaceAll("]", "");
	       
	       BASE64Decoder decoder = new BASE64Decoder();
	       
	       String str = new String(decoder.decodeBuffer(result), "GBK");
	       
	       String json = XmlUtil.xml2json(str).replaceAll("@", "");
	       
	       ThirdGood thirdOrgResult = XmlUtil.parseJsonWithGson(json, ThirdGood.class);

		   List<GOODS_DATA> goodList = thirdOrgResult.getRESULT_DATA().getGOODS_DATA();
			
	   return goodList;
	   }
	  
	  public static void main(String[] args) throws UnsupportedEncodingException, SOAPException, ServiceException, IOException{
		  System.out.println(findGoods());
	  } 
}
	
	
