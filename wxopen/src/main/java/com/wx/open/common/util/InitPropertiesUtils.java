package com.wx.open.common.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <pre>
 *  配置文件工具类
 * </pre>
 *
 * @author jiejie.liao
 * @create 15-1-22 17:37
 * @modify
 * @since JDK1.6
 */
public class InitPropertiesUtils {
	private static Logger logger = LoggerFactory.getLogger(InitPropertiesUtils.class);
	
	private static Properties properties = new Properties();
	private static String propertiesFilePath = null;
	
	static {
		try {
			InputStream is = InitPropertiesUtils.class.getClassLoader().getResourceAsStream("properties/wxconf.properties");
			propertiesFilePath = InitPropertiesUtils.class.getClassLoader().getResource("properties/wxconf.properties").getFile();
			propertiesFilePath = URLDecoder.decode(propertiesFilePath, "UTF-8");
			properties.load(is);
			logger.info("propertiesFilePath【{}】",propertiesFilePath);
		} catch (Exception e) {
			logger.error(null, e);
		}
	}
	
	public static String getProperty(String key) {
		String value = properties.getProperty(key);
		if (value != null) {
			return value.trim();
		}
		return value;
	}
	
	public static void setKey(String key, String value) {
		properties.setProperty(key, value);
		logger.info(propertiesFilePath + " setKey:" + key + "=" + value);
		saveKey("InitPropertiesUtils");
	}
	
	/**
	 * 修改或添加键值对 如果key存在，修改否则添加。
	 * 
	 * @param key
	 * @param value
	 */
	private static void saveKey(String comment) {
		FileOutputStream outputFile = null;
		try {
			outputFile = new FileOutputStream(propertiesFilePath);
			properties.store(outputFile, comment);
		} catch (IOException e) {
			logger.error(null, e);
		} finally {
			try {
				outputFile.close();
			} catch (IOException e) {
			}
		}
	}
}
