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
	 * @param str ������properties�ļ���
	 *            
	 * @return mp ��Ž������ݵ�HashMap
	 */
	public static Map<String, String> analytic(String str) {
		Map<String, String> mp = new HashMap<String, String>();
		// ����propertiesʱ����Ҫ����
		Properties pp = new Properties();
		try {
			InputStream in =Propery.class.getClassLoader()
					.getResourceAsStream(str);
			pp.load(in);
		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("����ftp.properitesʧ��");
			e.printStackTrace();
		}
		Enumeration<Object> mj = pp.keys();
		while (mj.hasMoreElements()) {
			String key = mj.nextElement().toString();
			String value = pp.getProperty(key);
			try {
				// ��key��valueת������ʽ
				key = new String(key.getBytes("ISO8859-1"), "UTF-8");
				value = new String(value.getBytes("ISO8859-1"), "UTF-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				System.out.println("ת��ʧ��");
				e.printStackTrace();
			}
			mp.put(key, value);
		}
		return mp;
	}
}
