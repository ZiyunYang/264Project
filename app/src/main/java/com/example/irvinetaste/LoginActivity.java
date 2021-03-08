package com.example.irvinetaste;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.irvinetaste.utils.DataSet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/*
need to place some little icon before each line
bug: first login fail and then enter true input , will fail too. Same phone Number  fixed on 2021.03.07
 */
public class LoginActivity extends AppCompatActivity {

    private Button loginButton;
    private EditText usernameText;
    private EditText passwordText;
    private Boolean canLogin = false;


    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);

        loginButton = (Button) findViewById(R.id.login);
        usernameText = (EditText) findViewById(R.id.username);
        passwordText = (EditText) findViewById(R.id.password);

        loginButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                LoginThread loginThread = new LoginThread();
                Thread thread = new Thread(loginThread);
                thread.start();

                try {
                    thread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


                System.out.println("canLogin is " + canLogin);

                if(canLogin){
                    Toast.makeText(LoginActivity.this,"login successfully", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(LoginActivity.this, PositionAuthActivity.class);
                    startActivity(intent);
                }else{
                    AlertDialog textTips = new AlertDialog.Builder(LoginActivity.this)
                            .setTitle("Warning:")
                            .setMessage("Wrong phone number or wrong password, Please input again")
                            .create();
                    textTips.show();
                }


            }
        });


    }

    class LoginThread implements Runnable{

        private String driver = DataSet.getDriver();
        private String url = DataSet.getUrl();
        private String user = DataSet.getUser();
        private String password = DataSet.getPassword();

        public Connection getConnection(){
            Connection conn = null;
            try{
                Class.forName(driver);
                conn = DriverManager.getConnection(url,user,password);

            }catch (Exception e){
                e.printStackTrace();
            }
            return conn;
        }
        @Override
        public void run() {
            Connection conn = getConnection();

            try{
                //create Statement object
                Statement stmt = conn.createStatement();
                //execute sql and get a ResultSet
                String sql = "SELECT * FROM user where phoneNumber = '"+usernameText.getText().toString()+"' and password = '"+passwordText.getText().toString()+"'";
                System.out.println(sql);
                ResultSet rs = stmt.executeQuery(sql);
                System.out.println();

                if (rs.next()) {
                    //login success
                    canLogin = true;
                    System.out.println("the login successed");

//                    System.out.println("userId = " + rs.getInt("userId"));
                    //set the attribute of static User
                    User.setUserId(rs.getInt("userId"));
                    User.setPhoneNum(rs.getString("phoneNumber"));
                    User.setUsername(rs.getString("userName"));
                    User.setPassword(rs.getString("password"));
//                    System.out.println("((((()))))" + User.getUserId());

                } else {
                    System.out.println("the login failed");
                    //login fails
                    canLogin = false;
                }

                rs.close();
                stmt.close();
                conn.close();

            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }



}
