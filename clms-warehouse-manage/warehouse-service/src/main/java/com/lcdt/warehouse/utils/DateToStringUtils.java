package com.lcdt.warehouse.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Sheng-ji Lau
 * @date 2018年8月10日
 * @version
 * @Description: TODO 
 */
public class DateToStringUtils {
	
	public static String ConvertDateToString(Date date) {
		if (null == date){
			return "";
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String dateString = sdf.format(date);
		return dateString;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	

}
