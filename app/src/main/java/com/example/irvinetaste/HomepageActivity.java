package com.example.irvinetaste;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomepageActivity extends AppCompatActivity {
    static final String TAG = HomepageActivity.class.getSimpleName();
    static final String BASE_URL = "https://api.yelp.com/v3/businesses";
    static Retrofit retrofit = null;
    final static String API_KEY = "Bearer qAqJsBlux6FpnHE_73tuCnL-ysWNEz1LNA_udit4Zbxhy-" +
            "VyzApCFk8U1704B1FrufOGYRdLgzCceyMEBcATpHgr1rfAfrRlO7dUaac8iJiE-0MvuPBxkXoEMQgzYHYx";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        connect();

    }
    private void connect(){
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        RestaurantApiService restaurantApiService = retrofit.create(RestaurantApiService.class);

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