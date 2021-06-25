package com.example.backend;

import com.example.backend.mapper.UserDao;
import com.example.backend.pojo.News;
import com.example.backend.pojo.User;
import com.example.backend.schedule.NewsPuller;
import com.example.backend.schedule.impl.SohuNewsPuller;
import com.example.backend.service.NewsService;
import com.example.backend.util.NewsUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashSet;

@SpringBootTest
class BackendApplicationTests {
    private static final Logger logger = LoggerFactory.getLogger(SohuNewsPuller.class);
    @Value("${news.sohu.Carurl}")
    private String Carurl;
    @Autowired
    private NewsService newsService;

    @Qualifier("sohuNewsPuller")
    @Autowired(required = false)
    private NewsPuller newsPuller;

    @Test
    void contextLoads() {
        logger.info("开始拉取搜狐体育！");
        // 1.获取首页
        Document html = null;
        try {
            html = newsPuller.getHtmlFromUrl(Carurl, false);
        } catch (Exception e) {
            logger.error("==============获取搜狐首页失败: {}=============", Carurl);
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

/*            if (url.substring(0, 1).equals("/")) {
                url = "https://www.sohu.com" + url;
            }*/

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
            logger.info("开始抽取搜狐体育内容：{}", news.getUrl());
            Document newsHtml = null;
            try {
                newsHtml = newsPuller.getHtmlFromUrl(news.getUrl(), false);
                Element newsContent = newsHtml.select("div.content area")
                        .select("div.article-box l")
                        .first();
                String title = newsContent.select("div.article-title").select("h3").text();
                String content = newsContent.select("article.article-text").first().toString();
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

}
