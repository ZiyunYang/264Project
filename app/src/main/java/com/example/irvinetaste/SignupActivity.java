package com.example.irvinetaste;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.irvinetaste.utils.DataSet;
import com.example.irvinetaste.utils.PhoneNumberUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class SignupActivity extends AppCompatActivity {

    private Button verifyCodeBtn,signUpBtn;
    private EditText userNameEdt, verifyCodeEdt, passwordEdt, phoneNumberEdt;
    private String phoneNumber, code;

    private boolean canSignUp = false;
    protected static String signUp_state = "200";

    //TODO need to add SMS SDK

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        verifyCodeBtn = findViewById(R.id.verifycode);
        signUpBtn = findViewById(R.id.signup);
        userNameEdt = findViewById(R.id.signupUsername);
        verifyCodeEdt = findViewById(R.id.verifycodetext);
        passwordEdt = findViewById(R.id.password);
        phoneNumberEdt = findViewById(R.id.phoneNumber);

        signUpBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                SignUpThread signUpThread = new SignUpThread();
                Thread thread = new Thread(signUpThread);
                thread.start();
                for(;signUp_state.equals("200");){

                }
                if(canSignUp){
                    Toast.makeText(SignupActivity.this,"SignUp successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SignupActivity.this, PositionAuthActivity.class);

                    startActivity(intent);
                }else{
                    AlertDialog textTips = new AlertDialog.Builder(SignupActivity.this)
                            .setTitle("Warning:")
                            .setMessage("Phone number has been existed or number not format, Please input again")
                            .create();
                    textTips.show();
                }
            }
        });
    }

    class SignUpThread implements Runnable{
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
                String sql = "SELECT * FROM user where phoneNumber = '"+userNameEdt.getText().toString()+"'";
                ResultSet rs = stmt.executeQuery(sql);

                //need to use PhoneNumberUtils to judge whether valid number
                if (rs.next()) {
                    //login success
                    canSignUp = false;
                } else {
                    //login fails
                    canSignUp = true;
                    String insertSql = "INSERT INTO user (userName,phoneNumber,password) values ('"+userNameEdt.getText().toString()+"', '"+phoneNumberEdt.getText().toString()+"', '"+passwordEdt.getText().toString()+"')";
                    stmt.execute(insertSql);

                }
                conn.close();
                stmt.close();


            }catch (Exception e){
                e.printStackTrace();
            }

            SignupActivity.signUp_state = "1";

        }
    }

}
