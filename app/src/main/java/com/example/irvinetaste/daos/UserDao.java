package com.example.irvinetaste.daos;

import android.content.Context;

import com.example.irvinetaste.utils.MongoDBUtil;

/**
 * @author zhangzhenyu
 * @date 2021/2/23 下午2:45
 */
public class UserDao {

    private static MongoDBUtil dbHelper;

    public UserDao(Context context){

        try{
            dbHelper = new MongoDBUtil("irvinetaste");
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public void createCollection(String collName){
        dbHelper.createCollection(collName);
    }

    public void insertUser(){

    }


    public void insert(String phoneNumber, String userName, String password){

    }


}
