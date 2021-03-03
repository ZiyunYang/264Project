package com.example.irvinetaste.utils;

import com.example.irvinetaste.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBThread implements Runnable {

    Connection conn;
    int jobId;
    Object argument;
    Runnable[] jobs = {() -> changeUserName((String) argument), () -> addBookmark((int) argument), () -> removeBookmark((int) argument)};;

    public DBThread(int id, Object arg) {
        jobId = id;
        argument = arg;
        conn = getConn();
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
            Statement stmt = conn.createStatement();
            stmt.execute(String.format("UPDATE user SET userName = \'%s\' WHERE userId = %d", n, User.getUserId()));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void addBookmark(int id) {
        try {
            Statement stmt = conn.createStatement();
            stmt.execute(String.format("INSERT INTO bookmark (userId, restaurantId) VALUES (%d, %d)", User.getUserId(), id));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void removeBookmark(int id) {
        try {
            Statement stmt = conn.createStatement();
            stmt.execute(String.format("DELETE FROM bookmark WHERE userId = %d and restaurantId = %d", User.getUserId(), id));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void run() { jobs[jobId].run(); }
}