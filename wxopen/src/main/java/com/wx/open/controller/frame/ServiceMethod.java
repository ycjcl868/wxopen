package com.wx.open.controller.frame;

import java.lang.reflect.Method;

/**
 * <pre>
 *  方法对象封装
 * </pre>
 *
 * @author jiejie.liao
 * @create 2014.10.24
 * @modify
 * @since JDK1.6
 */
public class ServiceMethod {
	private String code;
	private Object inst;
	private Method method;
	
	public ServiceMethod(Object inst, Method method,String code) {
		this.code = code;
		this.inst = inst;
		this.method = method;
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Object getInst() {
		return inst;
	}

	public void setInst(Object inst) {
		this.inst = inst;
	}

	public Method getMethod() {
		return method;
	}

	public void setMethod(Method method) {
		this.method = method;
	}
	
}
