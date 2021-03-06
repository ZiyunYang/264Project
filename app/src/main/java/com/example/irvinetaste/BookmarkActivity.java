package com.example.irvinetaste;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.irvinetaste.utils.DBThread;

import java.util.function.Consumer;

public class BookmarkActivity extends AppCompatActivity {

    private RecyclerView bookmarks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark);
        bookmarks = findViewById(R.id.bookmarks);
        bookmarks.setLayoutManager(new LinearLayoutManager(this));
        bookmarks.setAdapter(new BookmarkAdapter(this));
    }
}