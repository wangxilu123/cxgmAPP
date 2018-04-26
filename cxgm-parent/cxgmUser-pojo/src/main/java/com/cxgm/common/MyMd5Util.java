package com.cxgm.common;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;  
  
/**  
 * @author fengcai ouyang  
 * @version V1.0  
 * @Description:  
 * @date 2016/7/23 18:48  
 * @Update by: ${todo}  
 */  
public class MyMd5Util {  
    private static MessageDigest sMd5MessageDigest;  
    private static StringBuilder sStringBuilder;  
  
    private MyMd5Util() {  
    }  
  
    public static String md5(String s) {  
        sMd5MessageDigest.reset();  
        sMd5MessageDigest.update(s.getBytes());  
        byte[] digest = sMd5MessageDigest.digest();  
        sStringBuilder.setLength(0);  
  
        for (int i = 0; i < digest.length; ++i) {  
            int b = digest[i] & 255;  
            if (b < 16) {  
                sStringBuilder.append('0');  
            }  
  
            sStringBuilder.append(Integer.toHexString(b));  
        }  
  
        return sStringBuilder.toString().toUpperCase();  
    }  
  
    static {  
        try {  
            sMd5MessageDigest = MessageDigest.getInstance("MD5");  
        } catch (NoSuchAlgorithmException var1) {  
  
        }  
  
        sStringBuilder = new StringBuilder();  
    }  
}
