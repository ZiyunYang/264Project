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
        Pattern p = Pattern.compile("^[1][3,4,5,7,8,9][0-9]{10}$");
        Matcher matcher = p.matcher(tel);
        return matcher.matches();
    }
}
