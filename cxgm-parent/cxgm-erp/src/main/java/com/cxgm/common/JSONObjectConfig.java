package com.cxgm.common;

import java.util.Date;

import net.sf.json.JsonConfig;

public class JSONObjectConfig {
	
	
	private static JsonConfig jsonConfig=new JsonConfig();
	
	public static JsonConfig getInstance(){
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		return jsonConfig;
	}
	
	public static JsonConfig getTime(){
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateTimeProcessor());
		return jsonConfig;
	}
}
