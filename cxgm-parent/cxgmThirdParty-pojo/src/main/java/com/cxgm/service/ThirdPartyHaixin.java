package com.cxgm.service;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;

import javax.xml.rpc.ServiceException;
import javax.xml.rpc.holders.StringHolder;
import javax.xml.soap.SOAPException;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.commons.codec.binary.Base64;
import org.tempuri.IHsMisWebSrvbindingStub;

import sun.misc.BASE64Decoder;

/**
 * 海信业务对接
 * User: CQL
 *
 */
public class ThirdPartyHaixin  {
	/**
	  * @param args
	 * @throws ServiceException 
	 * @throws IOException 
	 * @throws UnsupportedEncodingException 
	  */
	 public static void main(String[] args) throws SOAPException, ServiceException, UnsupportedEncodingException, IOException {
	  try {
		  
		 String endpoint = "http://221.219.243.5:8099/HsMisWebSrv.dll/soap/IHsMisWebSrv";
		   
		   URL url = new URL(endpoint);
		   Service service = new Service();
		   
		   IHsMisWebSrvbindingStub cleant =  new IHsMisWebSrvbindingStub(url,service);
		   
		   
		   String pIntfCode = "2001";
		   StringBuilder sb = new StringBuilder();  
	       sb.append("<IMPORTDATA>");  
	       sb.append("<OPERATION>1</OPERATION>");
	       sb.append("<DEVBRAND>1</DEVBRAND>");
	       sb.append("<DEVNO>1</DEVNO>");
	       sb.append("<CERTYPE>01</CERTYPE>");
	       sb.append("<CERSIGN>112556653728E050D8350900446C390E93B6CBA90DD8D4135C09CCBC3C9C05FEE241FC1CE3DAF0EE1D5499060D46CD62D0B04CA0C4300819BC755e164aa45664fded1b54ae2fdbd818e</CERSIGN>");
	       sb.append("</IMPORTDATA>");
	       
	       String  pInData = sb.toString();
		  
	       StringHolder pOutData = new StringHolder();
	       
	       cleant.IHiOpenedIntf(pIntfCode, pInData, pOutData);
	       
	       String result = cleant._getCall().getOutputParams().values().toString().replace("[", "").replaceAll("]", "");
	       
	       BASE64Decoder decoder = new BASE64Decoder();
	       
	       String str = new String(decoder.decodeBuffer(result), "UTF-8");
	   System.out.println("111111111111111111111111111111"+str);
		  
		 /* getResult();*/
	  } catch (RemoteException e) {
	   e.printStackTrace();
	  }
	    }
	 
	 
	 public static String getResult() throws ServiceException, MalformedURLException, RemoteException, SOAPException
	  {
	     //标识Web Service的具体路径
	   String endpoint = "http://221.219.243.5:8099/HsMisWebSrv.dll/soap/IHsMisWebSrv/IHsMisWebSrvservice";
	   Service service = new Service();
	   Call call = (Call) service.createCall();  
      call.setTargetEndpointAddress(endpoint);  
      call.setOperationName("IHiOpenedIntf");// WSDL里面描述的接口名称  
      call.addParameter("pIntfCode",  
              org.apache.axis.encoding.XMLType.XSD_STRING,  
              javax.xml.rpc.ParameterMode.IN);// 接口的参数  
      call.addParameter("pInData",  
              org.apache.axis.encoding.XMLType.XSD_STRING,  
              javax.xml.rpc.ParameterMode.IN);// 接口的参数
      
      call.addParameter("pOutData",  
              org.apache.axis.encoding.XMLType.XSD_STRING,  
              javax.xml.rpc.ParameterMode.IN);// 接口的参数
      call.setReturnType(org.apache.axis.encoding.XMLType.XSD_STRING);// 设置返回类型  
	   String pIntfCode = "2001";
	   
	  StringBuilder sb = new StringBuilder();  
      sb.append("<?xml version='1.0' encoding='UTF-8'?>");  
      sb.append("<IMPORTDATA>");  
      sb.append("<OPERATION>1</OPERATION>");
      sb.append("<DEVBRAND>1</DEVBRAND>");
      sb.append("<DEVNO>1</DEVNO>");
      sb.append("<CERTYPE>01</CERTYPE>");
      sb.append("<CERSIGN>112556653728E050D8350900446C390E93B6CBA90DD8D4135C09CCBC3C9C05FEE241FC1CE3DAF0EE1D5499060D46CD62D0B04CA0C4300819BC755e164aa45664fded1b54ae2fdbd818e</CERSIGN>");
      sb.append("</IMPORTDATA>");
      
       String  pInData = sb.toString();
      
       String pOutData = new String();
	   String res =(String) call.invoke(new Object[]{pIntfCode,pInData,pOutData});
	   System.out.println("111111111111111111111111111111111111111111111");
	   System.out.println(res);
	   System.out.println("222222222222222222222222222222222222222222222");
	   return res;
	  }
	 /*public String format(String unformattedXml) {
	        try {
	            final Document document = parseXmlFile(unformattedXml);
	            OutputFormat format = new OutputFormat(document);
	            format.setLineWidth(65);
	            format.setIndenting(true);
	            format.setIndent(2);
	            Writer out = new StringWriter();
	            XMLSerializer serializer = new XMLSerializer(out, format);
	            serializer.serialize(document);
	            return out.toString();
	        } catch (IOException e) {
	            throw new RuntimeException(e);
	        }
	    }

	    private Document parseXmlFile(String in) {
	        try {
	            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	            DocumentBuilder db = dbf.newDocumentBuilder();
	            InputSource is = new InputSource(new StringReader(in));
	            return db.parse(is);
	        } catch (ParserConfigurationException e) {
	            throw new RuntimeException(e);
	        } catch (SAXException e) {
	            throw new RuntimeException(e);
	        } catch (IOException e) {
	            throw new RuntimeException(e);
	        }
	    }*/
}
	
	
