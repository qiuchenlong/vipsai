package com.vs.library.utils;

import android.content.Context;
import android.text.TextUtils;

import com.vs.library.R;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;


/**
 * 日期时间工具
 * @author chends
 */
public class DateUtil {

	public static final String FORMAT_HOUR_MINUTE = "HH:mm";

	/**默认格式yyyy-MM-dd HH:mm:ss*/
	public static final String DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss";
	
	/**默认格式yyyy.MM.dd HH:mm:ss*/
	public static final String DEFAULT_FORMAT_DOT = "yyyy.MM.dd HH:mm:ss";
	
	/**默认日期格式yyyy-MM-dd*/
	public static final String FORMAT_DAY = "yyyy-MM-dd";
	
	/**日期格式yyyy.MM.dd*/
	public static final String FORMAT_DOT_DAY = "yyyy.MM.dd";

	public static final String FORMAT_SLASH_DAY = "yyyy/MM/dd";

	public static final String FORMAT_SLASH = "yyyy/MM/dd HH:mm:ss";

	/**
	 * 比较日期
	 * @param left
	 * @param right
	 * @param format
	 * @return left > right = 1; left < right = -1; or 0
	 */
	public static int compare(String left, String right, String format) {
		int result = 0;
		SimpleDateFormat date = new SimpleDateFormat(format);
		try {
			Date leftd = date.parse(left);
			Date rightd = date.parse(right);
			return leftd.compareTo(rightd);

		}catch(ParseException e){}
		return result;
	}

	/**
	 * 当前时间
	 * @param format
	 * @return
	 */
	public static String nowDate(String format) {
		return format(System.currentTimeMillis(), format);
	}
	
	/**
	 * 返回指定格式时间
	 * @param milliseconds 毫秒
	 * @param format
	 * @return
	 */
	public static String format(long milliseconds, String format) {
		SimpleDateFormat date = new SimpleDateFormat(format);
		return date.format(new Date(milliseconds));
	}
	
	/**
	 * 返回指定格式时间
	 * @param second 秒
	 * @param format
	 * @return
	 */
	public static String formatSecond(long second, String format) {
		SimpleDateFormat date = new SimpleDateFormat(format);
		return date.format(new Date(second * 1000));
	}

	/**
	 * 目标时间与当前时间差值（按天算）
	 * @param mills
	 * @return -2 | -1 | 0 | 1 | 2 等
	 */
	public static long intervalFormNow(long mills) {
		if(mills > 0) {
			Calendar from = Calendar.getInstance();
			from.setTime(new Date(mills));
			Calendar cur = Calendar.getInstance();
			cur.setTime(new Date());

			if(from.get(Calendar.YEAR) == cur.get(Calendar.YEAR) && from.get(Calendar.MONTH) == cur.get(Calendar.MONTH)) {
				return from.get(Calendar.DAY_OF_MONTH) - cur.get(Calendar.DAY_OF_MONTH);
			}
		}
		return Integer.MAX_VALUE;
	}

	/**
	 * 返回配置的可读时间
	 * @param context
	 * @return 今天、昨天、前天...
	 */
	public static String[] readableDate(Context context) {
		return context.getResources().getStringArray(R.array.readable_date);
	}

	/**
	 * 时间范围内
	 * @param startStr
	 * @param endStr
	 * @param intervalDay
	 * @return true or false
	 */
	public static boolean between(String startStr, String endStr, int intervalDay, String format) {
		int tmpDay = intervalDay;
		boolean isPositive = tmpDay == Math.abs(tmpDay);
		Date startDate = toDay(startStr, format);
		Date endDate = toDay(endStr, format);
		if (startDate == null || endDate == null) {
			return false; 
		}
		long timeLong = endDate.getTime() - startDate.getTime();
		int dayInterval = (int) (timeLong / 1000 / 60 / 60 / 24);
		if(isPositive){
			return dayInterval >=0 && dayInterval <= intervalDay;
		}else{
			return dayInterval >= intervalDay && dayInterval <=0;
		}
	}
	
	/**
	 *
	 * @param str
	 * @return null or date
	 */
	public static Date toDay(String str, String format) {
		String fmt = FORMAT_DAY;
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Date date = null;
		try {
			date = sdf.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}catch(Exception e){}
		return date;
	}
	
