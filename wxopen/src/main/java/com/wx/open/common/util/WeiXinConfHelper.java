package com.wx.open.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <pre>
 *  WeiXinConfHelper帮助工具类
 * </pre>
 *
 * @author jiejie.liao
 * @create 2014.10.24
 * @modify
 * @since JDK1.6
 */
public class WeiXinConfHelper {
	private static Logger logger = LoggerFactory.getLogger(WeiXinConfHelper.class);
    
    /**
     * 获取缓存配置
     * 
     * @param key
     * @return
     */
    public static String getBaseContext(String appid){
    	String value = "";
    	try {
    		value = InitPropertiesUtils.getProperty(appid);
    	} catch (Exception e) {
			logger.error("getBaseContext err {}",e.getMessage());
		}
    	return value;
    }
    
    /**
     * 获取缓存配置
     * 
     * @param key
     * @param defValue
     * @return
     */
    public static String getBaseContext(String appid,String appSecret){
    	String value = getBaseContext(appid);
    	return StringUtil.isEmpty(value)?appSecret:value;
    }
    
}
