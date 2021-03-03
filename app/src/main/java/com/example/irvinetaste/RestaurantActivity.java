package com.example.irvinetaste;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.irvinetaste.utils.DBThread;

import java.util.function.Consumer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestaurantActivity extends AppCompatActivity {

    private RecyclerView nearbyView;
    private Button btn;
    private NearbyAdapter nearbyAdapter;
    static Retrofit retrofit = null;
    static final String BASE_URL = "https://api.yelp.com/v3/";
    static final String TAG = WelcomeActivity.class.getSimpleName();
    private int id;
    private Restaurant restaurant;
    private String url;
    private String name;
    private String phone;
    private Float rating;
    private String price;
    private Location location;
    private Context ctx = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);

        Intent intent = getIntent();
        id = Integer.parseInt(intent.getStringExtra("id"));
        btn = this.findViewById(R.id.bookmark);
        connect();

    }

    private void connect() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        RestaurantApiService restaurantApiService = retrofit.create(RestaurantApiService.class);
        Call<Restaurant> call = restaurantApiService.getRestaurantByID(id);
        call.enqueue(new Callback<Restaurant>() {
            @Override
            public void onResponse(Call<Restaurant> call, Response<Restaurant> response) {
                System.out.println("Restaurant: " + response.body().getName());
                restaurant = response.body();
                url = restaurant.getImgUrl();
                name = restaurant.getName();
                phone = restaurant.getDisplayPhone();
                rating = restaurant.getRating();
                price = restaurant.getPrice();
                location = restaurant.getLocation();
                onClick(btn, (c) -> addBookmark((int) c), (c) -> removeBookmark((int) c), id);
                nearbyView = findViewById(R.id.nearby);
                nearbyView.setLayoutManager(new LinearLayoutManager(ctx));
                nearbyView.setAdapter(nearbyAdapter);
            }

            @Override
            public void onFailure(Call<Restaurant> call, Throwable throwable) {
                Log.e(TAG, throwable.toString());
            }
        });
    }

    public void addBookmark(int id) {
        DBThread dbThread = new DBThread(0, id);
        Thread thread = new Thread(dbThread);
        thread.start();
    }

    public void removeBookmark(int id) {
        DBThread dbThread = new DBThread(0, id);
        Thread thread = new Thread(dbThread);
        thread.start();
    }

    public void onClick(Button btn, Consumer c1, Consumer c2, int id) {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btn.getText().toString().equals("Bookmark")) {
                    User.addBookmark(id, name, url);
                    btn.setText("Unbookmark");
                    c1.accept(id);
                } else if (btn.getText().toString().equals("Unbookmark")) {
                    Bookmark rm = null;
                    for (Bookmark bm : User.getBookmarks()) { if (bm.getRestaurantId() == id) { rm = bm; break; } }
                    User.removeBookmark(rm);
                    c2.accept(id);
                }
            }
        });
    }
}