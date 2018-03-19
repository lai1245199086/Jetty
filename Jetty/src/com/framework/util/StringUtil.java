/************************************************************

  Copyright (C), 1996-2008, resoft Tech. Co., Ltd.
  FileName: StringUtil.java
  Author: superleo       
  Version: 1.0    
  Date:2008-8-5 下午12:44:12
  Description:    

  Function List:   
    1. -------

  History:         
      <author>    <time>   <version >   <desc>


 ***********************************************************/

package com.framework.util;

import java.text.NumberFormat;
import java.util.Random;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author superleo
 * @since 2008-8-5
 * @version
 */
public class StringUtil {
	protected Log log = LogFactory.getLog(this.getClass()) ;
	/**
	 * 判断是否为空字符串
	 */
	public static boolean isNotBlank(String str) {
		return (str != null && !"".equals(str)) ? true : false;
	}

	/**
	 * 判断是否为空字符串
	 */
	public static boolean isBlank(String str) {
		return !isNotBlank(str);
	}

	/**
	 * 判断是否为空字符串(包括空格)
	 */
	public static boolean isNotEmpty(String str) {
		return (str != null && !"".equals(str.trim())) ? true : false;
	}

	/**
	 * 判断是否为空字符串(包括空格)
	 */
	public static boolean isEmpty(String str) {
		return !isNotEmpty(str);
	}

	/**
	 * 字符串比较
	 */
	public static boolean equals(String src, String des) {
		if (src == null)
			return (des == null ? true : false);
		if (des == null)
			return (src == null ? true : false);
		return src.equals(des);
	}
	
	/**
	 * 产生length位的随机数
	 */
	public static String getRandomString(int length) throws NumberFormatException {
		// 定义一个字符串（A-Z，a-z，0-9）即62位；
		String str = "zxcvbnmlkjhgfdsaqwertyuiopQWERTYUIOPASDFGHJKLZXCVBNM1234567890";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; ++i) {
			int number = random.nextInt(62);// 产生0-61的数字
			sb.append(str.charAt(number));
		}
		return sb.toString();
	}

	
	/**
	 * 将String数组变成","号间隔的字符串
	 */
	public static String StringArrayToString(String[] str) {
		StringBuilder sb = new StringBuilder();
		if (str != null && str.length > 0) {
			for (String s : str) {
				if (s != null) {
					sb.append(s + ",");
				}
			}
			if (sb.length() == 0)
				return "";
			return sb.substring(0, sb.length() - 1).toString();
		}
		return str[0];
	}

	/**
	 * 判断URL后缀是否为.action或.do 如果是的话，提取actionName
	 */
	public static String parseServletPath(String servletPath) {
		if (null != servletPath && !"".equals(servletPath)) {
			if (servletPath.contains(".action")) {
				return servletPath.substring(servletPath.lastIndexOf("/") + 1, servletPath.indexOf(".action"));
			}else if (servletPath.contains(".do")){
				return servletPath.substring(servletPath.lastIndexOf("/") + 1, servletPath.indexOf(".do"));
			}
		}
		return "";
	}
	/**
	 * 判断URL后缀是否有“!” 如果是的话，提取methodName
	 */
	public static String getMethodName(String servletPath) {
		if (null != servletPath && !"".equals(servletPath)) {
			if (servletPath.contains("!")) {
				if (servletPath.contains("?")) {
					return servletPath.substring(servletPath.lastIndexOf("!") + 1, servletPath.lastIndexOf("?"));
				}
				return servletPath.substring(servletPath.lastIndexOf("!") + 1, servletPath.length());
			}
		}
		return null;
	}
	
	/**
	 * 获取定长的8位报文头字符串：不足补0
	 */
	public static String getFixedLengthNum(int number,int maxLength) {
		NumberFormat numberFormat = NumberFormat.getNumberInstance();
		numberFormat.setGroupingUsed(false);
	    numberFormat.setMaximumIntegerDigits(maxLength);//最大位数
	    numberFormat.setMinimumIntegerDigits(maxLength);//最小位数
	    String num = numberFormat.format(number);
	    return num;
	}
	
	public static void main(String[] args) {
		String fixedLengthNum= StringUtil.getFixedLengthNum(308,8);
		System.out.println(fixedLengthNum);
	}
}
