package com.example.mywebdemo.user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mywebdemo.FragActivity;
import com.example.mywebdemo.R;
import com.example.mywebdemo.history.historyActivity;
import com.example.mywebdemo.constance.fragConst;
import com.example.mywebdemo.httputils.HttpUtils;

public class MeActivity extends AppCompatActivity {

    private Button btn;
    private LinearLayout lEdit;
    private LinearLayout lExit;

    private TextView tPhone;
    private TextView tNickName;
    private TextView tUsername;
    private TextView tEmail;

    private EditText ePhone;
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
        Log.d("user",""+fragConst.user.getEmail());


        lEdit=findViewById(R.id.me_edit);
        lExit=findViewById(R.id.me_exit_user);

        tPhone=findViewById(R.id.me_phone);
        tNickName=findViewById(R.id.me_nickname);
        tUsername=findViewById(R.id.me_username);
        tEmail=findViewById(R.id.me_email);

        tPhone.setText(fragConst.user.getPhone());
        tNickName.setText(fragConst.user.getNickname());
        tUsername.setText(fragConst.user.getUsername());
        tEmail.setText(fragConst.user.getEmail());

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
}
