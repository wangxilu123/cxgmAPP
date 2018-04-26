package com.cxgm.common;

import java.util.Arrays;
import java.util.Comparator;

/**
 * 字符串交集工具类。<br>
 *
 * @author wangxilu
 * @version 1.0
 */
public class StringUtil {

	 public static String isExist(String user_tag,String select_tag){
		    if("".equals(select_tag)||select_tag==null){
		    	return "";
		    }
	        String[] userArray=user_tag.split(",");  
	        String[] selectArray=select_tag.split(",");  
	        //对标签进行排序  
	        sort(selectArray);sort(userArray);  
	        StringBuffer  result=new StringBuffer();
	        for(String select:selectArray){  
	              
	            for(String user:userArray){  
	                if(select.equals(user)){  
	                	result.append(","+select);
	                }
	            }
	        }
	        result.append(",");
	        return result.toString();  
	    }  
	    /** 
	     * 对标签数组进行排序 
	     * @param tags 
	     */  
	    private static void sort(String[] tags){  
	        Arrays.sort(tags,new Comparator<String>(){  
	            @Override  
	            public int compare ( String o1,String o2){  
	                if (Integer.parseInt(o1)>Integer.parseInt(o2)){  
	                    return 1;  
	                }else if (Integer.parseInt(o1)<Integer.parseInt(o2)){  
	                    return -1;  
	                }else{  
	                    return 0;  
	                }  
	            }  
	        });  
	    } 
}