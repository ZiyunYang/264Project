package com.example.irvinetaste;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.irvinetaste.utils.DBThread;
import com.example.irvinetaste.utils.DataSet;
import com.example.irvinetaste.utils.PhoneNumberUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

//implementation of phone verify: https://www.mob.com/wiki/detailed?wiki=SMSSDK_for_Android_kuaisujicheng&id=23
public class SignupActivity extends AppCompatActivity {

    private Button verifyCodeBtn,signUpBtn;
    private EditText userNameEdt, verifyCodeEdt, passwordEdt, phoneNumberEdt;
    private String phoneNumber, code;

    private boolean canSignUp = false;

    private EventHandler eh;

    //TODO need to add SMS SDK  verifyCodeBtn, VerifyCodeEdt, phoneNumberEdt

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

        eh = new EventHandler() {
            @Override
            public void afterEvent(int event, int result, Object data) {
                if (result == SMSSDK.RESULT_COMPLETE){
                    //回调完成
                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        //提交验证码成功
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(SignupActivity.this,"Verify Code correct",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }else if (event == SMSSDK.EVENT_GET_VOICE_VERIFICATION_CODE){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(SignupActivity.this,"voice verify sent",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE){
                        //获取验证码成功
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(SignupActivity.this,"Verify Code sent",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES){
                        Log.i("test","test");
                    }
                }else{
                    ((Throwable)data).printStackTrace();
                    Throwable throwable = (Throwable) data;
                    throwable.printStackTrace();
                    Log.i("1234",throwable.toString());
                    try {
                        JSONObject obj = new JSONObject(throwable.getMessage());
                        final String des = obj.optString("detail");
                        if (!TextUtils.isEmpty(des)){
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(SignupActivity.this,des,Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        SMSSDK.registerEventHandler(eh);

        verifyCodeBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                phoneNumber = phoneNumberEdt.getText().toString();
                if(!phoneNumber.isEmpty()){
                    if(PhoneNumberUtils.checkTel(phoneNumber)){ //利用正则表达式获取检验手机号
                        // 获取验证码
                        SMSSDK.getVerificationCode("1", phoneNumber);
                    }else{
                        Toast.makeText(getApplicationContext(),"Please input valid phone number",Toast.LENGTH_LONG).show();
                    }
                }else {
                    Toast.makeText(getApplicationContext(),"Please input your phone number",Toast.LENGTH_LONG).show();
                    return;
                }
                phoneNumber = phoneNumberEdt.getText().toString();
            }
        });

        signUpBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                code = verifyCodeEdt.getText().toString();
                if(!code.isEmpty()){
                    //Submit the verify code
                    SMSSDK.submitVerificationCode("1", phoneNumber, code);
                }else{
                    Toast.makeText(getApplicationContext(),"Please input the verify code",Toast.LENGTH_LONG).show();
                    return;
                }

                SignUpThread signUpThread = new SignUpThread();
                Thread thread = new Thread(signUpThread);
                thread.start();

                try {
                    thread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if(canSignUp){
                    Toast.makeText(SignupActivity.this,"SignUp successfully", Toast.LENGTH_SHORT).show();

                    addUser(phoneNumber,passwordEdt.getText().toString(),userNameEdt.getText().toString());

                    Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
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

    @Override
    protected void onDestroy(){
        super.onDestroy();
        SMSSDK.unregisterEventHandler(eh);
    }

    public void addUser(String phoneNumber,String password, String userName){
        DBThread dbThread = new DBThread(4,phoneNumber,password,userName);
        Thread thread1 = new Thread(dbThread);
        thread1.start();
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
                String sql = "SELECT * FROM user where phoneNumber = '"+phoneNumberEdt.getText().toString()+"'";
                ResultSet rs = stmt.executeQuery(sql);

                //need to use PhoneNumberUtils to judge whether valid number
                if (rs.next()) {
                    //signUp success
                    canSignUp = false;
                } else {
                    //signUp fails
                    canSignUp = true;
                }
                conn.close();
                stmt.close();


            }catch (Exception e){
                e.printStackTrace();
            }


        }
    }

}
