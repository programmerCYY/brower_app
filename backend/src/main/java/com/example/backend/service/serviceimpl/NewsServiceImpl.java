package com.example.backend.service.serviceimpl;


import com.example.backend.mapper.NewsDao;
import com.example.backend.pojo.News;
import com.example.backend.pojo.NewsExample;
import com.example.backend.service.NewsService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

/**
 * @author XXX
 * @since 2018-05-22
 */
@Service
public class NewsServiceImpl implements NewsService {

    @Autowired
    private NewsDao newsDao;

    @Override
    @Transactional
    public int saveNews(News news) {
        //1.check if the news is already existing
        NewsExample newsExample = new NewsExample();
        newsExample.createCriteria().andUrlEqualTo(news.getUrl());
        long count = newsDao.countByExample(newsExample);
        //2.if the news is not existing, insert it into the table
        if (count == 0) {
            return newsDao.insert(news);
        }
        return 0;
    }

    @Override
    public List<News> searchNewsForPage(int page, int pageSize, NewsExample example) {
        PageHelper.startPage(page, pageSize);
        List<News> news = newsDao.selectByExampleWithBLOBs(example);
        if (CollectionUtils.isEmpty(news)) {
            return Collections.EMPTY_LIST;
        } else {
            return news;
        }
    }

    @Override
    public Long countByExample(NewsExample example) {
        return newsDao.countByExample(example);
    }

    @Override
    public List<News> selectByTag(String tag,int pagenum,int pagesize) {
        PageHelper.startPage(pagenum, pagesize);
        return newsDao.selectByTag(tag);
    }

}
