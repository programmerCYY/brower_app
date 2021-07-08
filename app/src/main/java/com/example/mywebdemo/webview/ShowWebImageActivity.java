package com.example.mywebdemo.webview;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mywebdemo.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class ShowWebImageActivity extends Activity {
    private static final String SAVE_REAL_PATH = Environment.getExternalStorageDirectory()+"/Download";//保存的确切位置
    private ArrayList<String> list_imgs=null;
    private String imagePath = null;
    private static int times=0;
    private ImageView imageView;
    private static int i=0;
    // 縮放控制
    private Matrix matrix = new Matrix();
    private Matrix savedMatrix = new Matrix();

    // 不同状态的表示：
    private static final int NONE = 0;
    private static final int DRAG = 1;
    private static final int ZOOM = 2;
    private int mode = NONE;

    // 定义第一个按下的点，两只接触点的重点，以及出事的两指按下的距离：
    private PointF startPoint = new PointF();
    private PointF midPoint = new PointF();
    private float oriDis = 1f;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_web_image);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        Bundle bundle = this.getIntent().getExtras();
        list_imgs=bundle.getStringArrayList("img");
        String currentimg=bundle.getString("currentimg");
        imagePath = currentimg;
        i=list_imgs.indexOf(currentimg);
         imageView  = (ImageView) findViewById(R.id.show_webimage_imageview);

         Bitmap bitmap=returnBitMap(imagePath);
        int width=bitmap.getWidth();
        int height=bitmap.getHeight();
        imageView.setImageBitmap(returnBitMap(imagePath));


        matrix.postTranslate(450-width/2,400-height/2);
        imageView.setImageMatrix(matrix);

//        拿到imageView的尺寸
//        imageView.post(new Runnable() {
//            @Override
//            public void run() {
//                int w=imageView.getWidth();
//                int h=imageView.getHeight();
//                Log.d("wlength",h+"");
//                //matrix.postTranslate(w/2,h/2);
//            }
//        });



        Button btn_rotate=(Button) findViewById(R.id.btn_rotate);
        btn_rotate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                times=times+1;
//                imageView.setPivotX(imageView.getWidth()/2);
//                imageView.setPivotY(imageView.getHeight()/2);//支点在图片中心
                float value=(90*times)%360;
                imageView.setRotation(value);
            }
        });


        imageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ImageView view = (ImageView) v;
                switch (event.getAction() & MotionEvent.ACTION_MASK) {
                    // 单指
                    case MotionEvent.ACTION_DOWN:
                        matrix.set(view.getImageMatrix());
                        savedMatrix.set(matrix);
                        startPoint.set(event.getX(), event.getY());
                        mode = DRAG;
                        break;
                    // 双指
                    case MotionEvent.ACTION_POINTER_DOWN:
                        oriDis = distance(event);
                        if (oriDis > 10f) {
                            savedMatrix.set(matrix);
                            midPoint = middle(event);
                            mode = ZOOM;
                        }
                        break;
                    // 手指放开
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_POINTER_UP:
                        mode = NONE;
                        break;
                    // 单指滑动事件
                    case MotionEvent.ACTION_MOVE:
                        if (mode == DRAG) {
                            // 是一个手指拖动
                            matrix.set(savedMatrix);
                            matrix.postTranslate(event.getX() - startPoint.x, event.getY() - startPoint.y);
                        } else if (mode == ZOOM) {
                            // 两个手指滑动
                            float newDist = distance(event);
                            if (newDist > 10f) {
                                matrix.set(savedMatrix);
                                float scale = newDist / oriDis;
                                matrix.postScale(scale, scale, midPoint.x, midPoint.y);
                            }
                        }
                        break;
                }
                // 设置ImageView的Matrix
                view.setImageMatrix(matrix);
                return true;
            }
        });

        Button btn_save=(Button) findViewById(R.id.btn_save);
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("string", String.valueOf(Environment.getExternalStorageDirectory()));
                saveBitmap(returnBitMap(imagePath));
            }
        });

        TextView current_pic=(TextView)findViewById(R.id.current_pic);
        TextView all_pic=(TextView)findViewById(R.id.all_pic);
        current_pic.setText((i+1)+"");



        Button btn_next=(Button) findViewById(R.id.btn_next);
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(i<list_imgs.size()-1) {
                    i = i + 1;
                    imagePath = list_imgs.get(i);
                    toCenter(imagePath);
                }else {
                    i=0;
                    imagePath = list_imgs.get(i);
                    toCenter(imagePath);
                }
                current_pic.setText((i+1)+"");
            }
        });

        Button btn_back=(Button) findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(i>0){
                    i=i-1;
                    imagePath = list_imgs.get(i);
                    toCenter(imagePath);

                }
                else {
                   i=list_imgs.size()-1;
                    imagePath = list_imgs.get(i);
                    toCenter(imagePath);
                    //imageView.setImageBitmap(returnBitMap(imagePath));
                }
                current_pic.setText((i+1)+"");

            }
        });

        all_pic.setText(list_imgs.size()+"");



    }

    private void saveBitmap(Bitmap bitmap) {
        try {
            File dirFile = new File(SAVE_REAL_PATH);
            if (!dirFile.exists()) {              //如果不存在，那就建立这个文件夹
                dirFile.mkdirs();
            }
            File file = new File(SAVE_REAL_PATH, imagePath.substring(imagePath.length()-10) + ".jpg");
            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

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

    private float distance(MotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return Float.valueOf(String.valueOf(Math.sqrt(x * x + y * y))) ;
    }

    // 计算两个触摸点的中点
    private PointF middle(MotionEvent event) {
        float x = event.getX(0) + event.getX(1);
        float y = event.getY(0) + event.getY(1);
        return new PointF(x / 2, y / 2);
    }

    //居中
    private void toCenter(String str){

        Bitmap bitmap=returnBitMap(str);
        int width=bitmap.getWidth();
        int height=bitmap.getHeight();
        imageView.setImageBitmap(bitmap);
        matrix = new Matrix();
        matrix.postTranslate(450-width/2,400-height/2);
        imageView.setImageMatrix(matrix);
    }

}
