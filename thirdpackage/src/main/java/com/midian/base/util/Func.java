package com.midian.base.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;

/**
 * Created by XuYang on 15/4/20.
 */
public class Func {

	public final static Pattern mobiler = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$");

	/**
	 * 判断是不是一个合法的手机号
	 *
	 * @return
	 */
	public static boolean isMobileNO(String mobile) {
		if (mobile == null || mobile.trim().length() == 0) {
			return false;
		}
		
		return mobiler.matcher(mobile).matches();
	}

	/**
	 * 把dp转为px
	 */
	public static int Dp2Px(Context context, float dp) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dp * scale + 0.5f);
	}

	/**
	 * 把px转为dp
	 */
	public static int Px2Dp(Context context, float px) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (px / scale + 0.5f);
	}
	
	/**
	 * json字符串的格式化
	 * 
	 * @author peiyuxin
	 * @param json
	 *            需要格式的json串
	 * @param fillStringUnit每一层之前的占位符号比如空格
	 *            制表符
	 * @return
	 */
	public static String formatJson(String json, String fillStringUnit) {
		if (json == null || json.trim().length() == 0) {
			return null;
		}

		int fixedLenth = 0;
		ArrayList<String> tokenList = new ArrayList<String>();
		{
			String jsonTemp = json;
			// 预读取
			while (jsonTemp.length() > 0) {
				String token = getToken(jsonTemp);
				jsonTemp = jsonTemp.substring(token.length());
				token = token.trim();
				tokenList.add(token);
			}
		}

		for (int i = 0; i < tokenList.size(); i++) {
			String token = tokenList.get(i);
			int length = token.getBytes().length;
			if (length > fixedLenth && i < tokenList.size() - 1
					&& tokenList.get(i + 1).equals(":")) {
				fixedLenth = length;
			}
		}

		StringBuilder buf = new StringBuilder();
		int count = 0;
		for (int i = 0; i < tokenList.size(); i++) {

			String token = tokenList.get(i);

			if (token.equals(",")) {
				buf.append(token);
				doFill(buf, count, fillStringUnit);
				continue;
			}
			if (token.equals(":")) {
				buf.append(" ").append(token).append(" ");
				continue;
			}
			if (token.equals("{")) {
				String nextToken = tokenList.get(i + 1);
				if (nextToken.equals("}")) {
					i++;
					buf.append("{ }");
				} else {
					count++;
					buf.append(token);
					doFill(buf, count, fillStringUnit);
				}
				continue;
			}
			if (token.equals("}")) {
				count--;
				doFill(buf, count, fillStringUnit);
				buf.append(token);
				continue;
			}
			if (token.equals("[")) {
				String nextToken = tokenList.get(i + 1);
				if (nextToken.equals("]")) {
					i++;
					buf.append("[ ]");
				} else {
					count++;
					buf.append(token);
					doFill(buf, count, fillStringUnit);
				}
				continue;
			}
			if (token.equals("]")) {
				count--;
				doFill(buf, count, fillStringUnit);
				buf.append(token);
				continue;
			}

			buf.append(token);
			// 左对齐
			if (i < tokenList.size() - 1 && tokenList.get(i + 1).equals(":")) {
				int fillLength = fixedLenth - token.getBytes().length;
				if (fillLength > 0) {
					for (int j = 0; j < fillLength; j++) {
						buf.append(" ");
					}
				}
			}
		}
		return buf.toString();
	}
	
	private static String getToken(String json) {
		StringBuilder buf = new StringBuilder();
		boolean isInYinHao = false;
		while (json.length() > 0) {
			String token = json.substring(0, 1);
			json = json.substring(1);

			if (!isInYinHao
					&& (token.equals(":") || token.equals("{")
							|| token.equals("}") || token.equals("[")
							|| token.equals("]") || token.equals(","))) {
				if (buf.toString().trim().length() == 0) {
					buf.append(token);
				}

				break;
			}

			if (token.equals("\\")) {
				buf.append(token);
				buf.append(json.substring(0, 1));
				json = json.substring(1);
				continue;
			}
			if (token.equals("\"")) {
				buf.append(token);
				if (isInYinHao) {
					break;
				} else {
					isInYinHao = true;
					continue;
				}
			}
			buf.append(token);
		}
		return buf.toString();
	}
	
	private static void doFill(StringBuilder buf, int count,
			String fillStringUnit) {
		buf.append("\n");
		for (int i = 0; i < count; i++) {
			buf.append(fillStringUnit);
		}
	}

	public static String stampToDate(String s){
		String res;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long lt = new Long(s);
		Date date = new Date(lt*1000);
		res = simpleDateFormat.format(date);
		return res;
	}

	/**
	 * 从字符串中提取所有的数字
	 * @param s
	 * @return
	 */
	public static String getNumberFromString(String s){
		String regEx="[^0-9]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(s);
		return m.replaceAll("").trim();
	}
}
