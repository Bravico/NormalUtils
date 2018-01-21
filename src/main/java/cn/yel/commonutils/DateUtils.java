package cn.yel.commonutils;



import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
	
	public static final String DATE_FORMAT_SIMPLEST = "yyyy-MM-dd";
	public static final String DATE_FORMAT_WITH_TIME = "yyyy-MM-dd HH:mm:ss";
	
		/**
		 * transfer date to string
		 * @param date
		 * @param formatType
		 * @return
		 */
	 	public static String dateToString(Date date, String formatType) {
	 		if(date==null || formatType == null) return null;
	 		return new SimpleDateFormat(formatType).format(date);
	 	}
	 
	 	/**
	 	 * transfer long-type seconds to string
	 	 * @param currentTime 秒数
	 	 * @param formatType formatType yyyy-MM-dd HH:mm:ss or yyyy-MM-dd
	 	 * @return
	 	 * @throws ParseException
	 	 */
	 	public static String longToString(long currentTime, String formatType)
	 			throws ParseException {
	 		if(currentTime==0l || formatType==null || "".equals(formatType)) return null;
	 		Date date = longToDate(currentTime, formatType); 
	 		String strTime = dateToString(date, formatType); 
	 		return strTime;
	 	}
	 	
	 	/**
	 	 * transfer string-type date to real date type
	 	 * @param strTime
	 	 * @param formatType
	 	 * @return
	 	 * @throws ParseException
	 	 */
	 	public static Date stringToDate(String strTime, String formatType)
	 			throws ParseException {
	 		if(strTime==null || "".equals(strTime) || formatType==null || "".equals(formatType)) return null;
	 		SimpleDateFormat formatter = new SimpleDateFormat(formatType);
	 		Date date = null;
	 		date = formatter.parse(strTime);
	 		return date;
	 	}
	 
	 	/**
	 	 * 将秒数转换为日期类型
	 	 * @param currentTime 秒数！
	 	 * @param formatType 传入的参数类型
	 	 * @return
	 	 * @throws ParseException
	 	 */
	 	public static Date longToDate(long currentTime, String formatType)
	 			throws ParseException {
	 		if(currentTime==0l || formatType==null || "".equals(formatType)) return null;
	 		Date dateOld = new Date(currentTime*1000); // 根据long类型的秒数生命一个date类型的时间
	 		String sDateTime = dateToString(dateOld, formatType); // 把date类型的时间转换为string
	 		Date date = stringToDate(sDateTime, formatType); // 把String类型转换为Date类型
	 		return date;
	 	}
	 
	 	/**
	 	 * 将String类型的date转换成秒数
	 	 * @param strTime
	 	 * @param formatType
	 	 * @return long类型的秒数
	 	 * @throws ParseException 
	 	 */
	 	public static long stringToLong(String strTime, String formatType)
	 			throws ParseException {
	 		if(strTime==null || formatType==null) return 0l;
	 		Date date = stringToDate(strTime, formatType); // String类型转成date类型
	 		if (date == null) {
	 			return 0;
	 		} else {
	 			long currentTime = dateToLong(date); // date类型转成long类型
	 			return currentTime;
	 		}
	 	}
	 
	 	/**
	 	 * 将date类型转换为long类型的的秒数
	 	 * @param date
	 	 * @return 秒数
	 	 */
	 	public static long dateToLong(Date date) {
	 		if(date==null) return 0l;
	 		return date.getTime()/1000;
	 	}
	 	
	 	public static void main(String[] args) throws ParseException {
	 		Date date = new Date();
	 		System.out.println(longToDate(1516242014000l, "yyyy-MM-dd HH:mm:ss"));
	 		System.out.println(date.getTime());
	 	}

}
