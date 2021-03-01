package com.example.irvinetaste;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.irvinetaste.utils.MongoDBUtil;

public class UserProfileActivity extends AppCompatActivity {

    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userprofile);
        MongoDBUtil dbUtil = new MongoDBUtil("irvinetaste");
//        user = dbUtil.getCollection("user");
//        EditText usrname = findViewById(R.id.editTextTextPersonName);
//        EditText usrphone = findViewById(R.id.editTextPhone);
//        usrname.setText(user.getUsername());
//        usrphone.setText(user.getPhoneNum());
    }

    public void goToBookmark(View view) {
        Intent intent = new Intent(this, BookmarkActivity.class);
        startActivity(intent);
    }
}