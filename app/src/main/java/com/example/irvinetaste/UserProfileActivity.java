package com.example.irvinetaste;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.irvinetaste.utils.MongoDBUtil;

public class UserProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userprofile);
        MongoDBUtil dbUtil = new MongoDBUtil("irvinetaste");
        User user = dbUtil.getCollection("user");
        EditText usrname = findViewById(R.id.editTextTextPersonName);
        EditText usrphone = findViewById(R.id.editTextPhone);
        usrname.setText(user.getUsername());
        usrphone.setText(user.getPhoneNum());
    }

    public void goToBookmark(View view) {

    }
}