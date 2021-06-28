package com.example.backend.schedule.impl;


import com.example.backend.pojo.News;
import com.example.backend.schedule.NewsPuller;
import com.example.backend.service.NewsService;
import com.example.backend.util.NewsUtils;
import io.swagger.annotations.ApiOperation;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Date;
import java.util.HashSet;

/**
 * @author XXX
 * @since 2018-06-07
 */
@Component("sohuNewsPuller")
public class SohuNewsPuller implements NewsPuller {

    private static final Logger logger = LoggerFactory.getLogger(SohuNewsPuller.class);
    @Value("${news.sohu.Newsurl}")
    private String Newsurl;

    @Value("${news.sohu.Sporturl}")
    private String Sporturl;

    @Value("${news.sohu.Carurl}")
    private String Carurl;

    @Value("${news.sohu.Houseurl}")
    private String Houseurl;

    @Value("${news.sohu.Iturl}")
    private String Iturl;

    @Value("${news.sohu.Entertainurl}")
    private String Entertainurl;

    @Autowired
    private NewsService newsService;

    @Override
    public void pullNews() {
        logger.info("开始拉取搜狐新闻！");
        // 1.获取首页
        Document html = null;
        try {
            html = getHtmlFromUrl(Newsurl, false);
        } catch (Exception e) {
            logger.error("==============获取搜狐首页失败: {}=============", Newsurl);
            e.printStackTrace();
            return;
        }
        // 2.jsoup获取新闻<a>标签
        Elements newsATags = html
                .select("div.list16")
                .select("li")
                .select("a");


        // 3.从<a>标签中抽取基本信息，封装成news
        HashSet<News> newsSet = new HashSet<>();
        for (Element a : newsATags) {
            String url = a.attr("href");
            String title = a.attr("title");

            if (url.substring(0, 1).equals("/")) {
                url = "https:" + url;
            }

            News n = new News();
            n.setSource("搜狐");
            n.setUrl(url);
            n.setTitle(title);
            n.setCreateDate(new Date());
            n.setTag("1");
            newsSet.add(n);
        }
        // 4.根据新闻url访问新闻，获取新闻内容
        newsSet.forEach(news -> {
            logger.info("开始抽取搜狐新闻内容：{}", news.getUrl());
            Document newsHtml = null;
            try {
                newsHtml = getHtmlFromUrl(news.getUrl(), false);
                Element newsContent = newsHtml.select("div#article-container")
                        .select("div.main")
                        .select("div.text")
                        .first();
                String title = newsContent.select("div.text-title").select("h1").text();
                String content = newsContent.select("article.article").first().toString();
                String image = NewsUtils.getImageFromContent(content);

                news.setTitle(title);
                news.setContent(content);
                news.setImage(image);
                newsService.saveNews(news);
                logger.info("抽取搜狐新闻《{}》成功！", news.getTitle());
            } catch (Exception e) {
                logger.error("新闻抽取失败:{}", news.getUrl());
                e.printStackTrace();
            }
        });
    }

    @Override
    public void pullSports() {
        logger.info("开始拉取搜狐体育！");
        // 1.获取首页
        Document html = null;
        try {
            html = getHtmlFromUrl(Sporturl, false);
        } catch (Exception e) {
            logger.error("==============获取搜狐首页失败: {}=============", Sporturl);
            e.printStackTrace();
            return;
        }
        // 2.jsoup获取新闻<a>标签
        Elements newsATags = html
                .select("div").not("li.navigation-head-list-li").not("li.team-item").not("li.pic-rec-item")
                .select("li")
                .select("a");


        // 3.从<a>标签中抽取基本信息，封装成news
        HashSet<News> newsSet = new HashSet<>();
        for (Element a : newsATags) {
            String url = a.attr("href");
            String title = a.text();

            if (url.substring(0, 1).equals("/")) {
                url = "https://www.sohu.com" + url;
            }

            News n = new News();
            n.setSource("搜狐");
            n.setUrl(url);
            n.setTitle(title);
            n.setCreateDate(new Date());
            n.setTag("2");
            newsSet.add(n);
        }
        // 4.根据新闻url访问新闻，获取新闻内容
        newsSet.forEach(news -> {
            logger.info("开始抽取搜狐体育内容：{}", news.getUrl());
            Document newsHtml = null;
            try {
                newsHtml = getHtmlFromUrl(news.getUrl(), false);
                Element newsContent = newsHtml.select("div#article-container")
                        .select("div.main")
                        .select("div.text")
                        .first();
                String title = newsContent.select("div.text-title").select("h1").text();
                String content = newsContent.select("article.article").first().toString();
                String image = NewsUtils.getImageFromContent(content);

                news.setTitle(title);
                news.setContent(content);
                news.setImage(image);
                newsService.saveNews(news);
                logger.info("抽取搜狐体育《{}》成功！", news.getTitle());
            } catch (Exception e) {
                logger.error("体育抽取失败:{}", news.getUrl());
                e.printStackTrace();
            }
        });
    }


