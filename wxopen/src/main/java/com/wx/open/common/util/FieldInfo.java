package com.wx.open.common.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import com.wx.open.common.annotation.JSONField;

/**
 * <pre>
 *  字段信息
 * </pre>
 *
 * @author jiejie.liao
 * @create 2014.10.24
 * @modify
 * @since JDK1.6
 */
public class FieldInfo {
	private String name;
	private Method setterMethod;
	private Method getterMethod;
	private Type fieldType;
	private boolean serialize=true;
	private boolean deserialize=true;
	private String format=null;
	public FieldInfo(String name, Field field, Method setterMethod, Method getterMethod) {
		this.name = name;
		this.setterMethod = setterMethod;
		this.getterMethod = getterMethod;
		this.fieldType = setterMethod.getGenericParameterTypes()[0];

		JSONField fieldAnno = field.getAnnotation(JSONField.class);
		if (fieldAnno != null) {
			if (fieldAnno.name() != null) {
				this.name = fieldAnno.name();
			}
			if (fieldAnno.uppercase()){
				this.name = this.name.toUpperCase();
			}
			if (fieldAnno.lowercase()){
				this.name=this.name.toLowerCase();
			}
			serialize = fieldAnno.serialize();
			deserialize = fieldAnno.deserialize();
			
			if (fieldAnno.format() != null && !"".equals(fieldAnno.format())) {
				format =fieldAnno.format();
			}
		}
	}

	public String getName() {
		return name;
	}
	
	public boolean isSerialize(){
		return serialize;
	}
	public boolean isDeserialize(){
		return deserialize;
	}
	
	private String getFormat() {
		return format;
	}

	public Object getPropertyValue(Object obj) throws Exception {
		Object value =getterMethod.invoke(obj);
		if (value==null){
			return null;
		}
		
		if (fieldType==Date.class){
			Date date = (Date) value;
			String fmt = getFormat();
			fmt = (fmt!=null)?fmt:Feature.DateFormat;
			SimpleDateFormat dformat = new SimpleDateFormat(fmt);
			return dformat.format(date);
		}
		
		return value;
	}

	public void setPropertyValue(Object obj, Object value) throws Exception {
		if (value==null){
			setterMethod.invoke(obj, value);
			return;
		}
		if (fieldType==Object.class){
			setterMethod.invoke(obj, value);
			return;
		}
		
		if (fieldType==Date.class && value.getClass()==String.class){
			String fmt = getFormat();
			fmt = (fmt!=null)?fmt:Feature.DateFormat;
			SimpleDateFormat dformat = new SimpleDateFormat(fmt);
			Date date = dformat.parse((String) value);
			setterMethod.invoke(obj, date);
			return;
		}
		
		value = TypeUtils.cast(value, fieldType);		
		setterMethod.invoke(obj, value);
	}

	public Object getValueFromMap(Map<String, Object> map) {
		String key = name;
		Object value = map.get(key);
		if (value == null) {
			value = map.get(key.toUpperCase());
		}
		if (value == null) {
			value = map.get(key.toLowerCase());
		}
		return value;
	}
}
