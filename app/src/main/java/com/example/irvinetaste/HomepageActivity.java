package com.example.irvinetaste;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomepageActivity extends AppCompatActivity {
    static final String TAG = HomepageActivity.class.getSimpleName();
    static final String BASE_URL = "https://api.yelp.com/v3/businesses";
    static Retrofit retrofit = null;
    List<Restaurant> restaurants;
    final static String API_KEY = "Bearer qAqJsBlux6FpnHE_73tuCnL-ysWNEz1LNA_udit4Zbxhy-" +
            "VyzApCFk8U1704B1FrufOGYRdLgzCceyMEBcATpHgr1rfAfrRlO7dUaac8iJiE-0MvuPBxkXoEMQgzYHYx";
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        connect( 0.0,0.0);

    }
    private void connect(double latitude, double longtitude){
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        RestaurantApiService restaurantApiService = retrofit.create(RestaurantApiService.class);
        recyclerView = findViewById(R.id.rvRestaurantList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Call<List<Restaurant>>  call= restaurantApiService.getRestaurants("",37.786882,-122.399972,5,"",50,50,"",false);
        call.enqueue(new Callback<List<Restaurant>>() {
            @Override
            public void onResponse(Call<List<Restaurant>> call, Response<List<Restaurant>> response) {
                restaurants = response.body();
                recyclerView.setAdapter(new RestaurantListAdapter(restaurants));
            }

            @Override
            public void onFailure(Call<List<Restaurant>> call, Throwable throwable) {
                Log.e(TAG, throwable.toString());
            }
        });

    }

    public void onClickSearch(View view){
        Intent intent = new Intent(this,SearchActivity.class);
        startActivity(intent);
    }

    public void onClickProfile(View view){
        Intent intent = new Intent(this,UserProfileActivity.class);
        startActivity(intent);
    }
}