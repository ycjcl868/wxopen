package com.wx.open.common.base;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wx.open.common.util.TypeUtils;

/**
 * <pre>
 *  Map集合对象
 * </pre>
 *
 * @author jiejie.liao
 * @create 2014.10.24
 * @modify
 * @since JDK1.6
 */
public class MapObject {
	private final Map<String, Object> map;

	public MapObject() {
		this(new HashMap<String, Object>());
	}
	
	public MapObject(Map<String, Object> map) {
		this.map = map;
	}
	public void put(String key,Object value){
		map.put(key, value);
	}
	public int size() {
		return map.size();
	}

	public boolean isEmpty() {
		return map.isEmpty();
	}

	public boolean containsKey(Object key) {
		return map.containsKey(key);
	}

	public boolean containsValue(Object value) {
		return map.containsValue(value);
	}

	public Object get(Object key) {
		Object v = map.get(key);
		if (v == null) {
			String nkey = String.valueOf(key);
			nkey = nkey.substring(0, 1).toUpperCase() + nkey.substring(1);
			v = map.get(nkey);
		}
		return v;
	}
	
	public <T> T toObject(Class<T> clazz){
		return TypeUtils.castToJavaBean(map, clazz);
	}
	public <T> T getObject(String key, Class<T> clazz) {
		Object obj = map.get(key);
		return TypeUtils.castToJavaBean(obj, clazz);
	}

	public Boolean getBoolean(String key) {
		Object value = get(key);

		if (value == null) {
			return Boolean.FALSE;
		}

		return TypeUtils.castToBoolean(value);
	}

	public boolean getBooleanValue(String key) {
		Object value = get(key);

		if (value == null) {
			return false;
		}

		return TypeUtils.castToBoolean(value).booleanValue();
	}

	public Byte getByte(String key) {
		Object value = get(key);

		return TypeUtils.castToByte(value);
	}

	public byte getByteValue(String key) {
		Object value = get(key);

		if (value == null) {
			return 0;
		}

		return TypeUtils.castToByte(value).byteValue();
	}

	public Short getShort(String key) {
		Object value = get(key);

		return TypeUtils.castToShort(value);
	}

	public short getShortValue(String key) {
		Object value = get(key);

		if (value == null) {
			return 0;
		}

		return TypeUtils.castToShort(value).shortValue();
	}

	public Integer getInteger(String key) {
		Object value = get(key);

		return TypeUtils.castToInt(value);
	}

	public int getIntValue(String key) {
		Object value = get(key);

		if (value == null) {
			return 0;
		}

		return TypeUtils.castToInt(value).intValue();
	}

	public Long getLong(String key) {
		Object value = get(key);

		return TypeUtils.castToLong(value);
	}

	public long getLongValue(String key) {
		Object value = get(key);

		if (value == null) {
			return 0L;
		}

		return TypeUtils.castToLong(value).longValue();
	}

	public Float getFloat(String key) {
		Object value = get(key);

		return TypeUtils.castToFloat(value);
	}

	public float getFloatValue(String key) {
		Object value = get(key);

		if (value == null) {
			return 0F;
		}

		return TypeUtils.castToFloat(value).floatValue();
	}

	public Double getDouble(String key) {
		Object value = get(key);

		return TypeUtils.castToDouble(value);
	}

	public double getDoubleValue(String key) {
		Object value = get(key);

		if (value == null) {
			return 0D;
		}

		return TypeUtils.castToDouble(value).floatValue();
	}

	public BigDecimal getBigDecimal(String key) {
		Object value = get(key);

		return TypeUtils.castToBigDecimal(value);
	}

	public BigInteger getBigInteger(String key) {
		Object value = get(key);

		return TypeUtils.castToBigInteger(value);
	}

	public String getString(String key) {
		Object value = get(key);

		if (value == null) {
			return null;
		}

		return value.toString();
	}

	public Date getDate(String key) {
		Object value = get(key);
		return TypeUtils.castToDate(value);
	}

	public java.sql.Date getSqlDate(String key) {
		Object value = get(key);
		return TypeUtils.castToSqlDate(value);
	}

	public java.sql.Timestamp getTimestamp(String key) {
		Object value = get(key);
		return TypeUtils.castToTimestamp(value);
	}
	public Map<String,Object> getMap(){
		return map;
	}
	@SuppressWarnings("unchecked")
	public Map<String,Object> getMap(String key){
		Object value = get(key);
		if (value==null){
			return null;
		}
		return (Map<String,Object>)value;
	}
	@SuppressWarnings("unchecked")
	public MapObject getMapObject(String key){
		Object value = get(key);
		if (value==null){
			return null;
		}
		return  new MapObject((Map<String,Object>)value);
	}
	
	@SuppressWarnings("unchecked")
	public List<Object> getList(String key){
		Object value = get(key);
		if (value==null){
			return null;
		}
		return (List<Object>)value;
	}
	
	@SuppressWarnings("unchecked")
	public ListObject getListObject(String key){
		Object value = get(key);
		if (value==null){
			return null;
		}
		return new ListObject((List<Object>)value);
	}
	
	public String toJSONString(){
		return JSON.toJSONString(map);
	}
}
