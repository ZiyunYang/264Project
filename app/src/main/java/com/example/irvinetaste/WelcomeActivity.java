package com.example.irvinetaste;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
    }

    public void onClickSignUp(View view){
        // just for test
        Intent intent = new Intent(this,SignupActivity.class);
        startActivity(intent);
    }

    public void onClickSignIn(View view){
        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);
    }

    public void onClickForget(View view){
        Intent intent = new Intent(this,ForgetAccountActivity.class);
        startActivity(intent);
    }


}