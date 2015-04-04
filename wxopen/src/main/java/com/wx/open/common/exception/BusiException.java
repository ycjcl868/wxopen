package com.wx.open.common.exception;

import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 *  业务异常类
 * </pre>
 *
 * @author jiejie.liao
 * @create 2014.10.24
 * @modify
 * @since JDK1.6
 */
public class BusiException extends RuntimeException {
	private static final long serialVersionUID = -6677057137691957308L;
	
	private String respCode;//0000 表示正常、9999 系统异常
	private String respDesc;
	private Map<String, String> map = null;
	
	public BusiException(String respDesc){
		this.respCode = "9999";
		this.respDesc = respDesc;
	}
	
	public BusiException(String respCode,String respDesc){
		this.respCode = respCode;
		this.respDesc = respDesc;
	}
	
	public String getRespCode() {
		return respCode;
	}

	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}

	public String getRespDesc() {
		return respDesc;
	}

	public void setRespDesc(String respDesc) {
		this.respDesc = respDesc;
	}
	
	public String getMessage(){
		StringBuilder builder = new StringBuilder();
		builder.append(respDesc).append(" (").append(respCode).append(")");
		if (map!=null){
			for (String key : map.keySet()){
				builder.append("\n\t").append(key).append(" = ").append(map.get(key));
			}
		}
		return builder.toString();
	}
	
	public BusiException set(String key, Object value) {
		if (key == null || value == null) {
			return this;
		}
		if (map == null) {
			map = new HashMap<String, String>();
		}
		map.put(key, value.toString());
		return this;
	}
	
	public static BusiException wrap(Throwable exception) {
		if (exception instanceof BusiException) {
			BusiException se = (BusiException) exception;
			return se;
		} else {
			return new BusiException(exception.getMessage());
		}
	}
}
