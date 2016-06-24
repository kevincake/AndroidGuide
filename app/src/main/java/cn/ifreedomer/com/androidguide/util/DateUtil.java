package cn.ifreedomer.com.androidguide.util;


import android.text.TextUtils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import cn.ifreedomer.com.androidguide.GuideApplication;
import cn.ifreedomer.com.androidguide.R;


public class DateUtil {
	private static SimpleDateFormat sdf = new SimpleDateFormat();
	public static final String YYMMDDPATTERN = "yyyy-MM-dd";

	public static void main(String[] agr) {
		String t1 = "2013-02-03T16:26:27.459944+08:00";
		String t2 = t1.substring(0, 19);
		t2 = t2.replaceAll("T", " ");
		Date d1 = parseDateFormat(t2, "yyyy-MM-dd HH:mm:ss");

		System.out.println(getDateFot(new Date(), d1));
	}

	/**
	 * 获取UTC时间
	 * 
	 * @return
	 */
	public static String getUTCDate() {
		Date data = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat(
				"yyyy-MM-dd'T'HH:mm:ss.ms00Z");
		String time = sdf.format(data).toString();
		time = time.substring(0, time.length() - 2) + ":"
				+ time.substring(time.length() - 2);
		return time;
	}

	public static String getUTCDate(long timestamp) {
		Timestamp ts = new Timestamp(timestamp);
		Date date = new Date();
		date = ts;
		SimpleDateFormat sdf = new SimpleDateFormat(
				"yyyy-MM-dd'T'HH:mm:ss.ms00Z");
		String time = sdf.format(date).toString();
		time = time.substring(0, time.length() - 2) + ":"
				+ time.substring(time.length() - 2);
		return time;
	}
	/**
	 * 创建者: eava
	 * 创建时间: 2016/3/30 16:30
	 * 功能说明:传两个毫秒数
	 */
	public static String getDateFot(long createtime,long currentTime){
		Date dateCreate = new Date(createtime);
		Date dateCurrent = new Date(currentTime);
		return getDateFot(dateCreate,dateCurrent);
	}
	/**
	 * 创建者: eava
	 * 创建时间: 2016/3/30 16:33
	 * 功能说明:获取指定时间跟现在的对比值
	 */
	public static String getDateFot(long createTime){
		Date dateCreate = new Date(createTime);
		Date dateCurrent = new Date(System.currentTimeMillis());
		return getDateFot(dateCreate,dateCurrent);
	}