	/**
	 * 计算年龄
	 * @param from 起始日期
	 * @param to 截止日期
	 * @return 失败返回null;
	 */
	public static Integer getYearSum(String from, String to) {
		Integer result = null;
		
		if(!TextUtils.isEmpty(from) && !TextUtils.isEmpty(to)) {
			
			SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_DAY);
			try {
				
				Calendar fromDate = Calendar.getInstance();
				fromDate.setTime(sdf.parse(from));
				
				Calendar toDate = Calendar.getInstance();
				toDate.setTime(sdf.parse(to));
				
				result = toDate.get(Calendar.YEAR) - fromDate.get(Calendar.YEAR);
				
				boolean full = false;
				if(toDate.get(Calendar.MONTH) == fromDate.get(Calendar.MONTH)) {
					full = toDate.get(Calendar.DAY_OF_MONTH) > toDate.get(Calendar.DAY_OF_MONTH);
				}else if(toDate.get(Calendar.MONTH) > fromDate.get(Calendar.MONTH)) {
					full = true;
				}
				
				if(!full) {
					result--;
				}
				
			}catch(ParseException e){
			}catch(IllegalArgumentException e) {
			}catch(ArrayIndexOutOfBoundsException e){}
			
		}
		return result;
	}

	/**
	 * 获取年、月、日
	 * @param c
	 * @return	int[0] 年 int[1] 月(0 起步) int[2] 日
	 */
	public static int[] getDate(Calendar c) {
		int[] result = new int[3];
		result[0] = c.get(Calendar.YEAR);
		result[1] = c.get(Calendar.MONTH);
		result[2] = c.get(Calendar.DAY_OF_MONTH);
		return result;
	}

	/**
	 * 获取年、月、日
	 * @param
	 * @return	int[0] 年 int[1] 月(0 起步) int[2] 日
	 */
	public static int[] getDate(String time, String format) {
		Calendar c = Calendar.getInstance();
		Date date = new Date();
		if(!TextUtils.isEmpty(time) && !TextUtils.isEmpty(format)) {
			try {
				SimpleDateFormat sdf = new SimpleDateFormat(format);
				date = sdf.parse(time);
			} catch (ParseException e) {
			}
		}
		c.setTime(date);
		return getDate(c);
	}

	/**
	 * 返回时间
	 * @param second
	 * @return "" or "09:23"
	 */
	public static String getHourMinute(long second) {
		return formatSecond(second, FORMAT_HOUR_MINUTE);
	}

	/**
	 * 返回文字的星期数
	 * @param date
	 * @param format
	 * @return 周一、周二...
	 */
	public static String getWeekName(String date, String format) {

		if(!TextUtils.isEmpty(date) && !TextUtils.isEmpty(format)) {
			try {
				Calendar c = Calendar.getInstance();
				SimpleDateFormat sdf = new SimpleDateFormat(format);
				c.setTime(sdf.parse(date));
				return c.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.CHINA);

			}catch(Exception e){}
		}
		return "";
	}

