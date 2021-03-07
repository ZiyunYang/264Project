package com.example.irvinetaste.utils;

import com.example.irvinetaste.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBThread implements Runnable {

    int jobId;
    Object argument;
    Runnable[] jobs = {() -> changeUserName((String) argument), () -> addBookmark((String) argument), () -> removeBookmark((String) argument)};;

    public DBThread(int id, Object arg) {
        jobId = id;
        argument = arg;
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

    //TODO
    public void ChangeUserPwd(String phoneNumber, String password){

    }

    public void addUser(String phoneNumber, String password, String userName){

    }

    @Override
    public void run() { jobs[jobId].run(); }
}