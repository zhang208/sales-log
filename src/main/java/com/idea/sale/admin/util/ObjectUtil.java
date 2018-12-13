package com.idea.sale.admin.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

/**  
* Title: ObjectUtil
* Description: 
* @author zhangyong
* @date 2018年11月29日下午1:39:45  
*/

public class ObjectUtil {
	/**
	 * desc:将map转化成实体类
	 * author:zhangyong
	 * date:2018年11月29日下午1:42:28
	 * return:Object
	 * version 1.0
	 */
	public static Object mapToBean(Map<?, ?> map, Class<?> clazz) {
		Object obj = null;
		try {
			obj = clazz.newInstance();
			Method[] methods = clazz.getDeclaredMethods();
			for (Method method : methods) {
				if (method.getName().startsWith("set")) {
					String key = method.getName().replace("set", "");
					key = key.substring(0, 1).toLowerCase().concat(key.substring(1));
					Object value = map.get(key);
					if (StringUtility.isNullOrEmpty(value) || value.equals("N/A")) {
						continue;
					}
					Class<?>[] paramType = method.getParameterTypes();
					// 根据参数类型执行对应的set方法给vo赋值
					if (paramType[0] == String.class) {
						method.invoke(obj, String.valueOf(value));
						continue;
					} else if (paramType[0] == BigDecimal.class) {
						method.invoke(obj, new BigDecimal(value.toString()));
						continue;
					} else if (paramType[0] == Double.class) {
						method.invoke(obj, Double.parseDouble(value.toString()));
						continue;
					} else if (paramType[0] == Date.class) {
						 Date date = new Date(Long.parseLong(value.toString()));
						method.invoke(obj, date);
						continue;
					} else if (paramType[0] == int.class || paramType[0] == Integer.class) {
						method.invoke(obj, Integer.valueOf(value.toString()));
						continue;
					} else if (paramType[0] == Boolean.class) {
						method.invoke(obj, Boolean.parseBoolean(value.toString()));
						continue;
					} else if (paramType[0] == char.class || paramType[0] == Character.class) {
						method.invoke(obj, value.toString().charAt(0));
						continue;
					}
				}
			}
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return obj;
	}
}
