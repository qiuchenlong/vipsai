package com.vs.library.utils;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Locale;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {

	public static String getPercentString(float percent) {
		return String.format(Locale.US, "%d%%", (int) (percent * 100));
	}

	/**
	 * 是否是指定长度的数字、字母、汉字、下划线组合
	 * @param minLength
	 * @param maxLength
	 * @return
	 */
	public static boolean isNumTextSimplified(String src, int minLength, int maxLength) {
		boolean result = false;
		
		if(!TextUtils.isEmpty(src)) {
			String expre  = "^(?!_)(?!.*?_$)[a-zA-Z0-9_\\u4e00-\\u9fa5]+$";
			if(minLength > 0 && maxLength >= minLength) {
				expre  = "^[\\u4E00-\\u9FA5\\uF900-\\uFA2D\\w]{" + minLength + "," + maxLength + "}$";
			}
			Pattern p = Pattern.compile(expre);
			result = p.matcher(src).matches();
		}
		return result;
	}

	/**
	 * 数字、字母、下划线组合
	 * @param src
	 * @return
	 */
	public static boolean isNumText(String src) {
		boolean result = false;
		if(!TextUtils.isEmpty(src)) {
			String expre = "^(?!_)(?!.*?_$)[a-zA-Z0-9_]+$";
			Pattern p = Pattern.compile(expre);
			result = p.matcher(src).matches();
		}
		return result;
	}

	public static boolean isPhoneNum(String src) {
		boolean result = false;
		if(!TextUtils.isEmpty(src)) {
			Pattern p = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$"); // 验证手机号
			Matcher m = p.matcher(src);
			return m.matches();
		}
		return result;
	}

	/**
	 * 只包含汉字
	 * @param src
	 * @return
	 */
	public static boolean onlyChinese(String src) {
		if(src != null) {
			Pattern p = Pattern.compile("^[\\u4e00-\\u9fa5]+$");
			Matcher m = p.matcher(src);
			if (m.find()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 是否包含中文
	 * @param str
	 * @return
	 */
	public static boolean hasContainChinese(String str) {
		if(str != null) {
			Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
			Matcher m = p.matcher(str);
		       if (m.find()) {
		           return true;
		       }   
		}
	       return false;
	}
	
	/**
	 * 验证邮箱格式
	 * @param email
	 * @return
	 */
	public static boolean isEmail(String email) {

		if(!TextUtils.isEmpty(email)) {
			String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
			Pattern p = Pattern.compile(str);
			Matcher m = p.matcher(email);
			return m.matches();
		}
		
		return false;
	}
	
	public static boolean isEmpty(String str) {
		return TextUtils.isEmpty(str) || "null".equalsIgnoreCase(str);
	}

	/**
	 * 增加颜色片段或大小
	 * @param txt
	 * @param startChar
	 * @param endChar
	 * @param colorRes
	 * @param sizeRes
	 * @param exclu_inclu
	 * @return
	 */
	public static Spannable addColorAndSize(Context context, String txt, String startChar, String endChar, int colorRes, int sizeRes, int exclu_inclu) {
		if(!TextUtils.isEmpty(txt)) {

			int start = 0;
			if(!TextUtils.isEmpty(startChar)) {
				start = txt.indexOf(startChar);
			}
			int end = Math.max(0,txt.length() - 1);
			if(!TextUtils.isEmpty(endChar)) {
				end = txt.lastIndexOf(endChar);
				if(Spannable.SPAN_INCLUSIVE_INCLUSIVE == exclu_inclu || Spannable.SPAN_EXCLUSIVE_INCLUSIVE == exclu_inclu) {
					end++;
				}
			}

			if(start < end) {
				int color = CompatibilityUtil.getColor(context, colorRes);
				int size = context.getResources().getDimensionPixelSize(sizeRes);

				SpannableString ssb = new SpannableString(txt);
				if(colorRes > 0) {
					ssb.setSpan(new ForegroundColorSpan(color), start, end, exclu_inclu);
				}
				if(sizeRes > 0) {
					ssb.setSpan(new AbsoluteSizeSpan(size), start, end, exclu_inclu);
				}

				return ssb;
			}else {
				return new SpannableString(txt);
			}

		}

		return null;
	}

	/**
	 * 验证qq号
	 * @param qq
	 * @return
     */
	public static boolean isValidQQ(String qq) {
		String regex = "[1-9][0-9]{4,}";
		boolean result = false;
		if(!TextUtils.isEmpty(qq)) {
			try {
				Pattern pattern = Pattern.compile(regex);
				Matcher matcher = pattern.matcher(qq);
				result = matcher.matches();
			} catch (Exception e) {
			}
		}
		return result;
	}

	/**
	 * 从assets读取字符串
	 * @param context
	 * @param assetFile
	 * @return null or string
	 */
	public static String getStringFromAssets(Context context, String assetFile) {
		InputStream is = null;
		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();
		String line;
		try {
			is = context.getAssets().open("areas.json");
			br = new BufferedReader(new InputStreamReader(is));
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			return sb.toString();
		}catch(IOException e){
		}finally {
			if(is != null) {
				try {
					is.close();
				}catch(IOException e){}
			}
		}
		return null;
	}

	/**
	 * counter ASCII character as one, otherwise two
	 *
	 * @param str
	 * @return count
	 */
	public static int counterChars(String str) {
		// return
		if (TextUtils.isEmpty(str)) {
			return 0;
		}
		int count = 0;
		for (int i = 0; i < str.length(); i++) {
			int tmp = (int) str.charAt(i);
			if (tmp > 0 && tmp < 127) {
				count += 1;
			} else {
				count += 2;
			}
		}
		return count;
	}

	/**
	 * 删除字符串中的空白符
	 *
	 * @param content
	 * @return String
	 */
	public static String removeBlanks(String content) {
		if (content == null) {
			return null;
		}
		StringBuilder buff = new StringBuilder();
		buff.append(content);
		for (int i = buff.length() - 1; i >= 0; i--) {
			if (' ' == buff.charAt(i) || ('\n' == buff.charAt(i)) || ('\t' == buff.charAt(i))
					|| ('\r' == buff.charAt(i))) {
				buff.deleteCharAt(i);
			}
		}
		return buff.toString();
	}

	public static String get32UUID() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

	/**
	 * 生成唯一号
	 *
	 * @return
	 */
	public static String get36UUID() {
		UUID uuid = UUID.randomUUID();
		String uniqueId = uuid.toString();
		return uniqueId;
	}

	public static int longDivPercent(long small, long big) {
		if(big == 0){
			return 0;
		}
		double smalld = Double.parseDouble(String.valueOf(small));
		double bigd = Double.parseDouble(String.valueOf(big));
		double result = (smalld / bigd) * 100;

		return (int)result;
	}
}
