package com.example.mywebdemo.flag;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mywebdemo.R;
import com.example.mywebdemo.constance.fragConst;

public class EditActivity extends AppCompatActivity {
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
        title.setText(fragConst.flag_name.get(position));
        url.setText(fragConst.flag_url.get(position));
        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragConst.flag_name.set(position,title.getText().toString());
                fragConst.flag_url.set(position,url.getText().toString());
                flagActivity.getFlagAdapter().notifyItemRangeChanged(0,flagActivity.getFlagAdapter().getItemCount());
                Intent intent = new Intent(EditActivity.this, flagActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
                finish();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditActivity.this, flagEditActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
                finish();
            }
        });
    }

    private void init() {
        Bundle bundle = this.getIntent().getExtras();
        position = bundle.getInt("editPosition");
    }
}
