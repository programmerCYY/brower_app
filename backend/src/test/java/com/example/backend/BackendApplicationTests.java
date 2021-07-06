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
        String s = "<html>\n" +
                "\n" +
                "<head>\n" +
                "\t<title>Upload Result</title>\n" +
                "</head>\n" +
                "\n" +
                "<body>\n" +
                "\t<h1>MD5: f44faca741833796bec38b4916996bb2</h1>\n" +
                "\tImage upload successfully! You can get this image via this address:<br/><br/>\n" +
                "\t<a\n" +
                "\t\thref=\"/f44faca741833796bec38b4916996bb2\">http://yourhostname:4869/f44faca741833796bec38b4916996bb2</a>?w=width&h=height&g=isgray&x=position_x&y=position_y&r=rotate&q=quality&f=format\n" +
                "</body>\n" +
                "\n" +
                "</html>";

        System.out.println(getMd5(s));
    }

    public String getMd5(String s){
        StringBuilder res = new StringBuilder();

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

}
