package com.idea.sale.admin.util;

/**  
 * Title: StringUtility
 * Description: 
 * @author zhangyong
 * @date 2018年11月30日下午3:39:58  
 */

import java.util.UUID;

public class StringUtility {

	public static boolean isNullOrEmpty(String s) {
		return ((s == null) || (s.equals("")));
	}

	public static boolean isNullOrEmpty(Object o) {
		if (o == null)
			return true;
		return isNullOrEmpty(o.toString());
	}

	public static boolean isInteger(Object o) {
		try {
			Integer.parseInt(o.toString());
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	public static boolean isDouble(Object o) {
		try {
			Double.parseDouble(o.toString());
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	public static boolean isUUID(Object o) {
		try {
			UUID.fromString(o.toString());
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	public static boolean isLong(Object o) {
		try {
			Long.parseLong(o.toString());
			return true;
		} catch (Exception ex) {
			return false;
		}
	}
}
