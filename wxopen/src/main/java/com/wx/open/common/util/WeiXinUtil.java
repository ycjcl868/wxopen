package com.wx.open.common.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wx.open.common.Constants;
import com.wx.open.common.util.HttpRequester.HttpRespons;

/**
 * <pre>
 *  微信公众平台工具类
 * </pre>
 *
 * @author jiejie.liao
 * @create 15-1-22 17:37
 * @modify
 * @since JDK1.6
 * @see http://mp.weixin.qq.com/wiki/home/index.html
 */
public class WeiXinUtil {
	private static final Logger logger = LoggerFactory.getLogger(WeiXinUtil.class);
	
	/**
	 * 获取微信accessToken
	 * 
	 * @param appid
	 * @param secret
	 * @param connTimeout
	 * @param readTimeout
	 * @return
	 * @throws Exception
	 */
	public static Map<String, String> getAccessToken(String appid, String secret,int connTimeout,int readTimeout) throws Exception {
		if(logger.isDebugEnabled()){
			logger.info("getAccessToken() appid {},secret {}",new String[]{appid,secret});
		}
		Map<String, String> ret = new HashMap<String, String>();
		String accessToken = "";
		String expiresIn = "";
		try {
			String urlString = Constants.WEIXIN_API.ACCESSTOKEN + "&appid="+appid+"&secret="+secret;
			HttpRespons resp = HttpRequester.sendPost(urlString, connTimeout, readTimeout);
			logger.info("getAccessToken resp text 【{}】",resp.getContent());
			JSONObject content = JSONObject.fromObject(resp.getContent());
			accessToken = getString(content,"access_token");
			expiresIn = getString(content,"expires_in");
		} catch (Exception e) {
			logger.error("getAccessToken {}",e.getMessage());
		}
		ret.put("accessToken", accessToken);
        ret.put("expiresIn", expiresIn);
		return ret;
	}
	
	/**
	 * 获取微信jsapiTicket
	 * 
	 * @param accessToken
	 * @param connTimeout
	 * @param readTimeout
	 * @return
	 * @throws Exception
	 */
	public static Map<String, String> getJsapiTicket(String accessToken,int connTimeout,int readTimeout) throws Exception {
		if(logger.isDebugEnabled()){
			logger.info("getAccessToken() accessToken {}",new String[]{accessToken});
		}
		Map<String, String> ret = new HashMap<String, String>();
		String errcode = "";
		String errmsg = "";
		String ticket = "";
		String expiresIn = "";
		try {
			String urlString = Constants.WEIXIN_API.JSAPITICKET + "&access_token="+accessToken;
			HttpRespons resp = HttpRequester.sendPost(urlString, connTimeout, readTimeout);
			logger.info("getJsapiTicket resp text 【{}】",resp.getContent());
			JSONObject content = JSONObject.fromObject(resp.getContent());
			errcode = getString(content,"errcode");
			errmsg = getString(content,"errmsg");
			ticket = getString(content,"ticket");
			expiresIn = getString(content,"expires_in");
		} catch (Exception e) {
			logger.error("getAccessToken {}",e.getMessage());
		}
		ret.put("errcode", errcode);
        ret.put("errmsg", errmsg);
        ret.put("jsapiTicket", ticket);
        ret.put("expiresIn", expiresIn);
		return ret;
	}
	
	/**
	 * 获取微信oauth2AccessToken
	 * 
	 * @param appid
	 * @param secret
	 * @param code
	 * @param connTimeout
	 * @param readTimeout
	 * @return
	 * @throws Exception
	 */
	public static Map<String, String> oauth2AccessToken(String appid, String secret,String code,int connTimeout,int readTimeout) throws Exception {
		if(logger.isDebugEnabled()){
			logger.info("oauth2AccessToken() appid {},secret {},code {}",new String[]{appid,secret,code});
		}
		Map<String, String> ret = new HashMap<String, String>();
		try {
			String urlString = Constants.WEIXIN_API.OAUTH2ACCESSTOKEN + "&appid="+appid+"&secret="+secret+"&code="+code;
			HttpRespons resp = HttpRequester.sendPost(urlString, connTimeout, readTimeout);
			logger.info("oauth2AccessToken resp text 【{}】",resp.getContent());
			JSONObject content = JSONObject.fromObject(resp.getContent());
			ret.put("accessToken", getString(content,"access_token"));
	        ret.put("expiresIn", getString(content,"expires_in"));
	        ret.put("refreshToken", getString(content,"refresh_token"));
	        ret.put("openid", getString(content,"openid"));
	        ret.put("scope", getString(content,"scope"));
		} catch (Exception e) {
			logger.error("oauth2AccessToken {}",e.getMessage());
		}
		return ret;
	}
	
