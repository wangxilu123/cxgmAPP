package com.cxgm.common;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.xml.XMLSerializer;

/**
 * 日期工具类。<br>
 *
 * @author zhou jintong
 * @version 1.0
 */
public  class XmlUtil {
	 private static XMLSerializer xmlserializer = new XMLSerializer();

	public static String xml2json(String xmlString){
        if(StringUtils.isNotBlank(xmlString)){
            try {
                return xmlserializer.read(xmlString).toString();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }
	
	/***
     * JSON 转换为 List
     * @param jsonStr
     *         [{"age":12,"createTime":null,"id":"","name":"wxw","registerTime":null,"sex":1},{...}]
     * @param objectClass
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> List<T> json2List(String jsonStr, Class<T> objectClass){  
        if (StringUtils.isNotBlank(jsonStr)) {
            JSONArray jsonArray = JSONArray.fromObject(jsonStr);  
            List<T> list = (List<T>) JSONArray.toCollection(jsonArray, objectClass);  
            return list;  
        }
        return null;
    }  
    // 将Json数据解析成相应的映射对象
         public static <T> T parseJsonWithGson(String jsonData, Class<T> type) {
             Gson gson = new Gson();
             T result = gson.fromJson(jsonData, type);
             return result;
         }
     
         // 将Json数组解析成相应的映射对象列表
         public static <T> List<T> parseJsonArrayWithGson(String jsonData,
                 Class<T> type) {
             Gson gson = new Gson();
             List<T> result = gson.fromJson(jsonData, new TypeToken<List<T>>() {
             }.getType());
             return result;
         }
}