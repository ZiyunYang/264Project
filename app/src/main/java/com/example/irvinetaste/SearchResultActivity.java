package com.example.irvinetaste;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;

public class SearchResultActivity extends AppCompatActivity {

    private String queryString;
    private SearchView query;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result_actity);
        Intent intent = getIntent();
        queryString = intent.getStringExtra("searchQuery");
        query = findViewById(R.id.search_result_bar);

        query.setQueryHint(queryString);
    }

    public void clickSearchBar(View view){
        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);
        System.out.println("i am back");
    }

}