	/**
	 * 微信snsapiUserinfo
	 * 
	 * @param accessToken
	 * @param openid
	 * @param lang 默认简体
	 * @param connTimeout
	 * @param readTimeout
	 * @return
	 * @throws Exception
	 */
	public static Map<String, String> snsapiUserinfo(String accessToken, String openid,String lang,int connTimeout,int readTimeout) throws Exception {
		if(logger.isDebugEnabled()){
			logger.info("snsapiUserinfo() accessToken {},openid {}",new String[]{accessToken,openid});
		}
		Map<String, String> ret = new HashMap<String, String>();
		try {
			if(StringUtil.isEmpty(lang)){
				lang = "zh_CN";//返回国家地区语言版本，zh_CN 简体，zh_TW 繁体，en 英语
			}
			String urlString = Constants.WEIXIN_API.SNSAPIUSERINFO + "access_token="+accessToken+"&openid="+openid+"&lang="+lang;
			HttpRespons resp = HttpRequester.sendPost(urlString, connTimeout, readTimeout);
			logger.info("snsapiUserinfo resp text 【{}】",resp.getContent());
			JSONObject content = JSONObject.fromObject(resp.getContent());
			ret.put("openid", getString(content,"openid"));//用户的唯一标识
	        ret.put("nickname", getString(content,"nickname"));//用户昵称
	        ret.put("sex", getString(content,"sex"));//用户的性别，值为1时是男性，值为2时是女性，值为0时是未知
	        ret.put("province", getString(content,"province"));//用户个人资料填写的省份
	        ret.put("city", getString(content,"city"));//普通用户个人资料填写的城市
	        ret.put("country", getString(content,"country"));//国家，如中国为CN
	        ret.put("headimgurl", getString(content,"headimgurl"));//用户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表640*640正方形头像），用户没有头像时该项为空。若用户更换头像，原有头像URL将失效。
	        ret.put("privilege", getString(content,"privilege"));//用户特权信息，json 数组，如微信沃卡用户为（chinaunicom）
	        ret.put("unionid", getString(content,"unionid"));//只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段。详见：获取用户个人信息（UnionID机制）
		} catch (Exception e) {
			logger.error("snsapiUserinfo {}",e.getMessage());
		}
		return ret;
	}
	
	/**
	 * 微信snsapiUserinfo
	 * 
	 * @param accessToken
	 * @param openid
	 * @param lang 默认简体
	 * @param connTimeout
	 * @param readTimeout
	 * @return
	 * @throws Exception
	 */
	public static Map<String, String> userinfo(String accessToken, String openid,String lang,int connTimeout,int readTimeout) throws Exception {
		if(logger.isDebugEnabled()){
			logger.info("userinfo() accessToken {},openid {}",new String[]{accessToken,openid});
		}
		Map<String, String> ret = new HashMap<String, String>();
		try {
			if(StringUtil.isEmpty(lang)){
				lang = "zh_CN";//返回国家地区语言版本，zh_CN 简体，zh_TW 繁体，en 英语
			}
			String urlString = Constants.WEIXIN_API.USERINFO + "access_token="+accessToken+"&openid="+openid+"&lang="+lang;
			HttpRespons resp = HttpRequester.sendPost(urlString, connTimeout, readTimeout);
			logger.info("userinfo resp text 【{}】",resp.getContent());
			JSONObject content = JSONObject.fromObject(resp.getContent());
			ret.put("subscribe", getString(content,"subscribe"));//用户是否订阅该公众号标识，值为0时，代表此用户没有关注该公众号，拉取不到其余信息。
			ret.put("openid", getString(content,"openid"));//用户的唯一标识
	        ret.put("nickname", getString(content,"nickname"));//用户昵称
	        ret.put("sex", getString(content,"sex"));//用户的性别，值为1时是男性，值为2时是女性，值为0时是未知
	        ret.put("province", getString(content,"province"));//用户个人资料填写的省份
	        ret.put("city", getString(content,"city"));//普通用户个人资料填写的城市
	        ret.put("country", getString(content,"country"));//国家，如中国为CN
	        ret.put("language", getString(content,"language"));//用户的语言，简体中文为zh_CN
	        ret.put("headimgurl", getString(content,"headimgurl"));//用户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表640*640正方形头像），用户没有头像时该项为空。若用户更换头像，原有头像URL将失效。
	        ret.put("subscribeTime", getString(content,"subscribe_time"));//用户关注时间，为时间戳。如果用户曾多次关注，则取最后关注时间
	        ret.put("unionid", getString(content,"unionid"));//只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段。详见：获取用户个人信息（UnionID机制）
		} catch (Exception e) {
			logger.error("userinfo {}",e.getMessage());
		}
		return ret;
	}
	
