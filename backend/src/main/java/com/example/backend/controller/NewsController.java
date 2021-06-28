package com.example.backend.controller;


import com.example.backend.VO.Result;
import com.example.backend.pojo.News;
import com.example.backend.pojo.NewsExample;
import com.example.backend.schedule.NewsPuller;
import com.example.backend.service.NewsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @author XXX
 * @since 2018-05-13
 */
@RestController
@RequestMapping("/news")
@Api(tags = "newsController",value = "新闻拉取API")
public class  NewsController {

    private static final Logger logger = LoggerFactory.getLogger(NewsController.class);

    @Autowired
    private NewsPuller sohuNewsPuller;

    @Autowired
    private NewsService newsService;


    @ApiOperation(value = "爬虫拉取搜狐新闻")
    @GetMapping("/pull/sohu/news")
    public Result pullSohuNews() {

        sohuNewsPuller.pullNews();
        return Result.OK().build();
    }

    @ApiOperation(value = "爬虫拉取搜狐体育")
    @GetMapping("/pull/sohu/sports")
    public Result pullSohuSports() {

        sohuNewsPuller.pullSports();
        return Result.OK().build();
    }

    @ApiOperation(value = "爬虫拉取搜狐车")
    @GetMapping("/pull/sohu/cars")
    public Result pullSohuCars() {

        sohuNewsPuller.pullCars();
        return Result.OK().build();
    }

    @ApiOperation(value = "爬虫拉取搜狐娱乐")
    @GetMapping("/pull/sohu/Entertains")
    public Result pullSohuEntertain() {

        sohuNewsPuller.pullEntertain();
        return Result.OK().build();
    }

    @ApiOperation(value = "爬虫拉取搜狐科技")
    @GetMapping("/pull/sohu/it")
    public Result pullSohuIt() {

        sohuNewsPuller.pullIt();
        return Result.OK().build();
    }

    @ApiOperation(value = "获取所有新闻")
    @GetMapping
    public Result getNews(@RequestParam Integer page, @RequestParam Integer pageSize) {
        NewsExample example = new NewsExample();
        example.createCriteria();
        example.setOrderByClause("create_date desc");

        List<News> list = newsService.searchNewsForPage(page, pageSize, example);
        return Result.OK().data(list).build();
    }

    @ApiOperation("获取新闻总数")
    @GetMapping("/count")
    public Result getCount() {
        NewsExample example = new NewsExample();
        example.createCriteria();
        long num = newsService.countByExample(example);
        return Result.OK().data(num).build();
    }

}