    @Override
    public void pullCars(){
        logger.info("开始拉取搜狐车！");
        // 1.获取首页
        Document html = null;
        try {
            html = getHtmlFromUrl(Carurl, false);
        } catch (Exception e) {
            logger.error("==============获取搜狐车失败: {}=============", Carurl);
            e.printStackTrace();
            return;
        }
        // 2.jsoup获取新闻<a>标签
        Elements newsATags = html
                .select("div.box")
                .select("li")
                .select("a");


        // 3.从<a>标签中抽取基本信息，封装成news
        HashSet<News> newsSet = new HashSet<>();
        for (Element a : newsATags) {
            String url = a.attr("href");
            String title = a.text();

            News n = new News();
            n.setSource("搜狐");
            n.setUrl(url);
            n.setTitle(title);
            n.setCreateDate(new Date());
            n.setTag("3");
            newsSet.add(n);
        }
        // 4.根据新闻url访问新闻，获取新闻内容
        newsSet.forEach(news -> {
            logger.info("开始抽取搜狐车内容：{}", news.getUrl());
            Document newsHtml = null;
            try {
                newsHtml = getHtmlFromUrl(news.getUrl(), false);
                Element newsContent = newsHtml.select("div.content")
                        .select("div.article-box")
                        .first();
                String title = newsContent.select("h3.article-title").text();
                System.out.println("999   "+title);
                String content = newsContent.select("article.article-text").first().toString();
                String image = NewsUtils.getImageFromContent(content);

                news.setTitle(title);
                news.setContent(content);
                news.setImage(image);
                newsService.saveNews(news);
                logger.info("抽取搜狐车《{}》成功！", news.getTitle());
            } catch (Exception e) {
                logger.error("车抽取失败:{}", news.getUrl());
                e.printStackTrace();
            }
        });
    }



    @Override
    public void pullEntertain(){
        logger.info("开始拉取娱乐！");
        // 1.获取首页
        Document html = null;
        try {
            System.out.println("lkodfd");
            html = getHtmlFromUrl(Entertainurl, false);
        } catch (Exception e) {
            logger.error("==============获取搜狐首页失败: {}=============", Entertainurl);
            e.printStackTrace();
            return;
        }
        // 2.jsoup获取新闻<a>标签
        Elements newsATags = html
                .select("div")
                .select("a");
        System.out.println(newsATags);


        // 3.从<a>标签中抽取基本信息，封装成news
        HashSet<News> newsSet = new HashSet<>();
        for (Element a : newsATags) {
            String url = a.attr("href");
            String title = a.text();
            if(url.length()<25){
                url = "";
                continue;
            }
            if (url.substring(0, 2).equals("//")) {
                System.out.println("钱钱"+url);
                url = "http:" + url;
            }

            News n = new News();
            n.setSource("搜狐");
            n.setUrl(url);
            n.setTitle(title);
            n.setCreateDate(new Date());
            n.setTag("4");
            newsSet.add(n);
        }
        // 4.根据新闻url访问新闻，获取新闻内容
        newsSet.forEach(news -> {
            if(news.getUrl().length()<25){
                return;
            }
            logger.info("开始抽取搜狐娱乐：{}", news.getUrl());
            Document newsHtml = null;
            try {
                newsHtml = getHtmlFromUrl(news.getUrl(), false);
                Element newsContent = newsHtml.select("div#article-container")
                        .select("div.main")
                        .select("div.text")
                        .first();
                String title = newsContent.select("div.text-title").select("h1").text();
                String content = newsContent.select("article.article").first().toString();
                String image = NewsUtils.getImageFromContent(content);

                news.setTitle(title);
                news.setContent(content);
                news.setImage(image);
                newsService.saveNews(news);
                logger.info("抽取搜狐娱乐《{}》成功！", news.getTitle());
            } catch (Exception e) {
                logger.error("娱乐抽取失败:{}", news.getUrl());
                e.printStackTrace();
            }
        });
    }


    @Override
    public void pullIt(){
        logger.info("开始拉取科技！");
        // 1.获取首页
        Document html = null;
        try {
            System.out.println("lkodfd");
            html = getHtmlFromUrl(Iturl, false);
            System.out.println("lkggsgfgggggggggggggggg");
        } catch (Exception e) {
            logger.error("==============获取搜狐首页失败: {}=============", Iturl);
            e.printStackTrace();
            return;
        }
        // 2.jsoup获取新闻<a>标签
        Elements newsATags = html
                .select("div")
                .select("ul")
                .select("li")
                .select("a");
        System.out.println(newsATags);


        // 3.从<a>标签中抽取基本信息，封装成news
        HashSet<News> newsSet = new HashSet<>();
        for (Element a : newsATags) {
            String url = a.attr("href");
            String title = a.text();

            if (url.substring(0, 1).equals("/")) {
                url = "http:" + url;
            }

            News n = new News();
            n.setSource("搜狐");
            n.setUrl(url);
            n.setTitle(title);
            n.setCreateDate(new Date());
            n.setTag("5");
            newsSet.add(n);
        }
        // 4.根据新闻url访问新闻，获取新闻内容
        newsSet.forEach(news -> {
            logger.info("开始抽取搜狐科技内容：{}", news.getUrl());
            Document newsHtml = null;
            try {
                newsHtml = getHtmlFromUrl(news.getUrl(), false);
                Element newsContent = newsHtml.select("div#article-container")
                        .select("div.main")
                        .select("div.text")
                        .first();
                String title = newsContent.select("div.text-title").select("h1").text();
                String content = newsContent.select("article.article").first().toString();
                String image = NewsUtils.getImageFromContent(content);

                news.setTitle(title);
                news.setContent(content);
                news.setImage(image);
                newsService.saveNews(news);
                logger.info("抽取搜狐科技《{}》成功！", news.getTitle());
            } catch (Exception e) {
                logger.error("科技抽取失败:{}", news.getUrl());
                e.printStackTrace();
            }
        });
    }

}
