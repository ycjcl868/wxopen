package com.wx.open.common.base;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.wx.open.common.exception.JSONException;
import com.wx.open.common.util.TypeUtils;

/**
 * <pre>
 *  JSON转换工具
 * </pre>
 *
 * @author jiejie.liao
 * @create 2014.10.24
 * @modify
 * @since JDK1.6
 */
public class JSON {
	
	public final static String toJSONString(Object object){
		try {
			if(object instanceof List || object.getClass().isArray()){
				JSONArray jsonArray = JSONArray.fromObject(object);
				return jsonArray.toString();
			}
			JSONObject json = JSONObject.fromObject(object);
			return json.toString();  
		} catch (Exception e) {
			e.printStackTrace();
			throw new JSONException(e.getMessage());
		} finally {
			
		}
	}
	
	public final static Object parse(String text) {
		if (text == null) {
			return null;
		}
		try {
		 	return JSONObject.fromObject(text);
		} catch (Exception e) {
			throw new JSONException(e.getMessage());
		}
	}
	
	@SuppressWarnings("unchecked")
	public final static MapObject parseMapObject(String text){
		Object value = parse(text);
		if (value==null){
			return null;
		}
		return new MapObject((Map<String,Object>) value);
	}
	
	@SuppressWarnings("unchecked")
	public final static ListObject parseListObject(String text){
		Object value = parse(text);
		if (value==null){
			return null;
		}
		return new ListObject((List<Object>) value);
	}
	
	@SuppressWarnings("unchecked")
	public final static <T> T parseObject(String text, Class<T> clazz) {
		Object object = parse(text);
		if (object == null) {
			return null;
		}
		Map<String, Object> map = (Map<String, Object>) object;
		return TypeUtils.castToJavaBean(map, clazz);
	}
}
