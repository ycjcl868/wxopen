package com.wx.open.controller.event;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wx.open.common.base.MapObject;
import com.wx.open.common.exception.BusiException;
import com.wx.open.controller.frame.ServiceAutowired;
import com.wx.open.controller.frame.ServiceInterfaceAnno;
import com.wx.open.controller.frame.ServiceResult;
import com.wx.open.service.IWeiXinOpenService;

/**
 * <pre>
 *  微信公众平台
 * </pre>
 *
 * @author jiejie.liao
 * @create 2015.01.20
 * @modify
 * @since JDK1.6
 */
public class WeiXinOpenAction {
	private static Logger logger = LoggerFactory.getLogger(WeiXinOpenAction.class);
	
	@ServiceAutowired("weiXinOpenService")
	private IWeiXinOpenService weiXinOpenService;
	
	/**
	 * 微信JS-SDK配置
	 * 
	 * @param parameters
	 * @return
	 */
	@ServiceInterfaceAnno(value = "weiXinOpen.config", desc = "微信分享配置")
	public ServiceResult config(MapObject parameters){
		logger.info("Enter the method config()");
		ServiceResult rst = new ServiceResult();
		try {
			String appid = parameters.getString("appid");
			String url = parameters.getString("url");
			Map<String, String> ret = weiXinOpenService.sign(appid,url);
			rst.setValue(ret);
		} catch (Exception e) {
			logger.error("config err {}", e.getMessage(),e);
			throw BusiException.wrap(e);
		}
		logger.info("Exit the method config()");
		return rst;
	}
	
	/**
	 * 微信网页授权获取用户基本信息
	 * 
	 * @param parameters
	 * @return
	 */
	@ServiceInterfaceAnno(value = "weiXinOpen.snsapiBase", desc = "网页授权获取用户基本信息")
	public ServiceResult snsapiBase(MapObject parameters){
		logger.info("Enter the method snsapiBase()");
		ServiceResult rst = new ServiceResult();
		try {
			String appid = parameters.getString("appid");
			String code = parameters.getString("code");
			Map<String, String> ret = weiXinOpenService.snsapiBase(appid, code);
			rst.setValue(ret);
		} catch (Exception e) {
			logger.error("snsapiBase err {}", e.getMessage(),e);
			throw BusiException.wrap(e);
		}
		logger.info("Exit the method snsapiBase()");
		return rst;
	}
	
	/**
	 * 微信网页授权获取用户基本信息
	 * 
	 * @param parameters
	 * @return
	 */
	@ServiceInterfaceAnno(value = "weiXinOpen.snsapiUserinfo", desc = "网页授权获取用户基本信息")
	public ServiceResult snsapiUserinfo(MapObject parameters){
		logger.info("Enter the method snsapiUserinfo()");
		ServiceResult rst = new ServiceResult();
		try {
			String appid = parameters.getString("appid");
			String code = parameters.getString("code");
			Map<String, String> ret = weiXinOpenService.snsapiUserinfo(appid, code);
			rst.setValue(ret);
		} catch (Exception e) {
			logger.error("snsapiUserinfo err {}", e.getMessage(),e);
			throw BusiException.wrap(e);
		}
		logger.info("Exit the method snsapiUserinfo()");
		return rst;
	}
	
	/**
	 * 获取用户基本信息
	 * 
	 * @param parameters
	 * @return
	 */
	@ServiceInterfaceAnno(value = "weiXinOpen.userinfo", desc = "获取用户基本信息")
	public ServiceResult userinfo(MapObject parameters){
		logger.info("Enter the method userinfo()");
		ServiceResult rst = new ServiceResult();
		try {
			String appid = parameters.getString("appid");
			String openid = parameters.getString("openid");
			Map<String, String> ret = weiXinOpenService.userinfo(appid, openid);
			rst.setValue(ret);
		} catch (Exception e) {
			logger.error("userinfo err {}", e.getMessage(),e);
			throw BusiException.wrap(e);
		}
		logger.info("Exit the method userinfo()");
		return rst;
	}
	
}
