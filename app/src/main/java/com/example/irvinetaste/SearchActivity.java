package com.example.irvinetaste;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
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
        query.setOnQueryTextListener(queryTextListener);
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

    SearchView.OnQueryTextListener queryTextListener
            = new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String s) {

            Intent intent = new Intent(SearchActivity.this, SearchResultActivity.class);
            intent.putExtra("searchQuery", s);
            SearchActivity.this.startActivity(intent);
            return true;
        }

        @Override
        public boolean onQueryTextChange(String s) {
            return false;
        }
    };

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(SearchActivity.this, HomepageActivity.class);
        SearchActivity.this.startActivity(intent);
        finish();
    }

}