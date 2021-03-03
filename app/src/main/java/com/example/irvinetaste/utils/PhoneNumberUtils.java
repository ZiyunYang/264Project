package com.example.irvinetaste.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author zhangzhenyu
 * @date 2021/2/24 下午12:30
 */
public class PhoneNumberUtils {

    /**
     * regex match phone number:
     */
    public static boolean checkTel(String tel){
        Pattern p = Pattern.compile("^(1\\s?)?(\\(\\d{3}\\)|\\d{3})\\s?-?\\d{3}-?\\s?\\d{4}$");
        Matcher matcher = p.matcher(tel);
        return matcher.matches();
    }
}
