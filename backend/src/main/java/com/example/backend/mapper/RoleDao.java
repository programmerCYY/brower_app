package com.example.backend.mapper;

import com.example.backend.pojo.Role;

public interface RoleDao {
    int deleteByPrimaryKey(String flagUrl);

    int insert(Role record);

    int insertSelective(Role record);

    Role selectByPrimaryKey(String flagUrl);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);
}