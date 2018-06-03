package com.cxgm.service;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;

import javax.xml.rpc.ServiceException;
import javax.xml.rpc.holders.StringHolder;
import javax.xml.soap.SOAPException;

import org.apache.axis.client.Service;
import org.tempuri.IHsMisWebSrvbindingStub;

/**
 * 海信业务对接
 * User: CQL
 *
 */
public class ThirdPartyHaixin  {
	/**
	  * @param args
	 * @throws ServiceException 
	  */
	 public static void main(String[] args) throws MalformedURLException, SOAPException, ServiceException {
	  try {
		  
		   String endpoint = "http://221.219.243.5:8099/HsMisWebSrv.dll/wsdl/IHsMisWebSrv/IHsMisWebSrvservice";
		   
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
	       
	       
	   System.out.println(cleant.IHiOpenedIntf(pIntfCode, pInData, pOutData));
	  } catch (RemoteException e) {
	   e.printStackTrace();
	  }
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
	
	
