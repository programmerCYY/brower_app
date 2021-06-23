package com.example.mywebdemo.flag;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.mywebdemo.MainActivity;
import com.example.mywebdemo.R;

public class EditActivity extends AppCompatActivity {
    public String editurl;
    public String edittitle;
    public int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        init();
        EditText title = (EditText) findViewById(R.id.edit_title);
        EditText url = (EditText) findViewById(R.id.edit_url);
        Button commit=(Button)findViewById(R.id.edit_commit);
        Button cancel=(Button)findViewById(R.id.edit_cancel);
        title.setText(edittitle);
        url.setText(editurl);
        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flagActivity.setxTitle(position,title.getText().toString());
                flagActivity.setxurl(position,url.getText().toString());
                Intent intent = new Intent(EditActivity.this, flagActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
                finish();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditActivity.this, flagActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
                finish();
            }
        });
    }

    private void init() {
        Bundle bundle = this.getIntent().getExtras();
       edittitle=bundle.getString("edittitle");
        editurl=bundle.getString("editurl");
        position = bundle.getInt("position");
    }
}