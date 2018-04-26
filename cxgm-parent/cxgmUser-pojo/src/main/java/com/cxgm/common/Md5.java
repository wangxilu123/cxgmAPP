package com.cxgm.common;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5 {
	/**
	 * Md5加密
	 * 
	 * @param param
	 * @return
	 */
	public static String encode(String param)
	{
		if (param == null)
		{
			return null;
		}
		java.security.MessageDigest md5 = null;
		try
		{
			md5 = MessageDigest.getInstance("MD5");
			byte[] result = md5.digest(param.getBytes("UTF-8"));
			return byte2String(result);
		}
		catch (NoSuchAlgorithmException e)
		{
			return null;
		}
		catch (UnsupportedEncodingException e)
		{
			return null;
		}
	}

	/**
	 * byte2String
	 * 
	 * @param in
	 * @return
	 */
	private static String byte2String(byte[] in)
	{
		DataInputStream data = new DataInputStream(new ByteArrayInputStream(in));
		String str = "";
		try
		{
			for (int j = 0; j < in.length; j++)
			{
				String tmp = Integer.toHexString(data.readUnsignedByte());
				if (tmp.length() == 1)
				{
					tmp = "0" + tmp;
				}
				str = str + tmp;
			}
		}
		catch (Exception ex)
		{
		}
		return str;
	}

	/**
	 * Md5加密
	 * 
	 * @param param
	 * @return
	 */
	public static String encode(byte[] b)
	{
		java.security.MessageDigest md5 = null;
		try
		{
			md5 = MessageDigest.getInstance("MD5");
			byte[] result = md5.digest(b);
			return byte2String(result);
		}
		catch (NoSuchAlgorithmException e)
		{
			return null;
		}
	}

	public static void main(String[] args){
		System.out.println(Md5.encode("111111"));
	}


}
