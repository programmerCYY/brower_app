package com.example.mywebdemo.user;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mywebdemo.FragActivity;
import com.example.mywebdemo.R;
import com.example.mywebdemo.history.historyActivity;
import com.example.mywebdemo.constance.fragConst;
import com.example.mywebdemo.httputils.HttpUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MeActivity extends AppCompatActivity {

    private Button btn;
    private LinearLayout lEdit;
    private LinearLayout lExit;
    private LinearLayout lAboveBar;
    private LinearLayout lHead;

    private ImageView tImg;
    private TextView tPhone;
    private TextView tNickName;
    private TextView tUsername;
    private TextView tEmail;


    private EditText ePhone;

    private String s;//图片上传路径
    private ImageView edit_pic;//用户图片编辑
//    private TextView t5;
//    private TextView t1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_me);

        HttpUtils httpUtils=new HttpUtils();
        httpUtils.GetUser();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        Log.d("user",""+fragConst.user.getEmail());




        lEdit=findViewById(R.id.me_edit);
        lExit=findViewById(R.id.me_exit_user);
        lAboveBar=findViewById(R.id.me_above_bar);
        lHead=findViewById(R.id.layout_me_header1);

        tPhone=findViewById(R.id.me_phone);
        tNickName=findViewById(R.id.me_nickname);
        tUsername=findViewById(R.id.me_username);
        tEmail=findViewById(R.id.me_email);
        tImg=findViewById(R.id.me_pic);

        tPhone.setText(fragConst.user.getPhone());
        tNickName.setText(fragConst.user.getNickname());
        tUsername.setText(fragConst.user.getUsername());
        tEmail.setText(fragConst.user.getEmail());
        tImg.setImageBitmap(returnBitMap(fragConst.user.getAvatar()));

        ePhone=findViewById(R.id.me_phone_edit);

        //返回按钮
        btn=(Button) findViewById(R.id.me_exit);

        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(MeActivity.this, FragActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
                finish();
            }
        });

        //编辑信息按钮
        lEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupWindow(v);
            }
        });

        //退出登录按钮
        lExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragConst.user_account="";
                Intent intent = new Intent(MeActivity.this, FragActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
                finish();
            }
        });

