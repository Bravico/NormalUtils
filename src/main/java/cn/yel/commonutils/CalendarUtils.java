package cn.yel.commonutils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 
 * @author Yel
 *
 */
public class CalendarUtils {

	static Calendar global_calendar = Calendar.getInstance();

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(getYear());
		System.out.println(getMonth());
		System.out.println(getDay());
		System.out.println(getMaxDays("2017-02-20"));
	}

	/**
	 * get current year number
	 * 
	 * @return int year
	 */
	public static int getYear() {
		return global_calendar.get(Calendar.YEAR);
	}

	/**
	 * get current month number
	 * 
	 * @return int month
	 */
	public static int getMonth() {
		return global_calendar.get(Calendar.MONTH) + 1;
	}

	/**
	 * get current day number
	 * 
	 * @return int day
	 */
	public static int getDay() {
		return global_calendar.get(Calendar.DATE);
	}

	/**
	 * get current month max days 返回当前月最大的天数
	 * 
	 * @return int 天数
	 */
	public static int getMaxDays() {
		return global_calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 返回特定时间的最大天数
	 * 
	 * @param date
	 *            String type, format "yyyy-MM-dd"
	 * @return int 天数
	 */
	public static int getMaxDays(String date) {
		if (date == null || "".equals(date))
			return getMaxDays();
		if (!RegexUtils.isValidatedDate(date))
			return -1;
		Calendar calendar = Calendar.getInstance();
		try {
			calendar.setTime(DateUtils.stringToDate(date, DateUtils.DATE_FORMAT_SIMPLEST));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 返回过去的天数
	 * 
	 * @param past
	 * @return
	 */
	public static String getPastDate(int past) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - past);
		Date today = calendar.getTime();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String result = format.format(today);
		return result;
	}

	/**
	 * 返回一个月中的第一天，format yyyy-MM-dd
	 * 
	 * @return
	 */
	public String getFirstDay() {
		int year = global_calendar.get(GregorianCalendar.YEAR);
		int month = (global_calendar.get(GregorianCalendar.MONTH) + 1);
		return year + "-0" + month + "-01";
	}

	/**
	 * 返回未来的天数的日期，按照 "yyyy-MM-dd"的形式
	 * 
	 * @param future
	 * @return "yyyy-MM-dd"的形式的日期
	 */
	public static String getFetureDate(int future) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + future);
		Date today = calendar.getTime();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String result = format.format(today);
		return result;
	}

}
