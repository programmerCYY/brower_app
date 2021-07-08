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


    @ApiOperation(value = "拉取搜狐新闻")
    @GetMapping("/sohunews")
    public Result pullSohuNews(@RequestParam int pageNum,
                               @RequestParam int pageSize) {
        List<News> list = newsService.selectByTag("1",pageNum,pageSize);
        return Result.OK().data(list).build();
    }

    @ApiOperation(value = "拉取搜狐体育")
    @GetMapping("sohusports")
    public Result pullSohuSports(@RequestParam int pageNum,
                                 @RequestParam int pageSize) {
        List<News> list = newsService.selectByTag("2",pageNum,pageSize);
        return Result.OK().data(list).build();
    }

    @ApiOperation(value = "拉取搜狐车")
    @GetMapping("sohucars")
    public Result pullSohuCars(@RequestParam int pageNum,
                               @RequestParam int pageSize) {
        List<News> list = newsService.selectByTag("3",pageNum,pageSize);

        return Result.OK().data(list).build();
    }

    @ApiOperation(value = "拉取搜狐娱乐")
    @GetMapping("sohuentertains")
    public Result pullSohuEntertain(@RequestParam int pageNum,
                                    @RequestParam int pageSize) {
        List<News> list = newsService.selectByTag("4",pageNum,pageSize);

        return Result.OK().data(list).build();
    }

    @ApiOperation(value = "拉取搜狐科技")
    @GetMapping("sohuit")
    public Result pullSohuIt(@RequestParam int pageNum,
                             @RequestParam int pageSize) {
        List<News> list = newsService.selectByTag("5",pageNum,pageSize);
        return Result.OK().data(list).build();
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
