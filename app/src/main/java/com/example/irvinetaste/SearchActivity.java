package com.example.irvinetaste;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;

public class SearchActivity extends AppCompatActivity {

    private SearchView query;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        query = findViewById(R.id.search_bar);
    }

    public void clickSearch(View view){
        String queryString = query.getQuery().toString();
        Intent intent = new Intent(this, SearchResultActivity.class);
        intent.putExtra("searchQuery", queryString);
        startActivity(intent);
    }

    public void clickBack(View view){
        Intent intent = new Intent(this, HomepageActivity.class);
        startActivity(intent);
    }

}