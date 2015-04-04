package com.wx.open.service;

import java.util.Map;

/**
 * <pre>
 *  微信公众平台服务接口
 * </pre>
 *
 * @author jiejie.liao
 * @create 15-1-22 17:37
 * @modify
 * @since JDK1.6
 */
public interface IWeiXinOpenService {
	/**
	 * 获取appsecret
	 * 
	 * @param appid
	 * @return
	 * @throws Exception
	 */
	public String getSecret(String appid)throws Exception;
	/**
	 * 刷新AccessToken
	 * 
	 * @param appid
	 * @param secret
	 * @return
	 * @throws Exception
	 */
	public void refreshAccessToken(String appid,String secret)throws Exception;
	/**
	 * 获取access token
	 * 
	 * @param appid 第三方用户唯一凭证
	 * @return
	 * @throws Exception
	 */
	public String getAccessToken(String appid)throws Exception;
	/**
	 * 刷新jsapiTicket
	 * 
	 * @param appid
	 * @param accessToken
	 * @return
	 * @throws Exception
	 */
	public void refreshJsapiTicket(String appid,String accessToken)throws Exception;
	/**
	 * 获取jsapiTicket
	 * 
	 * @param appid
	 * @param accessToken 
	 * @return
	 * @throws Exception
	 */
	public String getJsapiTicket(String appid,String accessToken)throws Exception;
	/**
	 * 微信分享签名
	 * 
	 * @param appid
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> sign(String appid,String url)throws Exception;
	/**
	 * 网页授权获取用户基本信息
	 * 
	 * @param appid
	 * @param code
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> snsapiBase(String appid,String code)throws Exception;
	/**
	 * 网页授权获取用户基本信息
	 * 
	 * @param appid
	 * @param code
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> snsapiUserinfo(String appid,String code)throws Exception;
	/**
	 * 获取用户基本信息(UnionID机制)
	 * 
	 * @param appid 
	 * @param openid 普通用户的标识，对当前公众号唯一
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> userinfo(String appid,String openid)throws Exception;
}
