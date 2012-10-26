package com.analytic;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class Propery {
	/**
	 * 
	 * @param str 解析的properties文件名
	 *            
	 * @return mp 存放解析内容的HashMap
	 */
	public static Map<String, String> analytic(String str) {
		Map<String, String> mp = new HashMap<String, String>();
		// 解析properties时所需要的类
		Properties pp = new Properties();
		try {
			InputStream in =Propery.class.getClassLoader()
					.getResourceAsStream(str);
			pp.load(in);
		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("加载ftp.properites失败");
			e.printStackTrace();
		}
		Enumeration<Object> mj = pp.keys();
		while (mj.hasMoreElements()) {
			String key = mj.nextElement().toString();
			String value = pp.getProperty(key);
			try {
				// 对key和value转变编码格式
				key = new String(key.getBytes("ISO8859-1"), "UTF-8");
				value = new String(value.getBytes("ISO8859-1"), "UTF-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				System.out.println("转码失败");
				e.printStackTrace();
			}
			mp.put(key, value);
		}
		return mp;
	}
}