	/**
	 * 日期比对函数
	 * 
	 * @param d1 当前时间
	 * @param d2 缓存的时间字符串
	 * @return
	 * @throws Exception
	 */
	public static String getDateFot(Date d1, String d2) {
		String back = "";
		if(TextUtils.isEmpty(d2)){
			return "";
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date2;
		try {
			date2 = sdf.parse(d2);
		} catch (ParseException e) {
			e.printStackTrace();
			return back;
		}
		String t1 = getDateYMD(d1);
		String t2 = getDateYMD(date2);
		if (t1.equals(t2)) {// 同一天
			back = GuideApplication.getInstance().getResources().getString(R.string.date_today) + " " + getDateHS(date2);
		} else {// 不同天
			if (d1.after(date2)) {// d2在d1的前
				String t3 = getDateYMD(getSpecifiedDayBefore(d1));
				String t4 = getDateYMD(date2);
				if (t3.equals(t4)) {// 早一天
					back =  GuideApplication.getInstance().getResources().getString(R.string.date_yesterday) + " " + getDateHS(date2);
				} else {// 早多天
					String t5 = getDateYYYY(d1);
					String t6 = getDateYYYY(date2);
					if (t5.equals(t6)) {// 同一年
						back = getDateMDHS(date2);
					} else {// 不同年
						back = getDateYMDHM(date2);
					}
				}
			}else{
				back = d2;
			}
		}
		return back;
	}
	
	
	/**
	 * 日期比对函数
	 * 
	 * @param d1   当前时间
	 * @param dateStr   缓存的时间字符串
	 * @return
	 * @throws Exception
	 */
	public static boolean getUpdateDateFot(Date d1, String dateStr) {
		if(!TextUtils.isEmpty(dateStr)){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date2;
			try {
				date2 = sdf.parse(dateStr);
			} catch (ParseException e) {
				e.printStackTrace();
				return true;
			}
			String t1 = getDateYMD(d1);
			String t2 = getDateYMD(date2);
			if (t1.equals(t2)) {// 同一天
				return false;
			}
		}
		return true;
	}
	
	
	/**
	 * 日期比对函数，解析今天，昨天
	 * 
	 * @param d1
	 * @param d2
	 * @return 
	 */
	public static String getDateFot(Date d2, Date d1) {
		String back = "";
		String t1 = getDateYMD(d1);
		String t2 = getDateYMD(d2);
		if (t1.equals(t2)) {// 同一天
			back =  GuideApplication.getInstance().getResources().getString(R.string.date_today) + getDateHS(d2);
		} else {// 不同天
			if (d1.after(d2)) {            //d2在d1的前
				String t3 = getDateYMD(getSpecifiedDayBefore(d1));
				String t4 = getDateYMD(d2);
				if (t3.equals(t4)) {       // 早一天
					back =  GuideApplication.getInstance().getResources().getString(R.string.date_yesterday) + getDateHS(d2);
				} else {// 早多天
					String t5 = getDateYYYY(d1);
					String t6 = getDateYYYY(d2);
					if (t5.equals(t6)) {// 同一年
						back = getDateMDHS(d2);
					} else {// 不同年
						back = getDateYMDHM(d2);
					}
				}
			}
		}
		return back;
	}

	public static String getDateFotSample(Date d1, Date d2) {
		String back = "";
		String t1 = getDateYMD(d1);
		String t2 = getDateYMD(d2);
		if (t1.equals(t2)) {// 同一天
			back =  GuideApplication.getInstance().getResources().getString(R.string.date_today) + getDateHS(d2);
		} else {// 不同天
			if (d1.after(d2)) {// d2在d1的前
				String t3 = getDateYMD(getSpecifiedDayBefore(d1));
				String t4 = getDateYMD(d2);
				if (t3.equals(t4)) {// 早一天
					back = GuideApplication.getInstance().getResources().getString(R.string.date_yesterday) + getDateHS(d2);
				} else {// 早多天
					String t5 = getDateYYYY(d1);
					String t6 = getDateYYYY(d2);
					if (t5.equals(t6)) {// 同一年
						back = getDateYMDHSSample(d2);
					} else {// 不同年
						back = getDateYMDHSDiff(d2);
					}
				}
			} else {// d2在d1的后
			}
		}
		return back;
	}

	/**
	 * 获得指定日期的前一天
	 *
	 * @return
	 * @throws Exception
	 */
	public static Date getSpecifiedDayBefore(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int day = c.get(Calendar.DATE);
		c.set(Calendar.DATE, day - 1);
		return c.getTime();
	}

	/**
	 * 将一个日期对象转换成为指定日期YYYYMMDD字符串格式
	 * 
	 */
	public static synchronized String getDateYMDHSDiff(Date theDate) {
		String timevalue = getDateFormat(theDate, "y-M-d H:m");
		return timevalue;
	}

	public static synchronized String getDateYMDHMSSample(Date theDate) {
		String timevalue = getDateFormat(theDate, "y-M-d H:m:s");
		return timevalue;
	}
	
	
	public static synchronized String getDateYMDHMS(Date theDate) {
		String timevalue = getDateFormat(theDate, "yyyy-MM-dd HH:mm:ss");
		return timevalue;
	}
	public static synchronized String getDateYMDHMSS(Date theDate) {
		String timevalue = getDateFormat(theDate, "yyyy-MM-dd HH:mm:ss.SSS");
		return timevalue;
	}
	

	/**
	 * 将一个日期对象转换成为指定日期YYYYMMDD字符串格式
	 * 
	 */
	public static synchronized String getDateMDHS(Date theDate) {
		String timevalue = getDateFormat(theDate, "MM-dd HH:mm");
		return timevalue;
	}

	public static synchronized String getDateYMDHM(Date theDate) {
		String timevalue = getDateFormat(theDate, "yyyy-MM-dd HH:mm");
		return timevalue;
	}
	

	public static synchronized String getDateYMDHSSample(Date theDate) {
		String timevalue = getDateFormat(theDate, "M-d H:m");
		return timevalue;
	}

	/**
	 * 将一个日期对象转换成为指定日期YYYYMMDD字符串格式
	 * 
	 */
	public static synchronized String getDateHS(Date theDate) {
		String timevalue = getDateFormat(theDate, "HH:mm");
		return timevalue;
	}
     public static synchronized String getCurrentDate(String format){
    	 return getDateFormat(Calendar.getInstance().getTime(), format);
     }
	/**
	 * 将一个日期对象转换成为指定日期YYYYMMDD字符串格式
	 * 
	 */
	public static synchronized String getDateYMD(Date theDate) {
		String timevalue = getDateFormat(theDate, "yyyy-MM-dd");
		return timevalue;
	}

	/**
	 * 将一个日期对象转换成为指定日期YYYYMMDD字符串格式
	 * 
	 */
	public static synchronized String getDateYYYY(Date theDate) {
		String timevalue = getDateFormat(theDate, "yyyy");
		return timevalue;
	}

	/**
	 * @param date
	 * @param pattern
	 * @return String
	 */
	public static synchronized String getDateFormat(Date date,
			String pattern) {
		synchronized (sdf) {
			String str = null;
			sdf.applyPattern(pattern);
			str = sdf.format(date);
			return str;
		}
	}
	
	/**
	 * 根据传进来的Date获取yyyyMMddhhMMss
	 * @param date
	 * @return
	 */
	public static synchronized String getDateNumber(Date date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddhhMMss", Locale.getDefault());
		return dateFormat.format(date);
	}

	/**
	 * @param strDate
	 * @param pattern
	 * @return java.util.Date
	 */
	public static synchronized Date parseDateFormat(String strDate,
			String pattern) {
		synchronized (sdf) {
			Date date = null;
			sdf.applyPattern(pattern);
			try {
				date = sdf.parse(strDate);
			} catch (Exception e) {
			}
			return date;
		}
	}

    public static String formatTime(long currentTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(currentTime);
    }




	private static SimpleDateFormat mSimpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);

