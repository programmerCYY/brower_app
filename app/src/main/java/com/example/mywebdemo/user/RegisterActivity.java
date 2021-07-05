package com.example.mywebdemo.user;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.example.mywebdemo.R;
import com.example.mywebdemo.constance.fragConst;
import com.example.mywebdemo.httputils.HttpUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
//    private EditText mEtUserPic;
    private EditText mEtPassword;
    private EditText mEtRePassword;
    private Button mBtnRegister;
    private ImageView mEtUserImg;
    private String s="";//图片的本地路径

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

        mEtUserImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImagePickDialog();
            }
        });

        mBtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String userphone = mEtUserphone.getText().toString();
                String username = mEtUsername.getText().toString();
                String useremail = mEtUserEmail.getText().toString();
//                String userpic = mEtUserPic.getText().toString();
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

                    //上传图片到服务器
                    //上传图片拿到url
                    File f=httpUtils.bitmapChangeFile(bitmap1);
                    try {
                        httpUtils.UploadPic(f);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    //注册图标
                    httpUtils.Register(userphone, password, useremail, username, httpUtils.appendUrl(fragConst.icon_temp_string));
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

        mEtPassword = (EditText) findViewById(R.id.id_re_password);
        mEtRePassword = (EditText) findViewById(R.id.id_re_repassword);
        mBtnRegister = (Button) findViewById(R.id.id_btn_register);
        mEtUserImg =(ImageView)findViewById(R.id.user_pic_big);

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

    private Uri photoUri1;
    private Bitmap bitmap1;
    private static final int PHOTO_REQUEST_CAMERA = 1;// 拍照
    private static final int PHOTO_REQUEST_GALLERY = 2;// 从相册中选择



    public void showImagePickDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(
                RegisterActivity.this);
        builder.setTitle("选择方式");
        builder.setPositiveButton("拍照", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                // 判断存储卡是否可以用，可用进行存储
                if (hasSDCard()) {

                    SimpleDateFormat timeStampFormat = new SimpleDateFormat(
                            "yyyy_MM_dd_HH_mm_ss");
                    String filename = timeStampFormat.format(new Date());
                    ContentValues values = new ContentValues();
                    values.put(MediaStore.Audio.Media.TITLE, filename);
                    photoUri1 = getContentResolver().insert(
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                            values);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri1);

                }
                startActivityForResult(intent, PHOTO_REQUEST_CAMERA);
            }
        });
        builder.setNeutralButton("相册", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, PHOTO_REQUEST_GALLERY);
            }
        });
        builder.create().show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PHOTO_REQUEST_GALLERY) {
            if (data != null) {
// 得到图片的全路径
                photoUri1 = data.getData();
                Log.d("photoUri1",""+photoUri1);
// bitmap1 = decodeUriAsBitmap(photoUri1);
// img1.setImageBitmap(bitmap1);

// imageLoader.getInstance().displayImage(
// photoUri1.toString(), img1, animateFirstListener);

                //获取图片本地路径
                s=getRealPathFromUri(RegisterActivity.this,photoUri1);
//                bitmap1 = small(ImageLoader.getInstance().loadImageSync(
//                        photoUri1.toString()));


                bitmap1= BitmapFactory.decodeFile(s);
                mEtUserImg.setImageBitmap(bitmap1);
            }

        } else if (requestCode == PHOTO_REQUEST_CAMERA) {
            if (hasSDCard()) {
                if (resultCode == -1) {
// bitmap1 = decodeUriAsBitmap(photoUri1);
// img1.setImageBitmap(bitmap1);

// imageLoader.getInstance().displayImage(
// photoUri1.toString(), img1,
// animateFirstListener);
// bitmap1 = img1.getDrawingCache();

                    String s=getRealPathFromUri(RegisterActivity.this,photoUri1);
                    bitmap1= BitmapFactory.decodeFile(s);

//                    bitmap1 = small(ImageLoader.getInstance()
//                            .loadImageSync(photoUri1.toString()));
                    mEtUserImg.setImageBitmap(bitmap1);
                }
            } else {
                Toast.makeText(RegisterActivity.this, "未找到存储卡，无法存储照片！", Toast.LENGTH_SHORT)
                        .show();
            }

        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    public static String getRealPathFromUri(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = { MediaStore.Images.Media.DATA };
            cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }
    public static boolean hasSDCard() {
        return Environment.MEDIA_MOUNTED.equals(Environment
                .getExternalStorageState());
    }
}
