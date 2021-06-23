package com.example.backend.mapper;

import com.example.backend.pojo.History;

import java.util.Date;
import java.util.List;

public interface HistoryDao {
    int deleteByPrimaryKey(Integer id);

    int insert(History record);

    int insertSelective(History record);

    History selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(History record);

    int updateByPrimaryKey(History record);

    List<History> selectByUserId(String phone);

    int updateBytwo(String history_user, String history_url, Date time);

    int deleteBytwo(String history_user, String history_url);
}