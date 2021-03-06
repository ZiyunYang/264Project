package com.example.irvinetaste;

import android.util.Log;

import com.example.irvinetaste.utils.DataSet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class User {
    static int userId;
    static String username;
    static String phoneNum;
    static String password;
    static List<Bookmark> bookmarks = new LinkedList<>();

    public static int getUserId() { return userId; }

    public static void setUserId(int id) {
        userId = id;
        fetchBookmarks();
    }

    public static String getUsername() { return username; }

    public static void setUsername(String name) { username = name; }

    public static String getPhoneNum() { return phoneNum; }

    public static void setPhoneNum(String number) { phoneNum = number; }

    public static String getPassword() { return password; }

    public static void setPassword(String pwd) { password = pwd; }

    public static void fetchBookmarks() {
        Connection conn;
        try {
            Class.forName(DataSet.getDriver());
            conn = DriverManager.getConnection(DataSet.getUrl(), DataSet.getUser(), DataSet.getPassword());
            Statement stmt = conn.createStatement();
            ResultSet resultSet = stmt.executeQuery(String.format("SELECT restaurantId FROM bookmark WHERE userId = %d", User.getUserId()));
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://api.yelp.com/v3/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            RestaurantApiService restaurantApiService = retrofit.create(RestaurantApiService.class);
            while (resultSet.next()) {
                String restid = resultSet.getString(1);
                Call<Restaurant> call = restaurantApiService.getRestaurantByID(restid);
                call.enqueue(new Callback<Restaurant>() {
                    @Override
                    public void onResponse(Call<Restaurant> call, Response<Restaurant> response) {
                        Restaurant restaurant = response.body();
                        Bookmark bm = new Bookmark(restaurant.getId(), restaurant.getName(), restaurant.getImgUrl());
                        bookmarks.add(bm);
                    }

                    @Override
                    public void onFailure(Call<Restaurant> call, Throwable throwable) {
                        Log.e(RestaurantActivity.class.getSimpleName(), throwable.toString());
                    }
                });
            }
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<Bookmark> getBookmarks() { return bookmarks; }

    public static void addBookmark(String id, String n, String url) { bookmarks.add(new Bookmark(id, n, url)); }

    public static void removeBookmark(Bookmark bm) { bookmarks.remove(bm); }

}

