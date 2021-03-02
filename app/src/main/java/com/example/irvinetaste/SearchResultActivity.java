package com.example.irvinetaste;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
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
    private String sort;
    private String sort_by;
    private AlertDialog alertDialog;
    private Button sortBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        Intent intent = getIntent();
        queryString = intent.getStringExtra("searchQuery");
        query = findViewById(R.id.search_result_bar);
        query.setQueryHint(queryString);

        sort = intent.getStringExtra("sort");
        if (sort == null) {
            sort = "Recommended";
        }
        switch(sort){
            case "Distance":
                sort_by = "distance";
                break;
            case "Rating":
                sort_by = "rating";
                break;
            case "Most Reviewed":
                sort_by = "review_count";
                break;
            default:
                sort_by = "best_match";
        }
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
        Call<SearchRestaurantResponse> call = restaurantApiService.getRestaurantsByLocation(queryString, Location, sort_by);

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

    public void showSortInSearch(View view){
        final String[] items = {"Recommended", "Distance", "Rating", "Most Reviewed"};
        final List<String> itemsList = Arrays.asList(items);
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        int index = itemsList.indexOf(sort);
        if (index == -1) index = 0;
        alertBuilder.setSingleChoiceItems(items, index, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(SearchResultActivity.this, items[i], Toast.LENGTH_SHORT).show();
                alertDialog.dismiss();
                refresh(items[i]);
            }
        });

        alertDialog = alertBuilder.create();
        alertDialog.show();
    }

    public void refresh(String item) {
        finish();
        Intent intent = new Intent(this, SearchResultActivity.class);
        intent.putExtra("searchQuery", queryString);
        intent.putExtra("sort", item);
        startActivity(intent);
    }

    public void search2(View view){
        query = findViewById(R.id.search_result_bar);
        queryString = query.getQuery().toString();
        Intent intent = new Intent(this, SearchResultActivity.class);
        intent.putExtra("searchQuery", queryString);
        startActivity(intent);
    }
}