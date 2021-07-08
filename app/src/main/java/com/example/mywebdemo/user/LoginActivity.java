package com.example.mywebdemo.user;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mywebdemo.FragActivity;
import com.example.mywebdemo.R;
import com.example.mywebdemo.constance.fragConst;
import com.example.mywebdemo.httputils.HttpUtils;

//import com.imooc.res.UserInfoHolder;
//import com.imooc.res.bean.User;
//import com.imooc.res.biz.UserBiz;
//import com.imooc.res.net.CommonCallback;
//import com.imooc.res.utils.T;
//import com.zhy.http.okhttp.OkHttpUtils;
//import com.zhy.http.okhttp.cookie.CookieJarImpl;

public class LoginActivity extends BaseActivity {

    private EditText mEtUsername;
    private EditText mEtPassword;
    private Button mBtnLogin;
    private TextView mBtnRegister;


    private static final String KEY_USERNAME = "key_username";
    private static final String KEY_PASSWORD = "key_password";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setUpToolbar();

        mEtUsername = (EditText) findViewById(R.id.id_et_username);
        mEtPassword = (EditText) findViewById(R.id.id_et_password);
        mBtnLogin = (Button) findViewById(R.id.id_btn_login);
        mBtnRegister = (TextView) findViewById(R.id.id_btn_register);

        initIntent();


        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String username = mEtUsername.getText().toString();
                String password = mEtPassword.getText().toString();

                if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
                    Toast.makeText(LoginActivity.this,"账号或者密码不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }else {
                startLoadingProgress();
                HttpUtils httpUtils=new HttpUtils();
                httpUtils.Login(username,password);

                try {
                    startLoadingProgress();
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                stopLoadingProgress();
                if(fragConst.http_msg!="succ"){
                    Toast.makeText(LoginActivity.this,"登录失败，请重试",Toast.LENGTH_SHORT).show();
                }else{

                    Toast.makeText(LoginActivity.this,"登录成功，用户id为："+fragConst.user_account,Toast.LENGTH_SHORT).show();
                    fragConst.http_msg="";
                    Intent intent = new Intent(LoginActivity.this, FragActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    startActivity(intent);

                }

            }

            }
        });

        mBtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toRegisterActivity();
            }
        });

    }

    private void initIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            String username = intent.getStringExtra(KEY_USERNAME);
            if (username != null) {
                mEtUsername.setText(username);
            }
            String password = intent.getStringExtra(KEY_PASSWORD);
            if (password != null) {
                mEtPassword.setText(password);
            }
        }
    }


    private void toRegisterActivity() {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);

    }


}
