package com.cxgm.common;

/** 
 * 接收消息的Listener,用于接收订阅到的消息.  
 * @author Administrator 
 * 
 */  
public class UmmessageSend{  
	
	private String appkey = "5af6acadb27b0a761e000306";
	private String appMasterSecret = "m72zniduleuccjevyabczru7sri1ec9e";
	private PushClient client = new PushClient();
	
  
    public void sendMessage(String title,String content) {  
       try{  
    	   
    	    AndroidBroadcast broadcast = new AndroidBroadcast(appkey,appMasterSecret);
	   		broadcast.setTitle(title);
	   		broadcast.setText(content);
	   		broadcast.setCustomField(content);
	   		broadcast.goAppAfterOpen();
	   		broadcast.setDisplayType(AndroidNotification.DisplayType.NOTIFICATION);
	   		
	   		broadcast.setProductionMode();
	   		client.send(broadcast);
        }catch(Exception e){
        	e.printStackTrace();
        }  
    }  
}  
