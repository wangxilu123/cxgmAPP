package com.cxgm.common;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class ByteUtil {
    /**  
     * 从字节数组获取对象  
     * @EditTime 2007-8-13 上午11:46:34  
     */   
    public static Object getObjectFromBytes(byte[] objBytes) throws Exception {   
        if (objBytes == null || objBytes.length == 0) {   
            return null;   
        }   
        ByteArrayInputStream bi = new ByteArrayInputStream(objBytes);   
        ObjectInputStream oi = new ObjectInputStream(bi);   
        return oi.readObject();   
    }   
  
    /** 
     * 从对象获取一个字节数组  
     * @EditTime 2007-8-13 上午11:46:56  
     */   
    public static byte[] getBytesFromObject(Serializable obj) throws Exception {   
        if (obj == null) {   
            return null;   
        }   
        ByteArrayOutputStream bo = new ByteArrayOutputStream();   
        ObjectOutputStream oo = new ObjectOutputStream(bo);   
        oo.writeObject(obj);   
        return bo.toByteArray();   
    } 
}
