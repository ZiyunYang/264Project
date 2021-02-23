package com.example.irvinetaste;

import java.util.LinkedList;
import java.util.List;

public class User {
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

    public String getUsername() { return username; }

    public String getPhoneNum() { return phoneNum; }

    public List<String> getBookmarks() { return bookmarks; }

    public void addBookmark(String id) { this.bookmarks.add(id); }

    public void removeBookmark(String id) { this.bookmarks.remove(id); }

}
