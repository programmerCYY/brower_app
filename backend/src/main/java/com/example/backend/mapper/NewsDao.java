package com.example.backend.mapper;

import com.example.backend.pojo.News;
import com.example.backend.pojo.NewsExample;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewsDao {
    int deleteByPrimaryKey(Integer id);

    int insert(News record);

    int insertSelective(News record);

    News selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(News record);

    int updateByPrimaryKey(News record);

    long countByExample(NewsExample example);

    List<News> selectByExampleWithBLOBs(NewsExample example);

    List<News> selectByTag(String tag);
}