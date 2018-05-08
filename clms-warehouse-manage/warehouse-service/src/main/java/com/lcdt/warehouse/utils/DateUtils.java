package com.lcdt.warehouse.utils;

import org.tl.commons.util.DateUtility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by ybq on 2018/2/17.
 */
public class DateUtils extends DateUtility {

    /***
     * 时间戳转为日期类型
     * @param timestamp
     * @return
     */
    public static String stampToDate(Long timestamp){
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(timestamp);
        res = simpleDateFormat.format(date);
        return res;
    }


    public static String stampToDate1(Long timestamp){
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(timestamp);
        res = simpleDateFormat.format(date);
        return res;
    }

    /***
     * 获取当前时间上月的最后一天日期
     * @param timestamp
     * @return
     */
    public static String stampToDate2(Long timestamp){
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(timestamp);
        date = DateUtility.getDateAfterMonths(date,-1);
        res = simpleDateFormat.format(DateUtility.getDateOfMonthLast(DateUtility.getYear(date), DateUtility.getMonth(date)));
        return res;
    }





}
