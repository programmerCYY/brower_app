package com.example.backend.util;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

public class http{
    public static String sendGet(String url, String param) {
        String result = "";
        BufferedReader bufferedReader = null;
        try {
            // 设置通用的请求属性
            URLConnection connection = initConnection(url, param, "get");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            InputStream inputStream = connection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            bufferedReader = new BufferedReader(inputStreamReader);
            String line;
            while (null != (line = bufferedReader.readLine())) {
                result += line;
            }
        } catch(Exception e) {
            String message = "发送GET请求出现异常！请求地址：" + url;
        } finally {
            try {
                if (null != bufferedReader) {
                    bufferedReader.close();
                }
            } catch (Exception e2) {
                String message = "http get请求关闭输入流异常！请求地址：" + url;
                System.out.println(message);
            }
        }
        return result;
    }

    public String sendPost(String url, String param) {

        PrintWriter out = null;
        BufferedReader bufferedReader = null;
        String result = "";
        try {
            URLConnection conn = initConnection(url, param, "post");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            InputStream inputStream = conn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            bufferedReader = new BufferedReader(inputStreamReader);
            String line;
            while (null != (line = bufferedReader.readLine())) {
                result += line;
            }

        } catch (Exception e) {
            String message = "发送 POST 请求出现异常！请求地址：" + url;
            System.out.println(message);
            e.printStackTrace();
            System.out.println(e.getMessage());
        } finally {
            try {
                if (null != out) {
                    out.close();
                }
                if (null != bufferedReader) {
                    bufferedReader.close();
                }
            } catch (IOException ex) {
                String message = "http post请求关闭输入流异常！请求地址：" + url;
                ex.printStackTrace();
                System.out.println(message);
            }
        }
        return result;
    }

    private static URLConnection initConnection(String url, String param, String method) throws IOException {
        String urlNameString = url;
        if(method.equals("get")){
            if (null != param) {
                urlNameString = url + "?" + param;
            }
        } else {
            urlNameString = url;
        }
        URL realUrl = new URL(urlNameString);
        // 打开和URL之间的连接
        URLConnection connection = realUrl.openConnection();
        connection.setRequestProperty("accept", "*/*");
        connection.setRequestProperty("connection", "Keep-Alive");
        connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
        connection.setRequestProperty("Accept-Charset", "utf-8");
        connection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
        connection.setRequestProperty("partnertoken", "ARS57BOZX0LC05GHYN");
        return connection;
    }

}
