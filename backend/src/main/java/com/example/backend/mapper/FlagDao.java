package com.example.backend.mapper;

import com.example.backend.pojo.Flag;

import java.util.List;

public interface FlagDao {
    int deleteByPrimaryKey(Integer id);

    int insert(Flag record);

    int insertSelective(Flag record);

    Flag selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Flag record);

    int updateByPrimaryKey(Flag record);

    List<Flag> selectByUserId(String phone);

    int updateBytwo(String flag_user,String old_url,String flag_name,String new_url);

    int deleteBytwo(String flag_user,String flag_url);

    Flag selectBytwo(String flag_user,String flag_url);

    List<Flag> selectByKey(String flag_user,String point);
}