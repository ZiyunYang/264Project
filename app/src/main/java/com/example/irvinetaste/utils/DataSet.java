package com.example.irvinetaste.utils;

/**
 * @author zhangzhenyu
 * @date 2021/2/23 下午9:46
 */
public class DataSet {
    private static String driver = "com.mysql.jdbc.Driver";
    private static String url = "jdbc:mysql://database-1.cwhr2wn7cj97.us-west-1.rds.amazonaws.com:3306/irvinetaste";
    private static String user = "root";
    private static String password = "12345678";

    public static String getDriver(){
        return driver;
    }

    public static String getUrl(){
        return url;
    }

    public static String getUser(){
        return user;
    }

    public static String getPassword(){
        return password;
    }


}