	/**获取系统时间 格式为："yyyy-MM-dd HH:mm:ss"*/
	public static String getCurrentDate() {
		Date d = new Date();
		return mSimpleDateFormat.format(d);
	}

	/**
	 * 按照固定格式，格式化日期
	 * @param dateString
	 * @return
	 */
	public static Date getFormatDate(String dateString){
		Date date=null;
		try {
			date = mSimpleDateFormat.parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 通过输入格式来格式化日期
	 * @param dateString
	 * @param pattern
	 * @return
	 */
	public static Date getFormatDate(String dateString, String pattern){
		SimpleDateFormat sdfTemp = null;
		if(!TextUtils.isEmpty(pattern)){
			sdfTemp = new SimpleDateFormat(pattern, Locale.CHINA);
		}else{
			sdfTemp = mSimpleDateFormat;
		}

		Date date=null;
		try {
			date = sdfTemp.parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 通过输入格式来格式化日期
	 * @param timestamp
	 * @param pattern
	 * @return
	 */
	public static String getFormatDate(long timestamp, String pattern){
		SimpleDateFormat tempSdf = null;
		if(!TextUtils.isEmpty(pattern)){
			tempSdf = new SimpleDateFormat(pattern, Locale.CHINA);
		}else{
			tempSdf = mSimpleDateFormat;
		}
		if(timestamp<9999999999.0){
			timestamp*=1000;
		}
		return tempSdf.format(new Date(timestamp));
	}

	/**时间戳转换成字符窜*/
	public static String getDateToString(long time) {
		if(time<9999999999.0){
			time*=1000;
		}
		Date d = new Date(time);
		return mSimpleDateFormat.format(d);
	}

	/**
	 * 在某日期上增加几个小时或者减少几个小时（输入负值）
	 * @param date
	 * @param hours
	 * @return
	 */
	public static Date addHoursByDate(Date date, int hours){
		Calendar ca=Calendar.getInstance();
		ca.setTime(date);
		ca.add(Calendar.HOUR_OF_DAY, hours);
		return ca.getTime();
	}

	/**
	 * 在某日期上增加几分钟或者减少几分钟（输入负值）
	 * @param date
	 * @param minute
	 * @return
	 */
	public static Date addMinuteTimeByDate(Date date, int minute){
		Calendar ca=Calendar.getInstance();
		ca.setTime(date);
		ca.add(Calendar.MINUTE, minute);
		return ca.getTime();
	}

	/**
	 * 在某日期上增加几天或者减少几天（输入负值）
	 * @param date
	 * @param day
	 * @return
	 */
	public static Date addDayTimeByDate(Date date, int day){
		Calendar ca=Calendar.getInstance();
		ca.setTime(date);
		ca.add(Calendar.DAY_OF_YEAR, day);
		return ca.getTime();
	}

	/**
	 * 获取当前系统时间戳
	 * @return
	 */
	public static long getCurrentDateTimestamp() {
		Date d = new Date();
		return d.getTime()/1000;
	}

	/**
	 * 通过秒数获取天数
	 * @param costTime
	 * @return
	 */
	public static String getDaysBySeconds(int costTime) {
		if(costTime>=0&&costTime<43200){
			return "0";
		}else if(costTime>=43200&&costTime<86400){
			return "0.5";
		}else if(costTime<0){
			return "0";
		}else{
			int day = costTime/86400;
			int surplus = costTime%86400;
			double f1 = surplus/86400.0;
			if(f1>0.5){
				return String.valueOf(day+0.5);
			}else{
				return String.valueOf(day);
			}
		}
	}



	public static String getTimeStrBySecond(int second) {
		int m = (int)(second)/60;
		int s= (int)(second- m*60) ;
		String mm = m < 10 ? "0"+m: m+"";
		String ss = s < 10 ? "0"+s: s+"";
	    String timeStr =  mm+":"+ss;
		return timeStr;
	}
}
