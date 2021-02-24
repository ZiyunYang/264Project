package com.example.irvinetaste;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.irvinetaste.daos.UserDao;
import com.example.irvinetaste.utils.DataSet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class LoginActivity extends AppCompatActivity {

    protected static String signIn_state = "200";
    private Button loginButton;
    private EditText usernameText;
    private EditText passwordText;

    private boolean can_login = false;

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);


        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);

        loginButton = (Button) findViewById(R.id.login);
        usernameText = (EditText) findViewById(R.id.username);
        passwordText = (EditText) findViewById(R.id.password);
        can_login = false;


        loginButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                usernameText = (EditText) findViewById(R.id.username);
                passwordText = (EditText) findViewById(R.id.password);

                SignInThread signInThread = new SignInThread();
                Thread thread = new Thread(signInThread);
                thread.start();

                for(;signIn_state.equals("200");){

                }

                System.out.println("before the switch" + can_login);
                if(can_login){
                    Toast.makeText(LoginActivity.this,"login successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, PositionAuthActivity.class);

                    startActivity(intent);
                }else{
                    AlertDialog textTips = new AlertDialog.Builder(LoginActivity.this)
                            .setTitle("Warn:")
                            .setMessage("Wrong phone number or wrong password, Please input again")
                            .create();

                    textTips.show();

                }

            }
        });


    }

    private class SignInThread implements Runnable{

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
                System.out.println(usernameText.getText().toString());
                System.out.println(passwordText.getText().toString());
                String sql = "SELECT * FROM user where phoneNumber = '"+usernameText.getText().toString()+"' and password = '"+passwordText.getText().toString()+"'";
                ResultSet rs = stmt.executeQuery(sql);



                if (rs.next()) {
                    can_login = true;
                } else {
                    //login fails
                    can_login = false;
                }

                rs.close();
                stmt.close();
                conn.close();

            }catch (Exception e){
                e.printStackTrace();
            }

            signIn_state = "1";
        }
    }



}
