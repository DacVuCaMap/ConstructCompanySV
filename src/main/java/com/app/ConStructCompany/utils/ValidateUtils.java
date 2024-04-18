package com.app.ConStructCompany.utils;

import org.springframework.util.ObjectUtils;

import java.util.regex.Pattern;

public class ValidateUtils {
    private static final Pattern NUMBER_PATTERN = Pattern.compile("^[0-9]+$");

    public ValidateUtils(){}

    public static boolean isValidStringOfNumber(String input){
        if (ObjectUtils.isEmpty(input)){
            return false;
        }

        return NUMBER_PATTERN.matcher(input).matches();
    }

    public static boolean isValidDouble(String input) {
        try {
            Double.parseDouble(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
