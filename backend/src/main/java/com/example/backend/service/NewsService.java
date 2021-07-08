package com.example.backend.service;



import com.example.backend.pojo.News;
import com.example.backend.pojo.NewsExample;

import java.util.List;

/**
 * @author XXX
 * @since 2018-05-22
 */
public interface NewsService {

    int saveNews(News news);

    List<News> searchNewsForPage(int page, int pageSize, NewsExample example);

    Long countByExample(NewsExample example);

    List<News> selectByTag(String tag,int pagenum,int pagesize);
}
