package com.idea.sale.admin.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**  
* Title: DateUtil.java  
* Description:   
* @author zhangyong 
* @date 2018年11月28日  
* @version 1.0  
*/
public class DateUtil {
	/**
	 *  desc:字符串转日期
	 * author:zhangyong
	 * date:2018年11月28日下午5:54:13
	 * return:Date
	 * version 1.0
	 */
	public static Date StringToDate(String str) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = null;
		try {
			date = sdf.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * desc:日期转字符串
	 * author:zhangyong
	 * date:2018年11月28日下午5:54:35
	 * return:String
	 * version 1.0
	 */
	public static String DateToString(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String str = null;
		str = sdf.format(date);
		return str;
	}
	 /**
	  * desc:返回当前时间，按yyyy-MM-dd HH:mm:ss格式
	  * author:zhangyong
	  * date:2018年11月28日下午5:57:26
	  * return:String
	  * version 1.0
	  */
    public static String getCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date());
    }

    /**
     * desc:返回当前时间，按yyyy-MM-dd 格式
     * author:zhangyong
     * date:2018年11月28日下午5:57:54
     * return:String
     * version 1.0
     */
    public static String getDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(new Date());
    }

}
