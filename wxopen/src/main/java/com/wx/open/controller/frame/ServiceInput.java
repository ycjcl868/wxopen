package com.wx.open.controller.frame;

import java.util.Map;

import com.wx.open.common.base.JSON;
import com.wx.open.common.base.MapObject;
import com.wx.open.common.exception.BusiException;

/**
 * <pre>
 *  业务请求类
 * </pre>
 *
 * @author jiejie.liao
 * @create 2014.10.24
 * @modify
 * @since JDK1.6
 */
public class ServiceInput {
	
	private MapObject busiParam;
	private String interfaceCode;
	private Boolean jsonp;
	private String callbackName;
	
	public void decode(String text) throws Exception{
		busiParam = JSON.parseMapObject(text);
		if(busiParam == null){
			throw new BusiException("interfaceCode is null");
		}
		interfaceCode = busiParam.getString("interfaceCode");//必须有值
		jsonp = busiParam.getBoolean("jsonp");
		callbackName = busiParam.getString("callbackName");
	}
	
	public void decode(Map<String, Object> text) throws Exception{
		busiParam = new MapObject(text);
		if(busiParam == null){
			throw new BusiException("interfaceCode is null");
		}
		interfaceCode = busiParam.getString("interfaceCode");//必须有值
		jsonp = busiParam.getBoolean("jsonp");
		callbackName = busiParam.getString("callbackName");
	}
	
	public MapObject getBusiParam() {
		return busiParam;
	}
	
	public void setBusiParam(MapObject busiParam) {
		this.busiParam = busiParam;
	}
	
	public String getInterfaceCode() {
		return interfaceCode;
	}
	
	public void setInterfaceCode(String interfaceCode) {
		this.interfaceCode = interfaceCode;
	}
	
	public Boolean getJsonp() {
		return jsonp;
	}
	
	public void setJsonp(Boolean jsonp) {
		this.jsonp = jsonp;
	}
	
	public String getCallbackName() {
		return callbackName;
	}
	
	public void setCallbackName(String callbackName) {
		this.callbackName = callbackName;
	}
	
}
