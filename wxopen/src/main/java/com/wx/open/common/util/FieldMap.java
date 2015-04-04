package com.wx.open.common.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *  字段集合
 * </pre>
 *
 * @author jiejie.liao
 * @create 2014.10.24
 * @modify
 * @since JDK1.6
 */
public class FieldMap {
	public static List<FieldInfo> getFields(Class<?> clazz) {
		return computeGetters(clazz);
	}

	private static Method getMethod(Class<?> clazz, String methodName, Class<?>... paramType) {
		try {
			return clazz.getDeclaredMethod(methodName, paramType);
		} catch (Exception e) {
			return null;
		}
	}

	private static List<FieldInfo> computeGetters(Class<?> clazz) {
		List<FieldInfo> list = new ArrayList<FieldInfo>();

		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			String propertyName = field.getName();
			if (Modifier.isStatic(field.getModifiers())) {
				continue;
			}
			if (Modifier.isTransient(field.getModifiers())) {
				continue;
			}

			String setterName = "set" + Character.toUpperCase(propertyName.charAt(0)) + propertyName.substring(1);
			Method setterMethod = getMethod(clazz, setterName, field.getType());
			if (setterMethod == null) {
				continue;
			}
			if (!Modifier.isPublic(setterMethod.getModifiers())){
				continue;
			}
			
			String getterName = "";
			if (field.getType() == Boolean.class || field.getType()==boolean.class) {
				getterName = "is" + Character.toUpperCase(propertyName.charAt(0)) + propertyName.substring(1);
			} else {
				getterName = "get" + Character.toUpperCase(propertyName.charAt(0)) + propertyName.substring(1);
			}
			
			Method getterMethod = getMethod(clazz, getterName);
			if (getterMethod == null) {
				continue;
			}
			if (!Modifier.isPublic(getterMethod.getModifiers())){
				continue;
			}

			FieldInfo fieldInfo = new FieldInfo(field.getName(), field, setterMethod, getterMethod);
			list.add(fieldInfo);
		}
		return list;
	}
}
