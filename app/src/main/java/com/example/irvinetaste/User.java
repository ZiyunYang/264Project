package com.example.irvinetaste;

import com.google.gson.annotations.SerializedName;

import java.util.LinkedList;
import java.util.List;

public class User {
    static int userId;
    static String username;
    static String phoneNum;
    static String password;
    static List<Bookmark> bookmarks;

    public static int getUserId() {
        return userId;
    }

    public static void setUserId(int userId) {
        userId = userId;
    }

    public static String getUsername() { return username; }

    public static void setUsername(String username) {
        username = username;
    }

    public static String getPhoneNum() { return phoneNum; }

    public static void setPhoneNum(String phoneNum) {
        phoneNum = phoneNum;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        password = password;
    }

    public static void setBookmarks(List<String> bookmarks) {
        bookmarks = bookmarks;
    }

    public static List<Bookmark> getBookmarks() { return bookmarks; }

    public static void addBookmark(int id, String n, String url) { bookmarks.add(new Bookmark(id, n, url)); }

    public static void removeBookmark(Bookmark bm) { bookmarks.remove(bm); }

}