//        lEdit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ePhone.setVisibility(View.VISIBLE);
//                ePhone.setText(fragConst.user.getPhone());
//            }
//        });

    }
    public Bitmap returnBitMap(String url) {
        URL myFileUrl = null;
        Bitmap bitmap = null;
        try {
            myFileUrl = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            HttpURLConnection conn = (HttpURLConnection) myFileUrl.openConnection();
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }


    private void showPopupWindow(View view){

        View v ;
        v = LayoutInflater.from(this).inflate(R.layout.me_edit, null);


        //获取弹窗尺寸
        v.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        int popupHeight=v.getMeasuredHeight();

        lHead.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        int headHeight=lHead.getMeasuredHeight();

        lAboveBar.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        int barHeight=lAboveBar.getMeasuredHeight();


        final PopupWindow window=new PopupWindow(v,getWindowManager().getDefaultDisplay().getWidth(),popupHeight+50,true);
        //设置背景
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //设置能响应外部的点击事件（返回按钮后会消失）
        window.setOutsideTouchable(true);
        //设置能响应点击事件（弹窗能响应事件）
        window.setTouchable(true);
        //①创建动画资源   ②创建一个style应用动画资源（新建anim文件夹下的animation文件）    ③对当前弹窗的动画风格设置为第二部的资源索引（在style中设置）
        window.setAnimationStyle(R.style.translate_anim);
        //参数1(anchor)：锚
        //参数2、3：相对于锚在x、y方向上的偏移量
        //window.showAtLocation(view,1,0,0);

        window.showAsDropDown(view,-getWindowManager().getDefaultDisplay().getWidth(),-headHeight-barHeight-70);


        edit_pic=v.findViewById(R.id.user_pic_big_edit);
        EditText edit_phone=v.findViewById(R.id.id_re_useid_edit);
        EditText edit_username=v.findViewById(R.id.id_re_username_edit);
        EditText edit_email=v.findViewById(R.id.id_re_email_edit);
        EditText edit_password=v.findViewById(R.id.id_re_password_edit);
        EditText edit_repassword=v.findViewById(R.id.id_re_repassword_edit);
        Button submit=v.findViewById(R.id.id_btn_register_edit);

        edit_pic.setImageBitmap(returnBitMap(fragConst.user.getAvatar()));
        edit_phone.setText(fragConst.user.getPhone());
        edit_username.setText(fragConst.user.getUsername());
        edit_email.setText(fragConst.user.getEmail());



        //返回按钮
        v.findViewById(R.id.me_exit_edit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                window.dismiss();
            }
        });


        edit_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImagePickDialog();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String userphone = edit_phone.getText().toString();
                String username = edit_username.getText().toString();
                String useremail = edit_email.getText().toString();

                String password = edit_password.getText().toString();
                String repassword = edit_repassword.getText().toString();


                if(!isMobile(userphone)){
                    Toast.makeText(MeActivity.this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
                    return;
                }else if(!isEmail(useremail)){
                    Toast.makeText(MeActivity.this, "请输入正确的邮箱地址", Toast.LENGTH_SHORT).show();
                    return;
                }else if (TextUtils.isEmpty(userphone) || TextUtils.isEmpty(password) || TextUtils.isEmpty(username)) {
                    Toast.makeText(MeActivity.this, "账号、密码或者用户名不能为空", Toast.LENGTH_SHORT).show();
                    return;
                } else if (!password.equals(repassword)) {
                    Toast.makeText(MeActivity.this, "两次输入密码不一致", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    HttpUtils httpUtils = new HttpUtils();

                    //上传图片到服务器
                    //上传图片拿到url
                    File f=httpUtils.bitmapChangeFile(bitmap1);
//                    Log.d("bit",bitmap1+"");
                    if(bitmap1!=null) {
                        try {
                            httpUtils.UploadPic(f);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        httpUtils.ModifyUser(username, useremail, httpUtils.appendUrl(fragConst.icon_temp_string), password);

                    }else {
//                        Log.d("bit2","success");
                        if(fragConst.user.getAvatar()!=null){
                            httpUtils.ModifyUser(username, useremail, fragConst.user.getAvatar(),password);
                        }else {
                            httpUtils.ModifyUser(username, useremail, "http://39.108.210.48:4869/c3ce5639c862d4a2eecb12f830d75029",password);

                        }
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }


                    if (fragConst.http_msg != "succ") {
                        Toast.makeText(MeActivity.this, "修改失败，请重试", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(MeActivity.this, "修改成功，用户id为：" + fragConst.user_account, Toast.LENGTH_SHORT).show();
                        fragConst.http_msg = "";
                    }
                }
                window.dismiss();
                MeActivity.this.refresh();

            }


        });
    }

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
                MeActivity.this);
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
//                Log.d("photoUri1",""+photoUri1);

// bitmap1 = decodeUriAsBitmap(photoUri1);
// img1.setImageBitmap(bitmap1);

// imageLoader.getInstance().displayImage(
// photoUri1.toString(), img1, animateFirstListener);

                //获取图片本地路径
                s=getRealPathFromUri(MeActivity.this,photoUri1);
//                bitmap1 = small(ImageLoader.getInstance().loadImageSync(
//                        photoUri1.toString()));


                bitmap1= BitmapFactory.decodeFile(s);
                edit_pic.setImageBitmap(bitmap1);
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

                    String s=getRealPathFromUri(MeActivity.this,photoUri1);
                    bitmap1= BitmapFactory.decodeFile(s);

//                    bitmap1 = small(ImageLoader.getInstance()
//                            .loadImageSync(photoUri1.toString()));
                    edit_pic.setImageBitmap(bitmap1);
                }
            } else {
                Toast.makeText(MeActivity.this, "未找到存储卡，无法存储照片！", Toast.LENGTH_SHORT)
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

    public void refresh(){
        HttpUtils httpUtils=new HttpUtils();
        httpUtils.GetUser();

        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        tPhone.setText(fragConst.user.getPhone());
        tNickName.setText(fragConst.user.getNickname());
        tUsername.setText(fragConst.user.getUsername());
        tEmail.setText(fragConst.user.getEmail());
        tImg.setImageBitmap(returnBitMap(fragConst.user.getAvatar()));

    }


}
