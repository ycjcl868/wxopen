package com.wx.open.common.base;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import com.wx.open.common.util.TypeUtils;

/**
 * <pre>
 *  List集合对象
 * </pre>
 *
 * @author jiejie.liao
 * @create 2014.10.24
 * @modify
 * @since JDK1.6
 */
public class ListObject {
	private final List<Object> list;
	
	public ListObject(){
		this.list = new ArrayList<Object>();
	}
	
	public ListObject(List<Object> list) {
		this.list = list;
	}

	public int size() {
		return list.size();
	}

	public boolean isEmpty() {
		return list.isEmpty();
	}

	public Object[] toArray() {
		return list.toArray();
	}

	public <T> T[] toArray(T[] a) {
		return list.toArray(a);
	}

	public boolean add(Object e) {
		return list.add(e);
	}

	public boolean remove(Object o) {
		return list.remove(o);
	}

	public void clear() {
		list.clear();
	}

	public Object set(int index, Object element) {
		return list.set(index, element);
	}

	public void add(int index, Object element) {
		list.add(index, element);
	}

	public Object remove(int index) {
		return list.remove(index);
	}

	public int indexOf(Object o) {
		return list.indexOf(o);
	}

	public int lastIndexOf(Object o) {
		return list.lastIndexOf(o);
	}

	public ListIterator<Object> listIterator() {
		return list.listIterator();
	}

	public ListIterator<Object> listIterator(int index) {
		return list.listIterator(index);
	}

	public List<Object> subList(int fromIndex, int toIndex) {
		return list.subList(fromIndex, toIndex);
	}

	public Object get(int index) {
		return list.get(index);
	}

	public <T> T getObject(int index, Class<T> clazz) {
		Object obj = list.get(index);
		return TypeUtils.castToJavaBean(obj, clazz);
	}

	public Boolean getBoolean(int index) {
		Object value = get(index);

		if (value == null) {
			return Boolean.FALSE;
		}

		return TypeUtils.castToBoolean(value);
	}

	public boolean getBooleanValue(int index) {
		Object value = get(index);

		if (value == null) {
			return false;
		}

		return TypeUtils.castToBoolean(value).booleanValue();
	}

	public Byte getByte(int index) {
		Object value = get(index);

		return TypeUtils.castToByte(value);
	}

	public byte getByteValue(int index) {
		Object value = get(index);

		if (value == null) {
			return 0;
		}

		return TypeUtils.castToByte(value).byteValue();
	}

	public Short getShort(int index) {
		Object value = get(index);

		return TypeUtils.castToShort(value);
	}

	public short getShortValue(int index) {
		Object value = get(index);

		if (value == null) {
			return 0;
		}

		return TypeUtils.castToShort(value).shortValue();
	}

	public Integer getInteger(int index) {
		Object value = get(index);

		return TypeUtils.castToInt(value);
	}

	public int getIntValue(int index) {
		Object value = get(index);

		if (value == null) {
			return 0;
		}

		return TypeUtils.castToInt(value).intValue();
	}

	public Long getLong(int index) {
		Object value = get(index);

		return TypeUtils.castToLong(value);
	}

	public long getLongValue(int index) {
		Object value = get(index);

		if (value == null) {
			return 0L;
		}

		return TypeUtils.castToLong(value).longValue();
	}

	public Float getFloat(int index) {
		Object value = get(index);

		return TypeUtils.castToFloat(value);
	}

	public float getFloatValue(int index) {
		Object value = get(index);

		if (value == null) {
			return 0F;
		}

		return TypeUtils.castToFloat(value).floatValue();
	}

	public Double getDouble(int index) {
		Object value = get(index);

		return TypeUtils.castToDouble(value);
	}

	public double getDoubleValue(int index) {
		Object value = get(index);

		if (value == null) {
			return 0D;
		}

		return TypeUtils.castToDouble(value).floatValue();
	}

	public BigDecimal getBigDecimal(int index) {
		Object value = get(index);

		return TypeUtils.castToBigDecimal(value);
	}

	public BigInteger getBigInteger(int index) {
		Object value = get(index);

		return TypeUtils.castToBigInteger(value);
	}

	public String getString(int index) {
		Object value = get(index);

		return TypeUtils.castToString(value);
	}

	public java.util.Date getDate(int index) {
		Object value = get(index);

		return TypeUtils.castToDate(value);
	}

	public java.sql.Date getSqlDate(int index) {
		Object value = get(index);

		return TypeUtils.castToSqlDate(value);
	}

	public java.sql.Timestamp getTimestamp(int index) {
		Object value = get(index);
		return TypeUtils.castToTimestamp(value);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String,Object> getMap(int index){
		Object value = get(index);
		if (value==null){
			return null;
		}
		return (Map<String,Object>)value;
	}
	@SuppressWarnings("unchecked")
	public MapObject getMapObject(int index){
		Object value = get(index);
		if (value==null){
			return null;
		}
		return  new MapObject((Map<String,Object>)value);
	}
	public List<Object> getList(){
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public List<Object> getList(int index){
		Object value = get(index);
		if (value==null){
			return null;
		}
		return (List<Object>)value;
	}
	
	@SuppressWarnings("unchecked")
	public ListObject getListObject(int index){
		Object value = get(index);
		if (value==null){
			return null;
		}
		return new ListObject((List<Object>)value);
	}
	
	public <T> List<T> toList(Class<T> clazz){
		List<T> r = new ArrayList<T>(list.size());
		for (Object object : list){
			r.add(TypeUtils.castToJavaBean(object, clazz));
		}
		return r;
	}
}
