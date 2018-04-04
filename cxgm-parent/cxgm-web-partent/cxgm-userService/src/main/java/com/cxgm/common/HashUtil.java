package com.cxgm.common;

import java.math.BigInteger;
import java.security.MessageDigest;

public class HashUtil {
    /**
     * 生成Hash串
     */
    public static String getResult(String inputStr){

        BigInteger sha =null;
        byte[] inputData = inputStr.getBytes();   
        try{
            MessageDigest messageDigest = MessageDigest.getInstance("SHA"); //确定计算方法
            messageDigest.update(inputData);
            sha = new BigInteger(messageDigest.digest());   //生成散列码
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sha.toString(32);

    }
}