//	public static boolean isEarly(int days, long time) {
//		return (currentTimeMillis() - time) > (days * 24 * 3600 * 1000);
//	}
//
//	public static int currentTimeSecond() {
//		return (int) (System.currentTimeMillis() / 1000);
//	}
//
//	public static long currentTimeMillis() {
//		return System.currentTimeMillis();
//	}
//
//	public static long[] getTsTimes() {
//		long[] times = new long[2];
//
//		Calendar calendar = Calendar.getInstance();
//
//		times[0] = calendar.getTimeInMillis() / 1000;
//
//		calendar.set(Calendar.HOUR_OF_DAY, 0);
//		calendar.set(Calendar.MINUTE, 0);
//		calendar.set(Calendar.SECOND, 0);
//
//		times[1] = calendar.getTimeInMillis() / 1000;
//
//		return times;
//	}
//
//	public static String getFormatDatetime(int year, int month, int day) {
//		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//		return formatter.format(new GregorianCalendar(year, month, day).getTime());
//	}
//
//	public static Date getDateFromFormatString(String formatDate) {
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//		try {
//			return sdf.parse(formatDate);
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
//
//		return null;
//	}
//
//	public static String getNowDatetime() {
//		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
//		return (formatter.format(new Date()));
//	}
//
//	public static int getNow() {
//		return (int) ((new Date()).getTime() / 1000);
//	}
//
//	public static String getNowDateTime(String format) {
//		Date date = new Date();
//
//		SimpleDateFormat df = new SimpleDateFormat(format, Locale.getDefault());
//		return df.format(date);
//	}
//
//	public static String getDateString(long milliseconds) {
//		return getDateTimeString(milliseconds, "yyyyMMdd");
//	}
//
//	public static String getTimeString(long milliseconds) {
//		return getDateTimeString(milliseconds, "HHmmss");
//	}
//
//	public static String getBeijingNowTimeString(String format) {
//		TimeZone timezone = TimeZone.getTimeZone("Asia/Shanghai");
//
//		Date date = new Date(currentTimeMillis());
//		SimpleDateFormat formatter = new SimpleDateFormat(format, Locale.getDefault());
//		formatter.setTimeZone(timezone);
//
//		GregorianCalendar gregorianCalendar = new GregorianCalendar();
//		gregorianCalendar.setTimeZone(timezone);
//		String prefix = gregorianCalendar.get(Calendar.AM_PM) == Calendar.AM ? "上午" : "下午";
//
//		return prefix + formatter.format(date);
//	}
//
//	public static String getBeijingNowTime(String format) {
//		TimeZone timezone = TimeZone.getTimeZone("Asia/Shanghai");
//
//		Date date = new Date(currentTimeMillis());
//		SimpleDateFormat formatter = new SimpleDateFormat(format, Locale.getDefault());
//		formatter.setTimeZone(timezone);
//
//		return formatter.format(date);
//	}
//
//	public static String getDateTimeString(long milliseconds, String format) {
//		Date date = new Date(milliseconds);
//		SimpleDateFormat formatter = new SimpleDateFormat(format, Locale.getDefault());
//		return formatter.format(date);
//	}
//
//
//	public static String getFavoriteCollectTime(long milliseconds) {
//		String showDataString = "";
//		Date today = new Date();
//		Date date = new Date(milliseconds);
//		Date firstDateThisYear = new Date(today.getYear(), 0, 0);
//		if (!date.before(firstDateThisYear)) {
//			SimpleDateFormat dateformatter = new SimpleDateFormat("MM-dd", Locale.getDefault());
//			showDataString = dateformatter.format(date);
//		} else {
//			SimpleDateFormat dateformatter = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
//			showDataString = dateformatter.format(date);
//		}
//		return showDataString;
//	}

	public static String getTimeShowString(Context context, long milliseconds, boolean abbreviate) {
		String dataString;
		String timeStringBy24;

		Date currentTime = new Date(milliseconds);
		Date today = new Date();
		Calendar todayStart = Calendar.getInstance();
		todayStart.set(Calendar.HOUR_OF_DAY, 0);
		todayStart.set(Calendar.MINUTE, 0);
		todayStart.set(Calendar.SECOND, 0);
		todayStart.set(Calendar.MILLISECOND, 0);
		Date todaybegin = todayStart.getTime();
		Date yesterdaybegin = new Date(todaybegin.getTime() - 3600 * 24 * 1000);
		Date preyesterday = new Date(yesterdaybegin.getTime() - 3600 * 24 * 1000);

		if (!currentTime.before(todaybegin)) {
			dataString = context.getString(R.string.today);
		} else if (!currentTime.before(yesterdaybegin)) {
			dataString = context.getString(R.string.yesterday);
		} else if (!currentTime.before(preyesterday)) {
			dataString = context.getString(R.string.the_day_before_yesterday);
		} else if (isSameWeekDates(currentTime, today)) {
			dataString = getWeekOfDate(context, currentTime);
		} else {
			SimpleDateFormat dateformatter = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
			dataString = dateformatter.format(currentTime);
		}

		SimpleDateFormat timeformatter24 = new SimpleDateFormat("HH:mm", Locale.getDefault());
		timeStringBy24 = timeformatter24.format(currentTime);

		if (abbreviate) {
			if (!currentTime.before(todaybegin)) {
				return getTodayTimeBucket(context, currentTime);
			} else {
				return dataString;
			}
		} else {
			return dataString + " " + timeStringBy24;
		}
	}

	/**
	 * 根据不同时间段，显示不同时间
	 *
	 * @param date
	 * @return
	 */
	public static String getTodayTimeBucket(Context context, Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		SimpleDateFormat timeformatter0to11 = new SimpleDateFormat("KK:mm", Locale.getDefault());
		SimpleDateFormat timeformatter1to12 = new SimpleDateFormat("hh:mm", Locale.getDefault());
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		if (hour >= 0 && hour < 5) {
			return context.getString(R.string.daybreak) + " " + timeformatter0to11.format(date);
		} else if (hour >= 5 && hour < 12) {
			return context.getString(R.string.morning) + " " + timeformatter0to11.format(date);
		} else if (hour >= 12 && hour < 18) {
			return context.getString(R.string.afternoon) + " " + timeformatter1to12.format(date);
		} else if (hour >= 18 && hour < 24) {
			return context.getString(R.string.evening) + " " + timeformatter1to12.format(date);
		}
		return "";
	}

	/**
	 * 根据日期获得星期
	 * @param date
	 * @return
	 */
	public static String getWeekOfDate(Context context, Date date) {
		String[] weekDaysName = context.getResources().getStringArray(R.array.week_string);
		// String[] weekDaysCode = { "0", "1", "2", "3", "4", "5", "6" };
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int intWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
		return weekDaysName[intWeek];
	}

