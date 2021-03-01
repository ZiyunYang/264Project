package com.example.irvinetaste;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class BookmarkActivity extends AppCompatActivity {

    private RecyclerView bookmarks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark);
        bookmarks = findViewById(R.id.bookmarks);
        bookmarks.setAdapter(new BookmarkAdapter());
    }
}