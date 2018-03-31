package com.lcdt.contract.web.utils;

import org.tl.commons.util.DateUtility;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 生成流水单号
 * @author Sheng-ji Lau
 * @date 2018年3月9日下午3:53:25
 * @version
 */
public class SerialNumAutoGenerator {
	
	public static String serialNoGenerate() {
		StringBuffer str=new StringBuffer();
		
		Date date=new Date();
		SimpleDateFormat sdf= new SimpleDateFormat("yyyyMMdd");
		str.append(sdf.format(date));
		
		Calendar c = Calendar.getInstance();
	    int minute=c.get(Calendar.MINUTE);//分
	    int hour=c.get(Calendar.HOUR_OF_DAY);//小时
	    int second=c.get(Calendar.SECOND);//秒
	    Long no=(long) (hour*3600+minute*60+second);
	    DecimalFormat df=new DecimalFormat("00000");
	    str.append(df.format(no));
		
		return str.toString();
	}
	public static String getSerialNoById(Long id){
		return DateUtility.date2String(new Date(),"yyyyMMdd") + id;
	}
}
