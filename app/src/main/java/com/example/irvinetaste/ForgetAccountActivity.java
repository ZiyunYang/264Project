package com.example.irvinetaste;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.irvinetaste.utils.DBThread;
import com.example.irvinetaste.utils.DataSet;
import com.example.irvinetaste.utils.PhoneNumberUtils;
import com.mongodb.DB;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

//TODO
public class ForgetAccountActivity extends AppCompatActivity {

    private Button verifyCodeBtn, changePwdBtn;
    private EditText verifyCodeEdt, passwordEdt,phoneNumberEdt;
    private String phoneNumber, code;

    private boolean canChangePwd = false;
    protected static String changePwd_state = "200";

    private EventHandler eh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_account);

        verifyCodeBtn = findViewById(R.id.forgotVerifyCode);
        changePwdBtn = findViewById(R.id.forgotChangePwd);
        verifyCodeEdt = findViewById(R.id.forgotVerifyCodeText);
        passwordEdt = findViewById(R.id.forgotNewPwd);
        phoneNumberEdt = findViewById(R.id.forgotPhoneNumber);

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
                                Toast.makeText(ForgetAccountActivity.this,"Verify Code correct",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }else if (event == SMSSDK.EVENT_GET_VOICE_VERIFICATION_CODE){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(ForgetAccountActivity.this,"voice verify sent",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE){
                        //获取验证码成功
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(ForgetAccountActivity.this,"Verify Code sent",Toast.LENGTH_SHORT).show();
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
                                    Toast.makeText(ForgetAccountActivity.this,des,Toast.LENGTH_SHORT).show();
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

                if(isNumberExist()){
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
                }else{
                    Toast.makeText(getApplicationContext(),"This phone number not SignUp",Toast.LENGTH_LONG).show();
                    return;
                }

            }
        });


        changePwdBtn.setOnClickListener(new View.OnClickListener() {
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

                if(canChangePwd){

                    Toast.makeText(ForgetAccountActivity.this,"ChangePassword successfully", Toast.LENGTH_SHORT).show();
                    changePassword(phoneNumberEdt.getText().toString(),passwordEdt.getText().toString());
                    Intent intent = new Intent(ForgetAccountActivity.this, LoginActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(ForgetAccountActivity.this,"cannot change the password", Toast.LENGTH_SHORT).show();
                }
            }
        });



    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        SMSSDK.unregisterEventHandler(eh);
    }

    public void changePassword(String phoneNumber,String password){
        DBThread dbThread = new DBThread(3,phoneNumber,password);
        Thread thread = new Thread(dbThread);
        thread.start();
    }

    public boolean isNumberExist(){
        if(phoneNumber == null || phoneNumber.length() == 0) return false;

        PhoneCheckThread phoneCheckThread = new PhoneCheckThread();
        Thread thread = new Thread(phoneCheckThread);
        thread.start();
        for (;changePwd_state.equals("200");){

        }
        return canChangePwd;
    }

    class PhoneCheckThread implements Runnable{

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
                String sql = "SELECT * FROM user where phoneNumber = '"+phoneNumber+"'";
                ResultSet rs = stmt.executeQuery(sql);

                //need to use PhoneNumberUtils to judge whether valid number
                if (rs.next()) {
                    //phoneNumber exist
                    canChangePwd = true;
                } else {
                    //no this phoneNumber
                    canChangePwd = false;
                }
                conn.close();
                stmt.close();


            }catch (Exception e){
                e.printStackTrace();
            }

            ForgetAccountActivity.changePwd_state = "1";
        }
    }

}