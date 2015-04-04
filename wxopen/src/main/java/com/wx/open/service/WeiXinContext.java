package com.wx.open.service;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.wx.open.common.util.DateUtil;
import com.wx.open.common.util.WeiXinUtil;

/**
 * <pre>
 *  微信本地缓存上下文
 * </pre>
 *
 * @author jiejie.liao
 * @create 14-9-12 17:37
 * @modify
 * @since JDK1.6
 */
public class WeiXinContext {
    private static Object lock = new Object();
    private volatile static WeiXinContext weiXinContext;
    
    private Map<String, String> weiXinMap;
    private final static class WeiXin_KEY{
		private final static String AT_ = "at_";//AccessToken
		private final static String ATP_ = "atp_";//失效前AccessToken
		private final static String ATI_ = "ati_";//初始化时间
		private final static String ATE_ = "ate_";//失效时间
		
		private final static String JT_ = "jt_";//jsapiTicket
		private final static String JTP_ = "jtp_";//失效前jsapiTicket
		private final static String JTI_ = "jti_";//初始化时间
		private final static String JTE_ = "jte_";//失效时间
		
		private final static String NS = "ns_";//nonceStr
		private final static String TS = "ts_";//timestamp
	}
    
    private WeiXinContext() {
    	weiXinMap = new ConcurrentHashMap<String, String>();
    }
    
    public static WeiXinContext getInstance() {
        synchronized (lock) {
            if (weiXinContext == null) {
            	weiXinContext = new WeiXinContext();
            }
        }
        return weiXinContext;
    }
    
	public Map<String, String> getWeiXinMap() {
		return weiXinMap;
	}
    
	/**
	 * 将accessToken缓存到本地内存中<br/>
	 * 微信接口调用基础凭证
	 * 
	 * @param accessToken
	 */
	private void putAccessToken(String appid,String accessToken){
		if(accessToken == null || "".equals(accessToken)){
			return;
		}
		String prevToken = getAccessToken(appid);
		weiXinMap.put(WeiXin_KEY.AT_+appid, accessToken);
		if(prevToken == null || "".equals(prevToken)){
			return;
		}
		weiXinMap.put(WeiXin_KEY.ATP_+appid, prevToken);
	}
	
	/**
	 * 将accessToken缓存到本地内存中<br/>
	 * 微信接口调用基础凭证
	 * 
	 * @param accessToken
	 * @param expiresIn 失效时间、单位（秒）
	 */
	public void putAccessToken(String appid,String accessToken,String expiresIn){
		this.putAccessToken(appid,accessToken);
		if(expiresIn == null || "".equals(expiresIn)){
			return;
		}
		weiXinMap.put(WeiXin_KEY.ATI_+appid, DateUtil.date2String(new Date(), 
				DateUtil.YYYYMMDDHHMMSS));//初始化时间
		weiXinMap.put(WeiXin_KEY.ATE_+appid, expiresIn);//过期时间
	}
	
	/**
	 * 获取accessToken本地缓存<br/>
	 * 微信接口调用基础凭证
	 * 
	 * @return
	 */
	public String getAccessToken(String appid){
		return weiXinMap.get(WeiXin_KEY.AT_+appid);
	}
	
	/**
	 * 获取初始化时间<br/>
	 * accessTokenInitIn本地缓存、微信接口调用基础凭证
	 * 
	 * @return
	 */
	public String getAccessTokenInitIn(String appid){
		return weiXinMap.get(WeiXin_KEY.ATI_+appid);
	}
	
	/**
	 * 获取失效时间<br/>
	 * accessTokenExpiresIn本地缓存、微信接口调用基础凭证
	 * 
	 * @return
	 */
	public String getAccessTokenExpiresIn(String appid){
		return weiXinMap.get(WeiXin_KEY.ATE_+appid);
	}
	
	/**
	 * 将jsapiTicket缓存到本地内存中<br/>
	 * 微信JS-SDK接口调用基础凭证
	 * 
	 * @param jsapiTicket
	 */
	private void putJsapiTicket(String appid,String jsapiTicket){
		if(jsapiTicket == null || "".equals(jsapiTicket)){
			return;
		}
		String prevTicket = getJsapiTicket(appid);
		weiXinMap.put(WeiXin_KEY.JT_+appid, jsapiTicket);
		if(prevTicket == null || "".equals(prevTicket)){
			return;
		}
		weiXinMap.put(WeiXin_KEY.JTP_+appid, prevTicket);
	}
	
	/**
	 * 将jsapiTicket缓存到本地内存中<br/>
	 * 微信JS-SDK接口调用基础凭证
	 * 
	 * @param jsapiTicket
	 * @param expiresIn 失效时间、单位（秒）
	 */
	public void putJsapiTicket(String appid,String jsapiTicket,String expiresIn){
		this.putJsapiTicket(appid,jsapiTicket);
		if(expiresIn == null || "".equals(expiresIn)){
			return;
		}
		weiXinMap.put(WeiXin_KEY.JTI_+appid, DateUtil.date2String(new Date(), 
				DateUtil.YYYYMMDDHHMMSS));//初始化时间
		weiXinMap.put(WeiXin_KEY.JTE_+appid, expiresIn);//失效时间
	}
	
	/**
	 * 获取jsapiTicket<br/>
	 * 本地缓存、微信JS-SDK接口调用基础凭证
	 * 
	 * @return
	 */
	public String getJsapiTicket(String appid){
		return weiXinMap.get(WeiXin_KEY.JT_+appid);
	}
	
	/**
	 * 获取初始化时间<br/>
	 * jsapiTicketInitIn本地缓存、微信JS-SDK接口调用基础凭证
	 * 
	 * @return
	 */
	public String getJsapiTicketInitIn(String appid){
		return weiXinMap.get(WeiXin_KEY.JTI_+appid);
	}
	
	/**
	 * 获取失效时间<br/>
	 * jsapiTicketExpiresIn本地缓存、微信JS-SDK接口调用基础凭证
	 * 
	 * @return
	 */
	public String getJsapiTicketExpiresIn(String appid){
		return weiXinMap.get(WeiXin_KEY.JTE_+appid);
	}
	
	public String getNonceStr(String appid){
		String nonceStr = weiXinMap.get(WeiXin_KEY.NS+appid);
		if(nonceStr != null && !"".equals(nonceStr)){
			return nonceStr;
		}
		synchronized (lock) {
			nonceStr = WeiXinUtil.create_nonce_str();
			weiXinMap.put(WeiXin_KEY.NS+appid, nonceStr);
        }
		return nonceStr;
	}
	
	public String getTimestamp(String appid){
		String timestamp = weiXinMap.get(WeiXin_KEY.TS+appid);
		if(timestamp != null && !"".equals(timestamp)){
			return timestamp;
		}
		synchronized (lock) {
			timestamp = WeiXinUtil.create_timestamp();
			weiXinMap.put(WeiXin_KEY.TS+appid, timestamp);
        }
		return timestamp;
	}
}
