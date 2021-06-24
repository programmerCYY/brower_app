package com.example.mywebdemo.httputils;

import android.util.Log;

import com.example.mywebdemo.constance.fragConst;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpUtils {
    private static final String sUrl = "http://47.111.12.192:11100";
    private static final String sLogin = "/user/login";
    private static final String sRegister = "/user/register";

    private static final String sHistoryAdd = "/history/add";
    private static final String sHistoryDelete = "/history/delete";
    private static final String sHistoryGet = "/history/get";

    private static final String sFlagAdd = "/flag/add";
    private static final String sFlagDelete = "/flag/delete";
    private static final String sFlagGet = "/flag/get";
    private static final String sFlagUpdate = "/flag/update";


    Gson gson = new Gson();
    OkHttpClient okHttpClient = new OkHttpClient();
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    /*---------------------User----------------------*/
    public void Login(String sAccount, String sPassword)
    {
        Map map = new HashMap<>();
        map.put("phone",sAccount);
        map.put("password",sPassword);
        String param = gson.toJson(map);

        RequestBody requestBody = RequestBody.create(JSON, param);
        Request request = new Request.Builder()
                .post(requestBody)
                .url(sUrl + sLogin)
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
                fragConst.user_account = sAccount;
                fragConst.http_msg = "succ";
            }
        });
    }

    public void Register(String sAccount, String sPassword)
    {
        Map map = new HashMap<>();
        map.put("phone",sAccount);
        map.put("password",sPassword);
        String param = gson.toJson(map);

        RequestBody requestBody = RequestBody.create(JSON, param);
        Request request = new Request.Builder()
                .post(requestBody)
                .url(sUrl + sRegister)
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
                fragConst.user_account = sAccount;
                fragConst.http_msg = "succ";
            }
        });
    }


    /*-------------------------History--------------------------------*/
    public void AddHistory(String sUrl, String sTitle)
    {
        Map map = new HashMap<>();
        map.put("historyUser",fragConst.user_account);
        map.put("historyUrl",sUrl);
        map.put("historyName",sTitle);
        String param = gson.toJson(map);

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

    public void DeleteHistory(String sUrl)
    {
        Map map = new HashMap<>();
        map.put("historyUser",fragConst.user_account);
        map.put("historyUrl",sUrl);
        String param = gson.toJson(map);

        RequestBody requestBody = RequestBody.create(JSON, param);
        Request request = new Request.Builder()
                .post(requestBody)
                .url(sUrl + sHistoryDelete)
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

    public void GetHistory()
    {
        Map map = new HashMap<>();
        map.put("historyUser",fragConst.user_account);
        String param = gson.toJson(map);

        RequestBody requestBody = RequestBody.create(JSON, param);
        Request request = new Request.Builder()
                .post(requestBody)
                .url(sUrl + sHistoryGet)
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
                for(int i = 0;i<Jarray.size();++i)
                {
                    JsonObject oJson = (JsonObject) Jarray.get(i);
                    fragConst.history_url.add( String.valueOf(oJson.get("historyUrl")) );
                    fragConst.history_name.add( String.valueOf(oJson.get("historyName")) );
                }
            }
        });
    }

    /*--------------------Flag-------------------------*/
    public void AddFlag(String sUrl, String sTitle)
    {
        Map map = new HashMap<>();
        map.put("flagUser",fragConst.user_account);
        map.put("flagUrl",sUrl);
        map.put("flagName",sTitle);
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
                Log.d("HttpUtils", "succ");
                fragConst.http_msg = "succ";
            }
        });
    }

    public void DeleteFlag(String sUrl)
    {
        Map map = new HashMap<>();
        map.put("flagUser",fragConst.user_account);
        map.put("flagUrl",sUrl);
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
        Map map = new HashMap<>();
        map.put("flagUser",fragConst.user_account);
        String param = gson.toJson(map);

        RequestBody requestBody = RequestBody.create(JSON, param);
        Request request = new Request.Builder()
                .post(requestBody)
                .url(sUrl + sFlagGet)
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
                for(int i = 0;i<Jarray.size();++i)
                {
                    JsonObject oJson = (JsonObject) Jarray.get(i);
                    fragConst.history_url.add( String.valueOf(oJson.get("flagUrl")) );
                    fragConst.history_name.add( String.valueOf(oJson.get("flagName")) );
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
}
