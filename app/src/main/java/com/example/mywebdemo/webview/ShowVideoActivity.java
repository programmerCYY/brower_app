package com.example.mywebdemo.webview;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.annotation.Nullable;

import com.example.mywebdemo.R;

import java.util.ArrayList;

public class ShowVideoActivity extends Activity {
    private String uri=null;
    private VideoView videoView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("openvideo", "onCreate: ");

        setContentView(R.layout.activity_web_video);

        this.uri=getIntent().getStringExtra("video");
        //url: https://cn-gdgz-fx-bcache-06.bilivideo.com/upgcxcode/40/67/360536740/360536740_nb2-1-16.mp4?e=ig8euxZM2rNcNbdlhoNvNC8BqJIzNbfq9rVEuxTEnE8L5F6VnEsSTx0vkX8fqJeYTj_lta53NCM=&uipk=5&nbs=1&deadline=1625221223&gen=playurlv2&os=bcache&oi=1849706652&trid=0000cb5363fbb0a34c97bbba562de949988bh&platform=html5&upsig=d1b8315fce3d5c73422d6cd7c90cf7cc&uparams=e,uipk,nbs,deadline,gen,os,oi,trid,platform&cdnid=3806&mid=0&logo=80000000
        Log.d("openvideo", "onCreate: "+uri);

        videoView = findViewById(R.id.video_view);

        videoView.setVideoPath(uri);

        MediaController mediaController =new MediaController(this);

        mediaController.setMediaPlayer(videoView);

        videoView.setMediaController(mediaController);

    }
}