//	public static boolean isSameDay(long time1, long time2) {
//		return isSameDay(new Date(time1), new Date(time2));
//	}
//
//	public static boolean isSameDay(Date date1, Date date2) {
//		Calendar cal1 = Calendar.getInstance();
//		Calendar cal2 = Calendar.getInstance();
//		cal1.setTime(date1);
//		cal2.setTime(date2);
//
//		boolean sameDay = cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
//				cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR);
//		return sameDay;
//	}

	/**
	 * 判断两个日期是否在同一周
	 *
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static boolean isSameWeekDates(Date date1, Date date2) {
		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		cal1.setTime(date1);
		cal2.setTime(date2);
		int subYear = cal1.get(Calendar.YEAR) - cal2.get(Calendar.YEAR);
		if (0 == subYear) {
			if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR))
				return true;
		} else if (1 == subYear && 11 == cal2.get(Calendar.MONTH)) {
			// 如果12月的最后一周横跨来年第一周的话则最后一周即算做来年的第一周
			if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR))
				return true;
		} else if (-1 == subYear && 11 == cal1.get(Calendar.MONTH)) {
			if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR))
				return true;
		}
		return false;
	}

	public static long getSecondsByMilliseconds(long milliseconds) {
		long seconds = new BigDecimal((float) ((float) milliseconds / (float) 1000)).setScale(0,
				BigDecimal.ROUND_HALF_UP).intValue();
		// if (seconds == 0) {
		// seconds = 1;
		// }
		return seconds;
	}

	public static String secToTime(int time) {
		String timeStr = null;
		int hour = 0;
		int minute = 0;
		int second = 0;
		if (time <= 0)
			return "00:00";
		else {
			minute = time / 60;
			if (minute < 60) {
				second = time % 60;
				timeStr = unitFormat(minute) + ":" + unitFormat(second);
			} else {
				hour = minute / 60;
				if (hour > 99)
					return "99:59:59";
				minute = minute % 60;
				second = time - hour * 3600 - minute * 60;
				timeStr = unitFormat(hour) + ":" + unitFormat(minute) + ":" + unitFormat(second);
			}
		}
		return timeStr;
	}

	public static String unitFormat(int i) {
		String retStr = null;
		if (i >= 0 && i < 10)
			retStr = "0" + Integer.toString(i);
		else retStr = "" + i;
		return retStr;
	}

	public static boolean afterNow(String dateStr, String format) {
		boolean result =false;
		if(!StringUtil.isEmpty(dateStr)) {
			Date date = toDay(dateStr, format);
			Date now = new Date();

			return date.after(now);
		}
		return result;
	}

//	public static String getElapseTimeForShow(int milliseconds) {
//		StringBuilder sb = new StringBuilder();
//		int seconds = milliseconds / 1000;
//		if (seconds < 1)
//			seconds = 1;
//		int hour = seconds / (60 * 60);
//		if (hour != 0) {
//			sb.append(hour).append("小时");
//		}
//		int minute = (seconds - 60 * 60 * hour) / 60;
//		if (minute != 0) {
//			sb.append(minute).append("分");
//		}
//		int second = (seconds - 60 * 60 * hour - 60 * minute);
//		if (second != 0) {
//			sb.append(second).append("秒");
//		}
//		return sb.toString();
//	}
}
