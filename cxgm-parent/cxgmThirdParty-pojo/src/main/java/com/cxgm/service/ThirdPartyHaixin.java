package com.cxgm.service;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.net.MalformedURLException;
import java.rmi.RemoteException;

import javax.xml.namespace.QName;
import javax.xml.rpc.ServiceException;
import javax.xml.soap.Name;
import javax.xml.soap.SOAPException;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.message.PrefixedQName;
import org.apache.axis.message.SOAPHeaderElement;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
/**
 * 海信业务对接
 * User: CQL
 *
 */
public class ThirdPartyHaixin {
	
	/* public static void main(String[] args) {  
		          String url = "http://221.219.243.5:8099/HsMisWebSrv.dll/wsdl/IHsMisWebSrv";  
		          
		          StringBuilder sb = new StringBuilder();  
		          sb.append("<?xml version='1.0' encoding='UTF-8'?>");  
		          sb.append("<IMPORTDATA>");  
		          sb.append(" <OPERATION>1</OPERATION>");
		          sb.append(" <DEVBRAND>1</DEVBRAND>");
		          sb.append(" <DEVNO>1</DEVNO>");
		          sb.append(" <CERTYPE>01</CERTYPE>");
		          sb.append(" <CERSIGN>096F5CE12EBDAC1C7274FE36515A716E4B5F052B5A920AFCCF53829D1A7876D074B6B463A0A6E75CB8EC3F28DEE42A0C9E145d4e9eb7411d52a1e5cdec5e4bbce23</CERSIGN>");
		          sb.append("</IMPORTDATA>");
		          
		          String  xmlString = sb.toString();
		          
		         String result = post(url,xmlString);
		          
		         System.out.println(result);
		      } */

	public static String post(String url,String xmlString){    
		          //关闭   
		          System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.SimpleLog");     
		          System.setProperty("org.apache.commons.logging.simplelog.showdatetime", "true");     
		          System.setProperty("org.apache.commons.logging.simplelog.log.org.apache.commons.httpclient", "stdout");    
		            
		          //创建httpclient工具对象   
		          HttpClient client = new HttpClient();    
		          //创建post请求方法   
		          PostMethod myPost = new PostMethod(url);    
		          //设置请求超时时间   
		          client.setConnectionTimeout(300*1000);  
		          String responseString = null;    
		          try{    
		              //设置请求头部类型   
		              myPost.setRequestHeader("Content-Type","text/xml");  
		              myPost.setRequestHeader("charset","utf-8");  
		                
		              //设置请求体，即xml文本内容，注：这里写了两种方式，一种是直接获取xml内容字符串，一种是读取xml文件以流的形式   
		           myPost.setRequestBody(xmlString);   
		                
		              /*InputStream body=this.getClass().getResourceAsStream("/"+xmlFileName);  
		              myPost.setRequestBody(body); */ 
		 //            myPost.setRequestEntity(new StringRequestEntity(xmlString,"text/xml","utf-8"));     
		             int statusCode = client.executeMethod(myPost);    
		             if(statusCode == HttpStatus.SC_OK){    
		                 BufferedInputStream bis = new BufferedInputStream(myPost.getResponseBodyAsStream());    
		                  byte[] bytes = new byte[1024];    
		                  ByteArrayOutputStream bos = new ByteArrayOutputStream();    
		                  int count = 0;    
		                  while((count = bis.read(bytes))!= -1){    
		                      bos.write(bytes, 0, count);    
		                  }    
		                  byte[] strByte = bos.toByteArray();    
		                  responseString = new String(strByte,0,strByte.length,"utf-8");    
		                  bos.close();    
		                  bis.close();    
		              }    
		          }catch (Exception e) {    
		              e.printStackTrace();    
		          }    
		         myPost.releaseConnection();    
		         return responseString;    
		    } 
	
	
	public static String getResult() throws ServiceException, MalformedURLException, RemoteException, SOAPException
	  {
	     //标识Web Service的具体路径
	   String endpoint = "http://221.219.243.5:8099/HsMisWebSrv.dll";
	   Service service = new Service();
	   Call call = (Call) service.createCall();  
       call.setTargetEndpointAddress(endpoint);  
       call.setOperationName("IHsMisWebSrvservice");// WSDL里面描述的接口名称  
       call.addParameter("pIntfCode",  
               org.apache.axis.encoding.XMLType.XSD_STRING,  
               javax.xml.rpc.ParameterMode.IN);// 接口的参数  
       call.addParameter("pInData",  
               org.apache.axis.encoding.XMLType.XSD_STRING,  
               javax.xml.rpc.ParameterMode.IN);// 接口的参数
       call.setReturnType(org.apache.axis.encoding.XMLType.XSD_STRING);// 设置返回类型  
	   String temp = "2001";
	   
	   StringBuilder sb = new StringBuilder();  
       sb.append("<?xml version='1.0' encoding='UTF-8'?>");  
       sb.append("<IMPORTDATA>");  
       sb.append(" <OPERATION>1</OPERATION>");
       sb.append(" <DEVBRAND>1</DEVBRAND>");
       sb.append(" <DEVNO>1</DEVNO>");
       sb.append(" <CERTYPE>01</CERTYPE>");
       sb.append(" <CERSIGN>096F5CE12EBDAC1C7274FE36515A716E4B5F052B5A920AFCCF53829D1A7876D074B6B463A0A6E75CB8EC3F28DEE42A0C9E145d4e9eb7411d52a1e5cdec5e4bbce23</CERSIGN>");
       sb.append("</IMPORTDATA>");
       
       String  xmlString = sb.toString();
       
	   String res =(String) call.invoke(new Object[]{temp,xmlString});
	   System.out.println("111111111111111111111111111111111111111111111");
	   System.out.println(res);
	   System.out.println("222222222222222222222222222222222222222222222");
	   return res;
	  }
	/**
	  * @param args
	  */
	 public static void main(String[] args) {
	  try {
	   System.out.println(getResult());
	  } catch (MalformedURLException e) {
	   e.printStackTrace();
	  } catch (RemoteException e) {
	   e.printStackTrace();
	  } catch (ServiceException e) {
	   e.printStackTrace();
	  } catch (SOAPException e) {
	   e.printStackTrace();
	  }
	    }
}
	
	
