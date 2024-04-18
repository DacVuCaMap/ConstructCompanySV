package com.app.ConStructCompany.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeUtils {
    public DateTimeUtils(){}

    public static Date getCurrentDate(){
        return new Date();
    }
    public static String getDateFormat(String pattern) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        return dateFormat.format(getCurrentDate());
    }
}
