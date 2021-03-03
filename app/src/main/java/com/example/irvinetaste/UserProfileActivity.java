package com.example.irvinetaste;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.irvinetaste.utils.DBThread;

import java.util.function.Consumer;

public class UserProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userprofile);
        EditText usrname = findViewById(R.id.editTextTextPersonName);
        EditText usrphone = findViewById(R.id.editTextPhone);
        usrname.setText(User.getUsername());
        usrphone.setText(User.getPhoneNum());
        onChange(usrname, (c) -> changeUserName((String) c));
    }

    public void goToBookmark(View view) {
        Intent intent = new Intent(this, BookmarkActivity.class);
        startActivity(intent);
    }

    public void changeUserName(String name) {
        DBThread dbThread = new DBThread(0, name);
        Thread thread = new Thread(dbThread);
        thread.start();
    }

    public void onChange(EditText edt, Consumer c) {
        edt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    User.setUsername(edt.getText().toString());
                    c.accept(edt.getText().toString());
                }
            }
        });
    }
}