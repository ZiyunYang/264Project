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
import android.widget.ImageView;
import android.widget.TextView;

import com.example.irvinetaste.utils.DBThread;
import com.squareup.picasso.Picasso;

import java.util.function.Consumer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestaurantActivity extends AppCompatActivity {

    private RecyclerView nearbyView;
    private Button btn;
    static Retrofit retrofit = null;
    static final String BASE_URL = "https://api.yelp.com/v3/";
    static final String TAG = RestaurantActivity.class.getSimpleName();
    private Restaurant restaurant;
    private String id;
    private String url;
    private String restName;
    private ImageView image;
    private TextView name;
    private TextView address;
    private TextView phone;
    private TextView rating;
    private TextView price;
    private Context ctx = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        btn = this.findViewById(R.id.bookmark);
        image = this.findViewById(R.id.restImage);
        name = this.findViewById(R.id.name);
        address = this.findViewById(R.id.address);
        phone = this.findViewById(R.id.phone);
        rating = this.findViewById(R.id.rating);
        price = this.findViewById(R.id.cost);
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
                restaurant = response.body();
                url = restaurant.getImgUrl();
                Picasso.get().load(url).into(image);
                restName = restaurant.getName();
                name.setText(restName);
                address.setText(restaurant.getLocation());
                phone.setText(restaurant.getDisplayPhone());
                rating.setText(restaurant.getRating().toString());
                price.setText(restaurant.getPrice());
                btn.setText("Pin");
                for (Bookmark bm : User.getBookmarks()) {
                    if (bm.getRestaurantId().equals(restaurant.getId())) {
                        btn.setText("Unpin");
                        break;
                    }
                }
                onClick(btn, (c) -> addBookmark((String) c), (c) -> removeBookmark((String) c), id);
                nearbyView = findViewById(R.id.nearby);
                nearbyView.setLayoutManager(new LinearLayoutManager(ctx));
                Call<NearbyRestaurants> neabyList = restaurantApiService.getRecommend(restaurant.getLatitude(), restaurant.getLongtitude());
                neabyList.enqueue(new Callback<NearbyRestaurants>() {
                    @Override
                    public void onResponse(Call<NearbyRestaurants> call, Response<NearbyRestaurants> response) {
                        nearbyView.setAdapter(new NearbyAdapter(response.body(), ctx));
                    }

                    @Override
                    public void onFailure(Call<NearbyRestaurants> call, Throwable t) {
                        Log.e(TAG, t.toString());
                    }
                });
            }

            @Override
            public void onFailure(Call<Restaurant> call, Throwable throwable) {
                Log.e(TAG, throwable.toString());
            }
        });
    }

    public void addBookmark(String id) {
        DBThread dbThread = new DBThread(1, id);
        Thread thread = new Thread(dbThread);
        thread.start();
    }

    public void removeBookmark(String id) {
        DBThread dbThread = new DBThread(2, id);
        Thread thread = new Thread(dbThread);
        thread.start();
    }

    public void onClick(Button btn, Consumer c1, Consumer c2, String id) {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btn.getText().toString().equals("Pin")) {
                    User.addBookmark(id, restName, url);
                    btn.setText("Unpin");
                    c1.accept(id);
                } else if (btn.getText().toString().equals("Unpin")) {
                    Bookmark rm = null;
                    for (Bookmark bm : User.getBookmarks()) { if (bm.getRestaurantId().equals(id)) { rm = bm; break; } }
                    User.removeBookmark(rm);
                    btn.setText("Pin");
                    c2.accept(id);
                }
            }
        });
    }
}
