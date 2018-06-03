package com.cxgm.common;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * 日期工具类。<br>
 *
 * @author zhou jintong
 * @version 1.0
 */
public  class XmlUtil {

    /** 
     * 将请求参数转换为xml格式的string 
     * 通知微信.异步确认成功.必写.不然会一直通知后台.八次之后就认为交易失败了. 
     *  
     */  
    @SuppressWarnings("all")  
    public static String getRequestXml(final SortedMap<String, String> parameters) {  
        StringBuffer sb = new StringBuffer();  
        sb.append("<xml>");  
        Set es = parameters.entrySet();  
        Iterator it = es.iterator();  
        while (it.hasNext()) {  
            Map.Entry entry = (Map.Entry) it.next();  
            String k = (String) entry.getKey();  
            String v = (String) entry.getValue();  
            if ("attach".equalsIgnoreCase(k) || "body".equalsIgnoreCase(k) || "sign".equalsIgnoreCase(k)) {  
                sb.append("<" + k + ">" + "<![CDATA[" + v + "]]></" + k + ">");  
            } else {  
                sb.append("<" + k + ">" + v + "</" + k + ">");  
            }  
        }  
        sb.append("</xml>");  
        return sb.toString();  
    }  
  
    /** 
     * main 
     */  
    public static void main(final String[] arg) {  
        SortedMap<String, String> sm = new TreeMap<String, String>();  
        sm.put("SUCCESS", "OK");  
        String ss = getRequestXml(sm);  
        System.out.println(ss);  
    }
}