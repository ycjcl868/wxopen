package com.wx.open.common;

/**
 * <pre>
 *  WAP常量定义
 * </pre>
 *
 * @author jiejie.liao
 * @create 2014.10.24
 * @modify
 * @since JDK1.6
 */
public class Constants {
	/**
	 * 基础key
	 * 
	 * @author jiejie.liao
	 *
	 */
    public final static class BASE_KEY{
    	
    }
    
    /**
     * 活动配置
     * 
     * @author jiejie.liao
     *
     */
    public final static class CODE{
    	public final static String SUCCESS = "0000";
    	public final static String ERROR = "9999";
    }
    
    /**
     * 微信API
     * 
     * @author jiejie.liao
     *
     */
    public final static class WEIXIN_API{
    	public final static String ACCESSTOKEN = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential";
    	public final static String JSAPITICKET = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?type=jsapi";
    	public final static String OAUTH2ACCESSTOKEN = "https://api.weixin.qq.com/sns/oauth2/access_token?grant_type=authorization_code";
    	public final static String SNSAPIUSERINFO = "https://api.weixin.qq.com/sns/userinfo?";
    	public final static String USERINFO = "https://api.weixin.qq.com/cgi-bin/user/info?";
    }
}