package com.example.irvinetaste;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchResultActivity extends AppCompatActivity {


    static Retrofit retrofit = null;
    static String Location = "Irvine";
    static final String BASE_URL = "https://api.yelp.com/v3/";
    static final String TAG = WelcomeActivity.class.getSimpleName();

    private SearchRestaurantListAdapter restaurantListAdapter;
    private RecyclerView recyclerView;
    private String queryString;
    private SearchView query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        Intent intent = getIntent();
        queryString = intent.getStringExtra("searchQuery");
        query = findViewById(R.id.search_result_bar);
        query.setQueryHint(queryString);

        List<Restaurant> restaurants = new ArrayList<>();
        restaurantListAdapter = new SearchRestaurantListAdapter(restaurants);
        connect();
        // recyclerView
        recyclerView = findViewById(R.id.rvSearchRestaurantList);
        // set layout
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        // set adapter
        recyclerView.setAdapter(restaurantListAdapter);

    }

    private void connect() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        RestaurantApiService restaurantApiService = retrofit.create(RestaurantApiService.class);
        Call<SearchRestaurantResponse> call = restaurantApiService.getRestaurantsByLocation(queryString, Location);

        call.enqueue(new Callback<SearchRestaurantResponse>() {
            @Override
            public void onResponse(Call<SearchRestaurantResponse> call, Response<SearchRestaurantResponse> response) {
                System.out.println("Restaurant: " + response.body().getRestaurantList());
                restaurantListAdapter.updateList(response.body().getRestaurantList());
            }

            @Override
            public void onFailure(Call<SearchRestaurantResponse> call, Throwable throwable) {
                Log.e(TAG, throwable.toString());
            }
        });
    }

    public void clickSearchBar(View view){
        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);
        System.out.println("i am back");
    }

    public void clickSearchRestaurant(View view) {
        Intent intent = new Intent(this, Restaurant.class);
        startActivity(intent);
    }
}