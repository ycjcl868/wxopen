package com.wx.open.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.wx.open.common.util.DateUtil;
import com.wx.open.common.util.InitPropertiesUtils;
import com.wx.open.common.util.StringUtil;
import com.wx.open.common.util.WeiXinConfHelper;
import com.wx.open.common.util.WeiXinUtil;
import com.wx.open.service.IWeiXinOpenService;
import com.wx.open.service.WeiXinContext;

@Service("weiXinOpenService")
public class WeiXinOpenServiceImpl implements IWeiXinOpenService{
	private static Logger logger = LoggerFactory.getLogger(WeiXinOpenServiceImpl.class);
	
	private int connTimeout = 3000;
	private int readTimeout = 3000;
	
	public String getSecret(String appid)throws Exception{
		if(StringUtil.isEmpty(appid)){
			throw new Exception("appid is null");
		}
		String secret = WeiXinConfHelper.getBaseContext(appid);
		if(StringUtil.isEmpty(secret)){
			throw new Exception("getBaseContext err secret is null");
		}
		return secret;
	}
	
	@Override
	public void refreshAccessToken(String appid, String secret)throws Exception {
		logger.info("Enter the method refreshAccessToken()");
		try {
			Map<String, String> ret = WeiXinUtil.getAccessToken(appid, secret, getConnTimeout(), getReadTimeout());
			String accessToken = ret.get("accessToken");
			String expiresIn = ret.get("expiresIn");
			WeiXinContext.getInstance().putAccessToken(appid,accessToken,expiresIn);
		} catch (Exception e) {
			logger.error("refreshAccessToken err {}",e.getMessage());
			throw e;
		}
		logger.info("Exit the method refreshAccessToken()");
	}
	
	@Override
	public String getAccessToken(String appid) throws Exception {
		logger.info("Enter the method getAccessToken()");
		String accessToken = "";
		try {
			String secret = getSecret(appid);//获取secret
			
			//校验是否过期
			String initIn = WeiXinContext.getInstance().getAccessTokenInitIn(appid);//初始化时间
			String expireIn = WeiXinContext.getInstance().getAccessTokenExpiresIn(appid);//失效时间
			if(StringUtil.isNotEmpty(initIn) && StringUtil.isNotEmpty(expireIn)
				&& StringUtil.isNumeric(expireIn)){
				Date anotherDate = DateUtil.getNextDate(initIn, Integer.parseInt(expireIn)-10*60);//提前10分钟刷新
				if(DateUtil.compareTo(new Date(), anotherDate) >= 0){//当前时间大于或者等于失效时间、刷新
					refreshAccessToken(appid, secret);
				}
			}
			
			//获取jsapiTicket、防止第一次加载
			accessToken = WeiXinContext.getInstance().getAccessToken(appid);
			if(StringUtil.isEmpty(accessToken)){
				refreshAccessToken(appid, secret);
				accessToken = WeiXinContext.getInstance().getAccessToken(appid);
			}
		} catch (Exception e) {
			logger.error("getAccessToken err {}",e.getMessage());
			throw e;
		}
		logger.info("Exit the method getAccessToken()");
		return accessToken;
	}
	
	@Override
	public void refreshJsapiTicket(String appid,String accessToken) throws Exception {
		logger.info("Enter the method refreshJsapiTicket()");
		try {
			if(StringUtil.isEmpty(accessToken)){
				logger.error("refreshJsapiTicket err accessToken is null");
				return;
			}
			Map<String, String> ret = WeiXinUtil.getJsapiTicket(accessToken, getConnTimeout(), getReadTimeout());
			String jsapiTicket = ret.get("jsapiTicket");
			String expiresIn = ret.get("expiresIn");
			WeiXinContext.getInstance().putJsapiTicket(appid,jsapiTicket,expiresIn);
		} catch (Exception e) {
			logger.error("refreshJsapiTicket err {}",e.getMessage());
			throw e;
		}
		logger.info("Exit the method refreshJsapiTicket()");
	}
	
	@Override
	public String getJsapiTicket(String appid,String accessToken) throws Exception {
		logger.info("Enter the method getJsapiTicket()");
		String jsapiTicket = "";
		try {
			if(StringUtil.isEmpty(accessToken)){
				logger.error("accessToken is null");
				return jsapiTicket;
			}
			
			//校验是否过期
			String initIn = WeiXinContext.getInstance().getJsapiTicketInitIn(appid);//初始化时间
			String expireIn = WeiXinContext.getInstance().getJsapiTicketExpiresIn(appid);//失效时间
			if(StringUtil.isNotEmpty(initIn) && StringUtil.isNotEmpty(expireIn)
				&& StringUtil.isNumeric(expireIn)){
				Date anotherDate = DateUtil.getNextDate(initIn, Integer.parseInt(expireIn)-10*60);//提前10分钟刷新
				if(DateUtil.compareTo(new Date(), anotherDate) >= 0){//当前时间大于或者等于失效时间、刷新
					refreshJsapiTicket(appid,accessToken);
				}
			}
			
			//获取jsapiTicket、防止第一次加载
			jsapiTicket = WeiXinContext.getInstance().getJsapiTicket(appid);
			if(StringUtil.isEmpty(jsapiTicket)){
				refreshJsapiTicket(appid,accessToken);
				jsapiTicket = WeiXinContext.getInstance().getJsapiTicket(appid);
			}
		} catch (Exception e) {
			logger.error("getJsapiTicket err {}",e.getMessage());
		}
		logger.info("Exit the method getJsapiTicket()");
		return jsapiTicket;
	}
	
