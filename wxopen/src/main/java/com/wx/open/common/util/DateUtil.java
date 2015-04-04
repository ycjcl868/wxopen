package com.wx.open.common.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期工具类
 * 
 * @author jiejie.liao
 *
 */
public class DateUtil {
	public static int DAY_HOUR = 24;
	public static String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
	public static String YYYY_MM_DD = "yyyy-MM-dd";
	public static String YYYYMMDD = "yyyyMMdd";
	public static String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
	public static String YYYYMMDDHHMMSS1 = "yyyyMMdd HHmmss";
	
	/**
	 * 校验格式是否正确
	 * @param dateStr
	 * @param pattern
	 * @author jiejie.liao
	 * @return
	 */
	public static boolean isPatternErr(String dateStr, String pattern) {
		try {
			Date date = string2Date(dateStr,pattern);
			if(date == null){
				return true;
			}
			return false;
		} catch (Exception e) {
			return true;
		}
	}
	
	/**
	 * 获取系统当前时间 YYYYMMDDHHMMSS
	 * @author jiejie.liao
	 * @return
	 */
	public static String getCurrentTime(){
	    Calendar cl = Calendar.getInstance();
	    return date2String(cl.getTime(),YYYYMMDDHHMMSS);
	}
	
	/**
	 * 获取系统当前时间  yyyyMMdd HHmmss
	 * @author jiejie.liao
	 * @return
	 */
	public static String getCurrentTime1(){
        Calendar cl = Calendar.getInstance();
        return date2String(cl.getTime(),YYYYMMDDHHMMSS1);
    }
	
	/**
	 * 将时间字符串从一格式转换成另一格式
	 * @param dateStr
	 * @param formatFrom
	 * @param formatTo
	 * @author jiejie.liao
	 * @return
	 */
	public static String format(String dateStr,String formatFrom,String formatTo){
		try {
			if(dateStr.charAt(0)=='0'){
				return "";
			}
			Date date = string2Date(dateStr, formatFrom);
			return date2String(date, formatTo);
		} catch (Exception e) {
			return dateStr == null ? "" : dateStr;
		}
	}
	
	/**
	 * 将Date按某一格式转化成String
	 * 
	 * @param date Date对象
	 * @param pattern 日期类型(见上面定义)
	 * @author jiejie.liao
	 * @return
	 */
	public static String date2String(Date date, String pattern) {
		pattern = pattern == null ? YYYY_MM_DD_HH_MM_SS : pattern;
		return new SimpleDateFormat(pattern).format(date);
	}
	
	/**
	 * 将String类型转换成Date类型
	 * 
	 * @param date Date对象
	 * @author jiejie.liao
	 * @return
	 */
	public static Date string2Date(String date,String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		try {
			return sdf.parse(date);
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * 获取多少天后的时间
	 * 
	 * @param date Date对象
	 * @author jiejie.liao
	 * @return
	 */
	public static Date formatTimeToNextDate(Date time,int day){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(time);
		calendar.add(Calendar.DATE, day);
		return calendar.getTime();
	}
	
	/**
	 * 获取多少天后的时间,yyyyMMdd
	 * 
	 * @param date Date对象
	 * @author jiejie.liao
	 * @return
	 */
	public static String formatTimeToNextDate(String time,int day){
		Date date = string2Date(time,YYYYMMDD);
		Date date2 = formatTimeToNextDate(date,day);
		String rstDate = date2String(date2, YYYYMMDD);
		return rstDate;
	}
	
	/**
	 * 获取多少秒后的时间,YYYYMMDDHHMMSS
	 * 
	 * @param time YYYYMMDDHHMMSS
	 * @param s 秒
	 * @author jiejie.liao
	 * @return
	 */
	public static Date getNextDate(String time,int s){
		Date date = string2Date(time,YYYYMMDDHHMMSS);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.SECOND, s);
		return calendar.getTime();
	}
	
	/**
	 * 将当前时间转换成m月
	 * @author jiejie.liao
	 * @param time
	 * @param m
	 * @return
	 */
	public static String formatTimeToNextMonth(Date time,int m){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(time);
		calendar.add(Calendar.MONTH, m);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		String date = date2String(calendar.getTime(),YYYYMMDDHHMMSS);
		return date;
	}
	
	/**
	 * 获取当前日期，YYYYMMDD
	 * 
	 * @author jiejie.liao
	 * @return
	 */
	public static String getCurrentDate(){
		return date2String(new Date(), DateUtil.YYYYMMDD);
	}
	
	/**
	 * 获取当前月
	 * @author jiejie.liao
	 * @param time
	 * @return
	 */
	public static int getCurMonth(Date time){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(time);
		int m = calendar.get(Calendar.DAY_OF_MONTH);
		return m;
	}
	
	/**
	 * 获取当前小时
	 * @author jiejie.liao
	 * @param time
	 * @return
	 */
	public static int getCurHour(Date time){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(time);
		int h = calendar.get(Calendar.HOUR_OF_DAY);
		return h;
	}
	
	/**
	 * 获取剩余小时
	 * @author jiejie.liao
	 * @param time
	 * @return
	 */
	public static int getResHour(Date time){
		return DAY_HOUR - getCurHour(time);
	}
	
	/**
	 * 比较当前时间是否大于等于起始时间、小于等于结束时间
	 * 
	 * @param startDateStr
	 * @param endDateStr
	 * @param date
	 * @return
	 * @throws Exception
	 */
	public static boolean compareTo(String startDateStr,String endDateStr,String date)throws Exception{
		try {
			if(compareTo(startDateStr,date) > 0){
				return false;
			}
			if(compareTo(endDateStr,date) < 0){
				return false;
			}
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	
	/**
	 * 比较实际大小
	 * 
	 * @param curDate YYYYMMddHHmmss
	 * @param anotherDate YYYYMMddHHmmss
	 * @return  0:curDate = anotherDate;1:curDate > anotherDate;-1:curDate < anotherDate
	 */
	public static int compareTo(String curDate,String anotherDate){
		try {
			if(isPatternErr(curDate, YYYYMMDDHHMMSS)){
				return -1;
			}
			if(isPatternErr(anotherDate, YYYYMMDDHHMMSS)){
				return -1;
			}
			return compareTo(string2Date(curDate, YYYYMMDDHHMMSS), string2Date(anotherDate, YYYYMMDDHHMMSS));
		} catch (Exception e) {
			return -1;
		}
	}
	
	/**
	 * 比较时间大小
	 * 
	 * @param curDate 当前时间
	 * @param anotherDate 相比较的另一个时间
	 * @return 0:curDate = anotherDate;1:curDate > anotherDate;-1:curDate < anotherDate
	 */
	public static int compareTo(Date curDate,Date anotherDate){
		try {
			Calendar cal1 = Calendar.getInstance();
			cal1.setTime(curDate); 
			
			Calendar cal2 = Calendar.getInstance(); 
			cal2.setTime(anotherDate);
			
			return cal1.compareTo(cal2);
		} catch (Exception e) {
			return -1;
		}
	}
	
}
