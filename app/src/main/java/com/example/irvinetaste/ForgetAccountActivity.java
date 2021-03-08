package com.example.irvinetaste;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.irvinetaste.utils.PhoneNumberUtils;

import org.json.JSONException;
import org.json.JSONObject;

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
                //first check the phoneNumber is a existing number
                //TODO

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
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        SMSSDK.unregisterEventHandler(eh);
    }


}