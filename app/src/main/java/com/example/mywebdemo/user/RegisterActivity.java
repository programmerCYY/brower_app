package com.example.mywebdemo.user;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mywebdemo.R;
import com.example.mywebdemo.constance.fragConst;
//import com.imooc.res.bean.User;
//import com.imooc.res.biz.UserBiz;
//import com.imooc.res.net.CommonCallback;
//import com.imooc.res.utils.T;

public class RegisterActivity extends BaseActivity {

    private EditText mEtUserid;
    private EditText mEtUsername;
    private EditText mEtPassword;
    private EditText mEtRePassword;
    private Button mBtnRegister;

//    private UserBiz mUserBiz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setUpToolbar();
        setTitle("手机注册");

        initView();

        initEvent();

//        mUserBiz = new UserBiz();

    }

    private void initEvent() {

        mBtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String userid = mEtUserid.getText().toString();
                String username = mEtUsername.getText().toString();
                String password = mEtPassword.getText().toString();
                String repassword = mEtRePassword.getText().toString();


                if (TextUtils.isEmpty(userid) || TextUtils.isEmpty(password)) {
                    Toast.makeText(RegisterActivity.this,"账号或者密码不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!password.equals(repassword)) {
                    Toast.makeText(RegisterActivity.this,"两次输入密码不一致",Toast.LENGTH_SHORT).show();
                    return;
                }

                startLoadingProgress();

                fragConst.user.add(new User(userid,username,password));
                stopLoadingProgress();
                Toast.makeText(RegisterActivity.this,"注册成功，用户名为："+fragConst.user.get(0).getName(),Toast.LENGTH_SHORT).show();



//                mUserBiz.register(username, password, new CommonCallback<User>() {
//                    @Override
//                    public void onError(Exception e) {
//                        T.showToast(e.getMessage());
//                        Log.d("ee", e.getMessage());
//                        stopLoadingProgress();
//                    }
//
//                    @Override
//                    public void onSuccess(User user) {
//                        stopLoadingProgress();
//                        T.showToast("注册成功，用户名为：" + user.getUsername());
//                        LoginActivity.launch(RegisterActivity.this,
//                                user.getUsername(), user.getPassword());
//                        finish();
//                    }
//                });
            }


        });

    }


    private void initView() {
        mEtUserid = (EditText) findViewById(R.id.id_re_useid);
        mEtUsername=(EditText) findViewById(R.id.id_re_username);
        mEtPassword = (EditText) findViewById(R.id.id_et_password);
        mEtRePassword = (EditText) findViewById(R.id.id_re_repassword);
        mBtnRegister = (Button) findViewById(R.id.id_btn_register);

    }

//    @Override
//    public void onDetachedFromWindow() {
//        super.onDetachedFromWindow();
//        mUserBiz.onDestory();
//    }
}
