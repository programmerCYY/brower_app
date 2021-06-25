package com.example.backend.service;

import com.example.backend.BO.FlagBO;
import com.example.backend.dto.FlagDTO;
import com.example.backend.pojo.Flag;

import java.util.List;

/**
 * @author: rain
 * @date: 2021/6/21 16:22
 * @description:
 */
public interface FlagService {

    List<Flag> getFlagsByUserId(String flag_user);

    int AddFlags(Flag Flag);

    int DeleteFlags(String flag_user,String flag_url);

    int UpdateFlags(String flag_user,String flag_url, String flag_name);

    List<Flag> getFlagsByKey(String flag_user,String point);

}
