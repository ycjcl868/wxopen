package com.wx.open.controller.frame;

import java.util.HashMap;
import java.util.Map;

import com.wx.open.common.base.JSON;
import com.wx.open.common.util.StringUtil;

/**
 * <pre>
 *  业务结果类
 * </pre>
 *
 * @author jiejie.liao
 * @create 2014.10.24
 * @modify
 * @since JDK1.6
 */
public class ServiceResult{
	private String result = "";
	private String message = "";
	private Object value = null;
	
	public String getResult() {
		return result;
	}
	
	public void setResult(String result) {
		this.result = result;
	}

	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public void setValue(Object value){
		this.value = value;
	}
	
	public void setError(String message){
		this.result = "9999";
		this.message = message;
	}
	
	public void setError(String code,String message){
		this.result = code;
		this.message = message;
	}
	
	@SuppressWarnings("unchecked")
	public void setValue(String k, Object v) {
		if (value == null) {
			value = new HashMap<String, Object>();
		}
		if (value instanceof Map) {
			((Map<String, Object>) value).put(k, v);
		}
	}
	
	public final String toJSONString() {
		Map<String, Object> map = new HashMap<String, Object>(3);
		String code = ("0000" + getResult()).substring(getResult().length());
		if(StringUtil.isNotNumeric(code)){
			code = "9999";
		}
		map.put("code", code);
		map.put("message", (getMessage() != null) ? getMessage() : "");
		map.put("data", (value!=null)?value: (new HashMap<String,Object>(1)));
		return JSON.toJSONString(map);
	}
	
	public final String toJSONNpString(String callback) {
		Map<String, Object> map = new HashMap<String, Object>(3);
		String code = ("0000" + getResult()).substring(getResult().length());
		if(StringUtil.isNotNumeric(code)){
			code = "9999";
		}
		map.put("code", code);
		map.put("message", (getMessage() != null) ? getMessage() : "");
		map.put("data", (value!=null)?value: (new HashMap<String,Object>(1)));
		return callback + "( "+JSON.toJSONString(map)+" )";
	}
	
	public final Map<String, Object> toJSONMap() {
		Map<String, Object> map = new HashMap<String, Object>(3);
		String code = ("0000" + getResult()).substring(getResult().length());
		if(StringUtil.isNotNumeric(code)){
			code = "9999";
		}
		map.put("code", code);
		map.put("message", (getMessage() != null) ? getMessage() : "");
		map.put("data", (value!=null)?value: (new HashMap<String,Object>(1)));
		return map;
	}
}
