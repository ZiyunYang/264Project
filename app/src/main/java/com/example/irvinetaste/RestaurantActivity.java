package com.example.irvinetaste;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class RestaurantActivity extends AppCompatActivity {

    private RecyclerView nearby;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);
<<<<<<< HEAD

        //    get all details about this restaurant
        TextView rating = findViewById(R.id.rating);
        TextView cost = findViewById(R.id.cost);
        menu = findViewById(R.id.menu);
        nearby = findViewById(R.id.nearby);
        rating.setText("");
        cost.setText("");
        nearby.setAdapter(new nearbyAdapter());
=======
        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        System.out.println("id---"+id);

>>>>>>> develop
    }
}