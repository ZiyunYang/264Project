package com.example.irvinetaste;

import java.util.LinkedList;
import java.util.List;

public class User {
    int userId;
    String username;
    String phoneNum;
    String password;
    List<String> bookmarks;

    public User(String name, String phone, String pw) {
        username = name;
        phoneNum = phone;
        password = pw;
        bookmarks = new LinkedList<>();
    }


    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() { return username; }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhoneNum() { return phoneNum; }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setBookmarks(List<String> bookmarks) {
        this.bookmarks = bookmarks;
    }


    public List<String> getBookmarks() { return bookmarks; }

    public void addBookmark(String id) { this.bookmarks.add(id); }

    public void removeBookmark(String id) { this.bookmarks.remove(id); }

}