	/**
	 * 微信分享签名
	 * 
	 * @param jsapiTicket
	 * @param url
	 * @param nonceStr
	 * @param timestamp
	 * @return
	 */
    public static Map<String, String> sign(String jsapiTicket, String url,String nonceStr,String timestamp) {
        Map<String, String> ret = new HashMap<String, String>();
        String string1;
        String signature = "";
        
        //注意这里参数名必须全部小写，且必须有序
        string1 = "jsapi_ticket=" + jsapiTicket +
                  "&noncestr=" + nonceStr +
                  "&timestamp=" + timestamp +
                  "&url=" + url;
        logger.info("WeiXinUtil.sign【{}】",string1);
        try{
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(string1.getBytes("UTF-8"));
            signature = byteToHex(crypt.digest());
        }catch (NoSuchAlgorithmException e){
            e.printStackTrace();
            logger.error("sign {}",e.getMessage());
        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
            logger.error("sign {}",e.getMessage());
        }
        ret.put("url", url);
        ret.put("jsapiTicket", jsapiTicket);
        ret.put("nonceStr", nonceStr);
        ret.put("timestamp", timestamp);
        ret.put("signature", signature);
        return ret;
    }
    
    private static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash){
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }
    
    public static String create_nonce_str() {
        return UUID.randomUUID().toString();
    }
    
    public static String create_timestamp() {
        return Long.toString(System.currentTimeMillis() / 1000);
    }
    
    private static String getString(JSONObject content,String key){
    	String value = "";
    	try {
    		value = content.getString(key);
		} catch (Exception e) {
		}
    	return value;
    }
    
    public static void print(Map<String, String> content){
    	for (@SuppressWarnings("rawtypes") Map.Entry entry : content.entrySet()) {
            System.out.println(entry.getKey() + ", " + entry.getValue());
        }
    }
    
    public static void main(String[] args) {
    	try {
    		/*int connTimeout = 30000;
        	int readTimeout = 30000;
        	String appid = "wxb96df2abc2d09980";
        	String secret = "a1ae48b1f63b6d1231656bddeda31985";
        	
        	test getAccessToken
        	Map<String, String> accessTokenMap = getAccessToken(appid, secret, connTimeout, readTimeout);
        	print(accessTokenMap);
        	String accessToken = accessTokenMap.get("accessToken");
        	System.out.println("####################################");
        	
        	test getJsapiTicket
        	Map<String, String> jsapiTicketMap = getJsapiTicket(accessToken, connTimeout, readTimeout);
        	print(jsapiTicketMap);
        	String jsapiTicket = jsapiTicketMap.get("jsapiTicket");
        	System.out.println("####################################");
        	
        	test sign
        	String url = "http://omstest.vmall.com:23568/thirdparty/wechat/vcode/gotoshare?quantity=1&batchName=MATE7";
//        	String jsapiTicket = "bxLdikRXVbTPdHSM05e5u4RbEYQn7pNQMPrfzl8lJNb1foLDa3HIwI3BRMkQmSO_5F64VFa75uURcq6Uz7QHgA";
            String nonce_str = create_nonce_str();
            String timestamp = create_timestamp();
//            String nonce_str = "82693e11-b9bc-448e-892f-f5289f46cd0f";
//            String timestamp = "1419835025";
            Map<String, String> signMap = sign(jsapiTicket, url, nonce_str, timestamp);
        	print(signMap);*/
    		
//    		Map<String, String> tokenMap = getAccessToken("wxb96df2abc2d09980", "a1ae48b1f63b6d1231656bddeda31985", 3000, 3000);
//    		String token = tokenMap.get("accessToken");
//    		System.out.println(token);
//    		Map<String, String> ret = userinfo(token,"owrJds9CIq1V_5SG3xtz8Zrx6u70","",3000,3000);
//    		System.out.println(ret.toString());
    		
    		//011a713603a27ec3c293693b585f4eb0
    		String appid = "wx8d49b7e9412d1b6d";
    		String secret = "7e13fc0940eb088d4ebce1a358497a46";
    		String code = "011a3b4ee30f47a2f2d7041a926b148n";
    		Map<String, String> oauthMap = oauth2AccessToken(appid, secret, code, 3000, 3000);
    		
    		String accessToken = oauthMap.get("access_token"); 
    		String openid = oauthMap.get("openid"); 
    		Map<String, String> userInfo = snsapiUserinfo(accessToken,openid,"",3000,3000);
    		System.out.println(userInfo.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
}
