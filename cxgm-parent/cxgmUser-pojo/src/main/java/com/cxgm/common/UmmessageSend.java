package com.cxgm.common;

/** 
 * 接收消息的Listener,用于接收订阅到的消息.  
 * @author Administrator 
 * 
 */  
public class UmmessageSend{  
	
	private String appkey = "5b42fb3cf29d98568500000f";
	private String appMasterSecret = "jzdhxdkwctmdizow9op5y3xrpwjc1hgs";
	
	private String iosappkey = "5b2c9d5d8f4a9d6fa1000018";
	private String iosappMasterSecret = "j54rnbj3o3jtbuwzszmzlq1zlkk41iqg";
	
	private PushClient client = new PushClient();
	
  
    public void sendMessage(String title,String content) {  
       try{  
    	    
    	    AndroidBroadcast androidbroadcast = new AndroidBroadcast(appkey,appMasterSecret);
    	    androidbroadcast.setTitle(title);
    	    androidbroadcast.setCustomField(content);
    	    androidbroadcast.goAppAfterOpen();
    	    androidbroadcast.setDisplayType(AndroidNotification.DisplayType.MESSAGE);
    	    
    	    androidbroadcast.setProductionMode();
	   		client.send(androidbroadcast);
	   		
	   		IOSBroadcast iosbroadcast = new IOSBroadcast(iosappkey,iosappMasterSecret);
	   		
	   		iosbroadcast.setAlert(title);
	   		iosbroadcast.setBadge( 0);
	   		iosbroadcast.setSound( "default");
			// TODO set 'production_mode' to 'true' if your app is under production mode
	   		iosbroadcast.setTestMode();
			// Set customized fields
	   		
	   		iosbroadcast.setCustomizedField(title, content);
	   		client.send(iosbroadcast);
        }catch(Exception e){
        	e.printStackTrace();
        }  
    }  
}  
