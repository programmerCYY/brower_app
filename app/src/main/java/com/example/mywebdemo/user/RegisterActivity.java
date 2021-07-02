package com.example.mywebdemo.user;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mywebdemo.R;
import com.example.mywebdemo.constance.fragConst;
import com.example.mywebdemo.httputils.HttpUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
//import com.imooc.res.bean.User;
//import com.imooc.res.biz.UserBiz;
//import com.imooc.res.net.CommonCallback;
//import com.imooc.res.utils.T;

public class RegisterActivity extends BaseActivity {

    private EditText mEtUserphone;
    private EditText mEtUsername;
    private EditText mEtUserEmail;
    private EditText mEtUserPic;
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
                final String userphone = mEtUserphone.getText().toString();
                String username = mEtUsername.getText().toString();
                String useremail = mEtUserEmail.getText().toString();
                String userpic = mEtUserPic.getText().toString();
                String password = mEtPassword.getText().toString();
                String repassword = mEtRePassword.getText().toString();


                if(!isMobile(userphone)){
                    Toast.makeText(RegisterActivity.this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
                    return;
                }else if(!isEmail(useremail)){
                    Toast.makeText(RegisterActivity.this, "请输入正确的邮箱地址", Toast.LENGTH_SHORT).show();
                    return;
                }else if (TextUtils.isEmpty(userphone) || TextUtils.isEmpty(password) || TextUtils.isEmpty(username)) {
                    Toast.makeText(RegisterActivity.this, "账号、密码或者用户名不能为空", Toast.LENGTH_SHORT).show();
                    return;
                } else if (!password.equals(repassword)) {
                    Toast.makeText(RegisterActivity.this, "两次输入密码不一致", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    HttpUtils httpUtils = new HttpUtils();
                    httpUtils.Register(userphone, password, useremail, username, userpic);
                    startLoadingProgress();
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    stopLoadingProgress();
                    if (fragConst.http_msg != "succ") {
                        Toast.makeText(RegisterActivity.this, "注册失败，请重试", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(RegisterActivity.this, "注册成功，用户id为：" + fragConst.user_account, Toast.LENGTH_SHORT).show();
                        fragConst.http_msg = "";
                    }
                }


//                fragConst.user.add(new User(userid,username,password));


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
        mEtUserphone = (EditText) findViewById(R.id.id_re_useid);
        mEtUsername = (EditText) findViewById(R.id.id_re_username);
        mEtUserEmail = (EditText) findViewById(R.id.id_re_email);
        mEtUserPic = (EditText) findViewById(R.id.id_re_userpic);
        mEtPassword = (EditText) findViewById(R.id.id_re_password);
        mEtRePassword = (EditText) findViewById(R.id.id_re_repassword);
        mBtnRegister = (Button) findViewById(R.id.id_btn_register);

    }

//    @Override
//    public void onDetachedFromWindow() {
//        super.onDetachedFromWindow();
//        mUserBiz.onDestory();
//    }

    /**
     * 验证邮箱输入是否合法
     *
     * @param strEmail
     * @return
     */
    public static boolean isEmail(String strEmail) {
// String strPattern =
// "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        String strPattern = "^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$";


        Pattern p = Pattern.compile(strPattern);
        Matcher m = p.matcher(strEmail);
        return m.matches();
    }

    /**
     * 验证是否是手机号码
     *
     * @param str
     * @return
     */
    public static boolean isMobile(String str) {
        Pattern pattern = Pattern.compile("1[0-9]{10}");
        Matcher matcher = pattern.matcher(str);
        if (matcher.matches()) {
            return true;
        } else {
            return false;
        }
    }
}
