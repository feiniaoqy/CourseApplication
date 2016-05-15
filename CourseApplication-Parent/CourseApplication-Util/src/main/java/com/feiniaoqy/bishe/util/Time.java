package com.feiniaoqy.bishe.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by asus on 2016/5/14.
 */
public class Time {
    public static String getTime(){
        Date date  = new Date();
        SimpleDateFormat ss = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = ss.format(date);
        return dateString;
    }
}
