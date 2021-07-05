package com.example.mywebdemo.httputils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import com.example.mywebdemo.constance.fragConst;
import com.example.mywebdemo.user.User;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONArray;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpUtils {
    private static final String SAVE_REAL_PATH = Environment.getExternalStorageDirectory()+"/Download";//保存的确切位置


    private static final String sUrl = "http://47.111.12.192:11100";
    private static final String sLogin = "/user/login";
    private static final String sRegister = "/user/register";
    private static final String sUserGet="/user/getuser";

    private static final String sHistoryAdd = "/history/add";
    private static final String sHistoryDelete = "/history/delete";
    private static final String sHistoryGet = "/history/get";

    private static final String sFlagAdd = "/flag/add";
    private static final String sFlagDelete = "/flag/delete";
    private static final String sFlagGet = "/flag/get";
    private static final String sFlagUpdate = "/flag/update";

    private static final String sUploadPic="/upload";
    private static final String sPicUrl = "http://39.108.210.48:4869";


    Gson gson = new Gson();
    OkHttpClient okHttpClient = new OkHttpClient();
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    public static final MediaType IPEG =MediaType.parse("text/jpeg");

    /*---------------------User----------------------*/
    public void Login(String sAccount, String sPassword)
    {
        Map map = new HashMap<>();
        map.put("phone",sAccount);
        map.put("password",sPassword);
        String param = gson.toJson(map);

        RequestBody requestBody = RequestBody.create(JSON, param);
        Request request = new Request.Builder()
                .url(sUrl + sLogin)
                .post(requestBody)
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("HttpUtils", "onFailure: 失败");
                fragConst.user_account = "";
                fragConst.http_msg = "failed:" + e.getMessage();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("HttpUtils", "succ");
                Log.d("data2",""+response);
                fragConst.user_account = sAccount;
                fragConst.http_msg = "succ";
            }
        });
    }

    public void Register(String sAccount, String sPassword,String sEmail,String sUsername,String sAvatar)
    {

        Map map = new HashMap<>();
        map.put("phone",sAccount);
        map.put("password",sPassword);
        map.put("email",sEmail);
        map.put("username",sUsername);
        map.put("avatar",sAvatar);
        map.put("nickname","111");


       String param = gson.toJson(map);
       Log.d("param",param);

        RequestBody requestBody = RequestBody.create(JSON, param);


        Request request = new Request.Builder()
                .url(sUrl + sRegister)
                .post(requestBody)
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("HttpUtils", "onFailure: 失败");
                fragConst.user_account = "";
                fragConst.http_msg = "failed:" + e.getMessage();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("HttpUtils_register", "succ");
                fragConst.user_account = sAccount;
                fragConst.http_msg = "succ";
            }
        });
    }

    public void GetUser(){
        String url = sUrl + sUserGet + "?phone=" + fragConst.user_account;
        //Log.d("HttpUtils",url);
        Request request = new Request.Builder()
                .url(url)
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("HttpUtils", "onFailure: 失败");
                fragConst.http_msg = "failed:" + e.getMessage();
            }


            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("HttpUtils", "succ");
                fragConst.http_msg = "succ";

                String result = response.body().string();
                JsonParser jparser = new JsonParser();
                JsonObject jobject = jparser.parse(result).getAsJsonObject();
                JsonObject oJson = (JsonObject) jobject.getAsJsonObject("data");
                String phone=String.valueOf(oJson.get("phone")).replace("\"","");
                String username=String.valueOf(oJson.get("username")).replace("\"","");
                String nickname=String.valueOf(oJson.get("nickname")).replace("\"","");
                String avatar=String.valueOf(oJson.get("avatar")).replace("\"","");
                String email=String.valueOf(oJson.get("email")).replace("\"","");
                String password=String.valueOf(oJson.get("password")).replace("\"","");

                fragConst.user=new User(phone,username,nickname,email,avatar,password);
            }
        });

    }



    /*-------------------------History--------------------------------*/
    public void AddHistory(String sUserUrl, String sTitle,String sIcon)
    {
        Map map = new HashMap<>();
        map.put("historyUser",fragConst.user_account);
        map.put("historyUrl",sUserUrl);
        map.put("historyName",sTitle);
        map.put("historyIcon",sIcon);
        String param = gson.toJson(map);

        Log.d("sUserUrl",""+sUserUrl);
        RequestBody requestBody = RequestBody.create(JSON, param);
        Request request = new Request.Builder()
                .post(requestBody)
                .url(sUrl + sHistoryAdd)
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("HttpUtils", "onFailure: 失败");
                fragConst.http_msg = "failed:" + e.getMessage();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("HttpUtils", "succ");
                fragConst.http_msg = "succ";
            }
        });
    }

    public void DeleteHistory(String historyUrl)
    {

        Map map = new HashMap<>();
        map.put("user",fragConst.user_account);
        map.put("url",historyUrl);

        String param = gson.toJson(map);


        RequestBody requestBody = RequestBody.create(JSON, param);
        Request request = new Request.Builder()
                .url(sUrl + sHistoryDelete)
                .post(requestBody)
                .build();


        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("HttpUtils", "onFailure: 失败");
                fragConst.http_msg = "failed:" + e.getMessage();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("HttpUtils2", "succ");
                fragConst.http_msg = "succ";
            }
        });
    }

    public void GetHistory()
    {

        String url = sUrl + sHistoryGet + "?phone=" + fragConst.user_account;
        Log.d("HttpUtils",url);
        Request request = new Request.Builder()
                .url(url)
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("HttpUtils", "onFailure: 失败");
                fragConst.http_msg = "failed:" + e.getMessage();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("HttpUtils", "succ");
                fragConst.http_msg = "succ";

                String result = response.body().string();
                Log.d("HttpUtils",result);
                JsonParser jparser = new JsonParser();
                JsonObject jobject = jparser.parse(result).getAsJsonObject();
                JsonArray Jarray = jparser.parse(String.valueOf(jobject.get("data"))).getAsJsonArray();
                fragConst.history_url=new ArrayList<>();
                fragConst.history_name=new ArrayList<>();
                fragConst.history_icon=new ArrayList<>();
                fragConst.history_icon_string=new ArrayList<>();
                for(int i = 0;i<Jarray.size();++i)
                {
                    JsonObject oJson = (JsonObject) Jarray.get(i);
                    //json字符串记得删掉“ ”
                    fragConst.history_url.add( String.valueOf(oJson.get("historyUrl")).replace("\"","") );
                    fragConst.history_name.add( String.valueOf(oJson.get("historyName")).replace("\"","") );
                    fragConst.history_icon_string.add( String.valueOf(oJson.get("historyIcon")).replace("\"",""));
                }
            }
        });
    }

    /*--------------------Flag-------------------------*/
    public void AddFlag(String sFlagUrl, String sTitle,String sFlagIcon)
    {
        Map map = new HashMap<>();
        map.put("flagUser",fragConst.user_account);
        map.put("flagUrl",sFlagUrl);
        map.put("flagName",sTitle);
        map.put("flagIcon",sFlagIcon);
        String param = gson.toJson(map);

        RequestBody requestBody = RequestBody.create(JSON, param);
        Request request = new Request.Builder()
                .post(requestBody)
                .url(sUrl + sFlagAdd)
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("HttpUtils", "onFailure: 失败");
                fragConst.http_msg = "failed:" + e.getMessage();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("HttpUtilsK", "succ");
                fragConst.http_msg = "succ";
            }
        });
    }

    public void DeleteFlag(String sFlagUrl)
    {
        Map map = new HashMap<>();
        map.put("user",fragConst.user_account);
        map.put("url",sFlagUrl);
        String param = gson.toJson(map);

        RequestBody requestBody = RequestBody.create(JSON, param);
        Request request = new Request.Builder()
                .post(requestBody)
                .url(sUrl + sFlagDelete)
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("HttpUtils", "onFailure: 失败");
                fragConst.http_msg = "failed:" + e.getMessage();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("HttpUtils", "succ");
                fragConst.http_msg = "succ";
            }
        });
    }

    public void GetFlag()
    {
        String url = sUrl + sFlagGet + "?phone=" + fragConst.user_account;
        Log.d("HttpUtils",url);
        Request request = new Request.Builder()
                .url(url)
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("HttpUtils", "onFailure: 失败");
                fragConst.http_msg = "failed:" + e.getMessage();
            }


            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("HttpUtils", "succ");
                fragConst.http_msg = "succ";

                String result = response.body().string();
                JsonParser jparser = new JsonParser();
                JsonObject jobject = jparser.parse(result).getAsJsonObject();
                JsonArray Jarray = jparser.parse(String.valueOf(jobject.get("data"))).getAsJsonArray();
                fragConst.flag_url=new ArrayList<>();
                fragConst.flag_name=new ArrayList<>();
                fragConst.flag_icon=new ArrayList<>();
                fragConst.flag_icon_string=new ArrayList<>();
                for(int i = 0;i<Jarray.size();++i)
                {
                    JsonObject oJson = (JsonObject) Jarray.get(i);
                    fragConst.flag_url.add( String.valueOf(oJson.get("flagUrl")).replace("\"","") );
                    fragConst.flag_name.add( String.valueOf(oJson.get("flagName")).replace("\"","") );
                    fragConst.flag_icon_string.add(String.valueOf(oJson.get("flagIcon")).replace("\"",""));
                }
            }
        });
    }

    public void UpdateFlag()
    {
        Map map = new HashMap<>();
        map.put("flagUser",fragConst.user_account);
        map.put("flagUrl",fragConst.user_account);
        map.put("flagName",fragConst.user_account);
        String param = gson.toJson(map);

        RequestBody requestBody = RequestBody.create(JSON, param);
        Request request = new Request.Builder()
                .post(requestBody)
                .url(sUrl + sFlagUpdate)
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("HttpUtils", "onFailure: 失败");
                fragConst.http_msg = "failed:" + e.getMessage();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("HttpUtils", "succ");
                fragConst.http_msg = "succ";
            }
        });
    }

    //    上传图片

    /**
     * 将bitmap转换为file存储起来
     * @param bitmap
     * @return
     */
    public static File bitmapChangeFile(Bitmap bitmap) {
        FileOutputStream fileOutStream = null;
        File file = null;
        try {
            //通过相关方法生成一个Bitmap类型的对象,生产文件选择用当前事件的long型作为文件路径
            file = new File(SAVE_REAL_PATH, System.currentTimeMillis()+".jpeg");
            fileOutStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutStream); // 把位图输出到指定的文件中
            fileOutStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                fileOutStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    public String appendUrl(String str){
        return sPicUrl+"/"+str;
    }



    public void UploadPic(File file1) throws FileNotFoundException {

//        //创建RequestBody封装参数
//        RequestBody fileBody = RequestBody.create(MediaType.parse("image/jpeg"), file);// MediaType.parse("image/jpeg")//application/octet-stream
//        //创建MultipartBody,给RequestBody进行设置
//        MultipartBody multipartBody = new MultipartBody.Builder()
//                .setType(MultipartBody.FORM)
//                .addFormDataPart("file",file.getName(), fileBody)
//                .build();
//
//        //创建Request
//        Request request = new Request.Builder()
//                .url(sPicUrl+sUploadPic)//"ip:1111/Api/App/ImgUpload?UId=7"
//                .post(multipartBody)
//                .build();

//        File file1=new File(SAVE_REAL_PATH+"/o&gp=0.jpg.jpg");





        //2.创建RequestBody
        RequestBody fileBody = RequestBody.create(MediaType.parse("image/jpeg"), file1);

        //3.构建MultipartBody
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", "testImage.png", fileBody)
                .build();

        Request request = new Request.Builder()
                .url(sPicUrl+sUploadPic)
                .post(requestBody)
                .addHeader("Content-Type","image/jpeg")
                .build();
//        Request request = new Request.Builder()
//                .url(sPicUrl+sUploadPic)
//                .post(fileBody)
//                .addHeader("Content-Type","jpeg")
//                .build();

//        Log.d("reuqest",""+requestBody);


        //上传完图片,得到服务器反馈数据
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("ff", "uploadMultiFile() e=" + e);
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                fragConst.icon_temp_string=getMd5(response.body().string());
//                Log.i("ff", "uploadMultiFile() response=" + response.body().string());
                Log.d("md5",fragConst.icon_temp_string);
            }
        });
    }

    public String getMd5(String s){
        StringBuilder res=new StringBuilder();
        int i = 0,j = 0,n = s.length();
        while(i<n&&j<n){
            if(s.charAt(i)=='M'){
                j = i+5;
            }

            if(s.substring(i,i+4).equals("</h1")){
                break;
            }
            i++;
        }
        return s.substring(j,i);
    }

    //接收图片
    public void GetPic(String picUrl){

        String url = sPicUrl + picUrl;
        //Log.d("HttpUtils",url);
        Request request = new Request.Builder()
                .url(url)
                .build();

        //上传完图片,得到服务器反馈数据
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("ff", "uploadMultiFile() e=" + e);
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream inputStream = response.body().byteStream();//得到图片的流
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
//                fragConst.bitmap_temp=bitmap;
                //Log.i("ff", "uploadMultiFile() response=" + response.body().string());
            }
        });
    }
}
