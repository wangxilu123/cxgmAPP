package com.cxgm.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

public class HttpUtil  
{  
  public static final String POST = "POST";  
  public static final String GET = "GET";  
  
  
  public static String sendGet(String url, String param)  
  {  
    String result = "";  
    BufferedReader in = null;  
    try {  
      String urlNameString = url + "?" + param;  
      URL realUrl = new URL(urlNameString);  
  
      URLConnection connection = realUrl.openConnection();  
  
      connection.setRequestProperty("accept", "*/*");  
      connection.setRequestProperty("Content-Type", "application/json");  
      connection.setRequestProperty("connection", "Keep-Alive");  
      connection.setRequestProperty("user-agent",   
        "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");  
  
      connection.connect();  
  
      Map map = connection.getHeaderFields();  
  
//      for (String key : map.keySet()) {  
//        System.out.println(key + "--->" + map.get(key));  
//      }  
  
      in = new BufferedReader(new InputStreamReader(  
        connection.getInputStream(), "utf-8"));  
      String line;  
      while ((line = in.readLine()) != null)  
      {  
        //String line;  
        result = result + line;  
      }  
    } catch (Exception e) {  
      e.printStackTrace();  
      try  
      {  
        if (in != null)  
          in.close();  
      }  
      catch (Exception e2) {  
        e2.printStackTrace();  
      }  
    }  
    finally  
    {  
      try  
      {  
        if (in != null)  
          in.close();  
      }  
      catch (Exception e2) {  
        e2.printStackTrace();  
      }  
    }  
    return result;  
  }  
  
  public static String sendPost(String url, String param)  
  {  
    PrintWriter out = null;  
    BufferedReader in = null;  
    String result = "";  
    try {  
      URL realUrl = new URL(url);  
  
      URLConnection conn = realUrl.openConnection();  
  
      conn.setRequestProperty("Content-Type", "application/xml");  
  
      conn.setDoOutput(true);  
      conn.setDoInput(true);  
  
      out = new PrintWriter(conn.getOutputStream());  
  
      out.print(param);  
  
      out.flush();  
  
      in = new BufferedReader(  
        new InputStreamReader(conn.getInputStream()));  
      String line;  
      while ((line = in.readLine()) != null)  
      {  
        //String line;  
        result = result + line;  
      }  
    } catch (Exception e) {  
      e.printStackTrace();  
      try  
      {  
        if (out != null) {  
          out.close();  
        }  
        if (in != null)  
          in.close();  
      }  
      catch (IOException ex)  
      {  
        ex.printStackTrace();  
      }  
    }  
    finally  
    {  
      try  
      {  
        if (out != null) {  
          out.close();  
        }  
        if (in != null)  
          in.close();  
      }  
      catch (IOException ex)  
      {  
        ex.printStackTrace();  
      }  
    }  
    return result;  
  }  
  
  public static enum RequstMethod  
  {  
    GET("GET"), POST("POST");  
  
    private String value;  
  
    private RequstMethod(String value) {  
      this.value = value;  
    }  
  
    public String value() {  
      return this.value;  
    }  
  }  
}
