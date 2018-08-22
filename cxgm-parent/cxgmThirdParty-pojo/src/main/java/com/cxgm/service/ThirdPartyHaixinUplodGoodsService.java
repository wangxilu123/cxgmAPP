package com.cxgm.service;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.rpc.ServiceException;
import javax.xml.rpc.holders.StringHolder;
import javax.xml.soap.SOAPException;

import org.apache.axis.client.Service;
import org.springframework.context.annotation.Primary;
import org.tempuri.IHsMisWebSrvbindingStub;

import com.cxgm.common.XmlUtil;
import com.cxgm.domain.HaixinResult;
import com.cxgm.domain.Order;
import com.cxgm.domain.OrderProductTransfer;

import sun.misc.BASE64Decoder;

/**
 * 海信业务对接
 * User: CQL
 *
 */
@Primary
@org.springframework.stereotype.Service
public class ThirdPartyHaixinUplodGoodsService  {
	/**
	  *上传订单商品详情到海信接口
	  */
	  public static String upload(Order order) throws SOAPException, ServiceException, UnsupportedEncodingException, IOException {
		  
		 String endpoint = "http://221.219.243.5:8099/HsMisWebSrv.dll/soap/IHsMisWebSrv";
		   
		   URL url = new URL(endpoint);
		   Service service = new Service();
		   
		   IHsMisWebSrvbindingStub cleant =  new IHsMisWebSrvbindingStub(url,service);
		   
		   SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
		   
		   SimpleDateFormat format1=new SimpleDateFormat("HH:mm:ss");
		   
		   String date = format.format(new Date());
		   String time = format1.format(new Date());
		   
		   String pIntfCode = "2018";
		   StringBuilder sb = new StringBuilder();  
	       sb.append("<IMPORTDATA>");  
	       sb.append("<OPERATION>1</OPERATION>");
	       sb.append("<BILLHEAD>");
	       sb.append("<ISEC>0</ISEC>");
	       sb.append("<ISAUTORZ>1</ISAUTORZ>");
	       sb.append("<BILLNO>"+order.getOrderNum()+"</BILLNO>");
	       sb.append("<ORGCODE>"+order.getHaixinShopCode()+"</ORGCODE>");
	       sb.append("<CKCODE>01</CKCODE>");
	       sb.append("<PFCUSTCODE>99998</PFCUSTCODE>");
	       sb.append("<ISKFP>0</ISKFP>");
	       sb.append("<JSCODE>00</JSCODE>");
	       sb.append("<DATE>"+date+"</DATE>");
	       sb.append("<TIME>"+time+"</TIME>");
	       sb.append("<STAFFCODE>9998</STAFFCODE>");
	       sb.append("<FHMODE>1</FHMODE>");
	       sb.append("<PRICETYPE>1</PRICETYPE>");
	       sb.append("</BILLHEAD>");
	       sb.append("<BILLBODY>");
	       int i=1;
	       for(OrderProductTransfer product : order.getProductDetails()){
	    	   sb.append("<PLUDATA>");
		       sb.append("<BILLNO>"+order.getOrderNum()+"</BILLNO>");
		       sb.append("<SERIALNO>"+i+"</SERIALNO>");
		       sb.append("<PLUCODE>"+product.getProductCode()+"</PLUCODE>");
		       sb.append("<PLUNAME>"+product.getProductName()+"</PLUNAME>");
		       sb.append("<BARCODE>"+product.getBarCode()+"</BARCODE>");//未加
		       sb.append("<PFCOUNT>"+product.getHaixinNum()+"</PFCOUNT>");
		       sb.append("<PFPRICE>"+product.getPrice()+"</PFPRICE>");
		       sb.append("</PLUDATA>");
		       i++;
	       }
	       sb.append("</BILLBODY>");
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
	       
	       String json = XmlUtil.xml2json(str);
	       
	       HaixinResult haixinResult = XmlUtil.parseJsonWithGson(json, HaixinResult.class);
	       
	       String code= haixinResult.getRESULT_CODE();
	       
	   return code;
	   }
}
	
	
