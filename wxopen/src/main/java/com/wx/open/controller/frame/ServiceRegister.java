package com.wx.open.controller.frame;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;

import com.wx.open.common.exception.BusiException;

/**
 * <pre>
 *  业务方法注册类
 * </pre>
 *
 * @author jiejie.liao
 * @create 2014.10.24
 * @modify
 * @since JDK1.6
 */
public class ServiceRegister {
	private static Map<String, ServiceMethod> serviceMap = new HashMap<String, ServiceMethod>();
	
	public static <T> void register(Class<?> clazz) {
		Object inst = null;
		try {
			inst = clazz.newInstance();
		} catch (Exception e) {
			throw BusiException.wrap(e).set("clazz", clazz.getName());
		}
		
		Method[] methods = clazz.getDeclaredMethods();
		ServiceInterfaceAnno serviceAnno = null;
		String code = "";
		for (Method method : methods) {
			serviceAnno = method.getAnnotation(ServiceInterfaceAnno.class);
			if (serviceAnno == null) {
				continue;
			}
			code = serviceAnno.value();
			if (serviceMap.containsKey(code)) {
				throw new BusiException("接口重复定义").set("code", code).set("clazz", clazz.getName()).set("method", method.getName());
			}
			serviceMap.put(code, new ServiceMethod(inst, method, code));
		}
	}
	
	public static <T> void register(Class<?> clazz,ApplicationContext applicationContext) throws BeansException, IllegalArgumentException, IllegalAccessException {
		Object inst = null;
		try {
			inst = clazz.newInstance();
		} catch (Exception e) {
			throw BusiException.wrap(e).set("clazz", clazz.getName());
		}
		
		/*操作私有属性，破坏了java的结构*/
		Field[] fields = clazz.getDeclaredFields();
		ServiceAutowired autowired = null;
		String beanClass = "";
		for (Field field : fields) {
			autowired = field.getAnnotation(ServiceAutowired.class);
			if(autowired == null){
				continue;
			}
			beanClass = autowired.value();
			field.setAccessible(true);
			field.set(inst, applicationContext.getBean(beanClass));
		}
		
		Method[] methods = clazz.getDeclaredMethods();
		ServiceInterfaceAnno serviceAnno = null;
		String code = "";
		for (Method method : methods) {
			serviceAnno = method.getAnnotation(ServiceInterfaceAnno.class);
			if (serviceAnno == null) {
				continue;
			}
			code = serviceAnno.value();
			if (serviceMap.containsKey(code)) {
				throw new BusiException("接口重复定义").set("code", code).set("clazz", clazz.getName()).set("method", method.getName());
			}
			serviceMap.put(code, new ServiceMethod(inst, method, code));
		}
	}
	
	public static ServiceMethod getService(String code) {
		return serviceMap.get(code);
	}
}
