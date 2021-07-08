package com.example.mywebdemo.news;

public class NewsItem {
    public String title;
    public String url;
    public String imgUrl;
    public String date;
    public String source;


    @Override
    public String toString() {
        return "NewsItem{" +
                "title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                ", date='" + date + '\'' +
                ", source='" + source + '\'' +
                '}';
    }
}
