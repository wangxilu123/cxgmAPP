package com.cxgm.common;

import java.text.SimpleDateFormat;
import java.util.Date;

/** 
 * 接收消息的Listener,用于接收订阅到的消息.  
 * @author Administrator 
 * 
 */  
public class UmmessageSend{  
	
	private String appkey = "5af6acadb27b0a761e000306";
	private String appMasterSecret = "m72zniduleuccjevyabczru7sri1ec9e";
	
	private String iosappkey = "5b2c54328f4a9d3b2a00003f";
	private String iosappMasterSecret = "p3qvfqpedv9vq0aluqe32ptcj8eey7wq";
	
	private PushClient client = new PushClient();
	
  
    public void sendMessage(String title,String content) {  
       try{  
    	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	    
    	    AndroidBroadcast androidbroadcast = new AndroidBroadcast(appkey,appMasterSecret);
    	    androidbroadcast.setTitle(title);
    	    androidbroadcast.setText(sdf.format(new Date()));
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
	   		iosbroadcast.setDescription(sdf.format(new Date()));
	   		client.send(iosbroadcast);
        }catch(Exception e){
        	e.printStackTrace();
        }  
    }  
}  
