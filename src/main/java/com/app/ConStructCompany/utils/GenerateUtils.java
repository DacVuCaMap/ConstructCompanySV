package com.app.ConStructCompany.utils;

public class GenerateUtils {
    public static final String DEFAULT_ORDER_NUMBER = "1";
    public static final int DEFAULT_NEXT_ORDER_NUMBER = 1;
    public static final String DEFAULT_ORDER_CODE_PATTERN = "ddMMyy";
    public GenerateUtils(){}

    public static String generateOrderCode(Long lastId){
        String nextOrderNumber = DEFAULT_ORDER_NUMBER;

        String currentDateFormat = DateTimeUtils.getDateFormat(DEFAULT_ORDER_CODE_PATTERN);
        if (lastId != null) {
            nextOrderNumber = String.valueOf(lastId + DEFAULT_NEXT_ORDER_NUMBER);
        }
        return  currentDateFormat + nextOrderNumber;
    }
}
