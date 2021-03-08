package com.example.irvinetaste.utils;

import com.example.irvinetaste.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBThread implements Runnable {

    int jobId;
    Object argument;
    Object phoneNumber,password,userName;
    Runnable[] jobs = {() -> changeUserName((String) argument), () -> addBookmark((String) argument), () -> removeBookmark((String) argument),
            () -> ChangeUserPwd((String)phoneNumber, (String)password), () -> addUser((String)phoneNumber,(String)password,(String)userName)};

    public DBThread(int id, Object arg) {
        jobId = id;
        argument = arg;
    }

    public DBThread(int id, Object arg1, Object arg2){
        jobId = id;
        phoneNumber = arg1;
        password = arg2;
    }

    public DBThread(int id, Object arg1, Object arg2, Object arg3){
        jobId = id;
        phoneNumber = arg1;
        password = arg2;
        userName = arg3;
    }

    public Connection getConn() {
        Connection conn = null;
        try{
            Class.forName(DataSet.getDriver());
            conn = DriverManager.getConnection(DataSet.getUrl(), DataSet.getUser(), DataSet.getPassword());

        }catch (Exception e){
            e.printStackTrace();
        }
        return conn;
    }

    public void changeUserName(String n) {
        try {
            Connection conn = getConn();
            Statement stmt = conn.createStatement();
            stmt.execute(String.format("UPDATE user SET userName = \'%s\' WHERE userId = %d", n, User.getUserId()));
            stmt.close();
            conn.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void addBookmark(String id) {
        try {
            Connection conn = getConn();
            Statement stmt = conn.createStatement();
            stmt.execute(String.format("INSERT INTO bookmark (userId, restaurantId) VALUES (%d, \'%s\')", User.getUserId(), id));
            stmt.close();
            conn.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void removeBookmark(String id) {
        try {
            Connection conn = getConn();
            Statement stmt = conn.createStatement();
            stmt.execute(String.format("DELETE FROM bookmark WHERE userId = %d and restaurantId = \'%s\'", User.getUserId(), id));
            stmt.close();
            conn.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void ChangeUserPwd(String phoneNumber, String password){
        try{
            Connection conn = getConn();
            Statement stmt = conn.createStatement();
            stmt.execute(String.format("UPDATE user SET password = \'%s\' WHERE phoneNumber = \'%s\'", password, phoneNumber));
            stmt.close();
            conn.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void addUser(String phoneNumber, String password, String userName){
        try{
            Connection conn = getConn();
            Statement stmt = conn.createStatement();
            stmt.execute(String.format("INSERT INTO user (userName,phoneNumber,password) values ('"+userName)+"', '"+phoneNumber+"', '"+password+"')");
            stmt.close();
            conn.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void run() { jobs[jobId].run(); }
}