	@Override
	public Map<String, String> sign(String appid,String url) throws Exception {
		logger.info("Enter the method sign()");
		Map<String, String> ret = new HashMap<String, String>();
		try {
			String accessToken = getAccessToken(appid);
			if(StringUtil.isEmpty(accessToken)){
				throw new Exception("getAccessToken err accessToken is null");
			}
			
			String jsapiTicket = getJsapiTicket(appid,accessToken);
			if(StringUtil.isEmpty(jsapiTicket)){
				throw new Exception("getJsapiTicket err jsapiTicket is null");
			}
			
			String nonceStr = WeiXinContext.getInstance().getNonceStr(appid);
			String timestamp = WeiXinContext.getInstance().getTimestamp(appid);
			ret = WeiXinUtil.sign(jsapiTicket, url, nonceStr, timestamp);
		} catch (Exception e) {
			logger.error("sign err {}",e.getMessage());
			throw e;
		}
		logger.info("Exit the method sign()");
		return ret;
	}
	
	@Override
	public Map<String, String> snsapiBase(String appid,String code)throws Exception{
		logger.info("Enter the method snsapiBase()");
		Map<String, String> ret = new HashMap<String, String>();
		try {
			String secret = getSecret(appid);//获取secret
			
			/*进行oauth2认证*/
			Map<String, String> oauth2 = WeiXinUtil.oauth2AccessToken(appid, secret, code, 
					getConnTimeout(), getReadTimeout());
			String accessToken = oauth2.get("accessToken");
			String openid = oauth2.get("openid");
			if(StringUtil.isEmpty(accessToken) || StringUtil.isEmpty(openid)){
				throw new Exception("oauth2 err");
			}
			ret.put("openid", "openid");
		} catch (Exception e) {
			logger.error("snsapiBase err {}",e.getMessage());
			throw e;
		}
		logger.info("Exit the method snsapiBase()");
		return ret;
	}
	
	@Override
	public Map<String, String> snsapiUserinfo(String appid,String code)throws Exception{
		logger.info("Enter the method snsapiUserinfo()");
		Map<String, String> ret = new HashMap<String, String>();
		try {
			String secret = getSecret(appid);//获取secret
			
			/*进行oauth2认证*/
			Map<String, String> oauth2 = WeiXinUtil.oauth2AccessToken(appid, secret, code, 
					getConnTimeout(), getReadTimeout());
			String accessToken = oauth2.get("accessToken");
			String openid = oauth2.get("openid");
			if(StringUtil.isEmpty(accessToken) || StringUtil.isEmpty(openid)){
				throw new Exception("oauth2 err accessToken【"+accessToken+"】,openid 【"+openid+"】");
			}
			
			/*网页授权获取用户基本信息*/
			ret = WeiXinUtil.snsapiUserinfo(accessToken, openid, "", getConnTimeout(), getReadTimeout());
		} catch (Exception e) {
			logger.error("snsapiUserinfo err {}",e.getMessage());
			throw e;
		}
		logger.info("Exit the method snsapiUserinfo()");
		return ret;
	}
	
	@Override
	public Map<String, String> userinfo(String appid,String openid)throws Exception{
		logger.info("Enter the method userinfo()");
		Map<String, String> ret = new HashMap<String, String>();
		try {
			if(StringUtil.isEmpty(appid) || StringUtil.isEmpty(openid)){
				throw new Exception("param is null");
			}
			
			String accessToken = getAccessToken(appid);
			if(StringUtil.isEmpty(accessToken)){
				throw new Exception("getAccessToken err accessToken is null");
			}
			
			ret = WeiXinUtil.userinfo(accessToken, openid, "", getConnTimeout(), getReadTimeout());
		} catch (Exception e) {
			logger.error("userinfo err {}",e.getMessage());
			throw e;
		}
		logger.info("Exit the method userinfo()");
		return ret;
	}
	
	public void setConnTimeout(int connTimeout) {
		this.connTimeout = connTimeout;
	}
	
	public void setReadTimeout(int readTimeout) {
		this.readTimeout = readTimeout;
	}
	
	public int getConnTimeout() {
		String connTimeoutStr = InitPropertiesUtils.getProperty("weixin.open.conntimeout");
		if(StringUtil.isNotEmpty(connTimeoutStr)){
			connTimeout = Integer.parseInt(connTimeoutStr);
		}
		return connTimeout;
	}
	
	public int getReadTimeout() {
		String readTimeoutStr = InitPropertiesUtils.getProperty("weixin.open.readtimeout");
		if(StringUtil.isNotEmpty(readTimeoutStr)){
			readTimeout = Integer.parseInt(readTimeoutStr);
		}
		return readTimeout;
	}
	
}
