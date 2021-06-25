package com.example.backend.service.serviceimpl;

import com.example.backend.BO.FlagBO;
import com.example.backend.dto.FlagDTO;
import com.example.backend.mapper.FlagDao;
import com.example.backend.pojo.Flag;
import com.example.backend.service.FlagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: rain
 * @date: 2021/6/21 16:23
 * @description:
 */
@Service
public class FlagServiceImpl implements FlagService {

    @Autowired(required = false)
    FlagDao flagDao;

    @Override
    public List<Flag> getFlagsByUserId(String flag_user) {
        return flagDao.selectByUserId(flag_user);
    }

    @Override
    public int AddFlags(Flag flag) {


        Flag f = flagDao.selectBytwo(flag.getFlagUser(),flag.getFlagUrl());

        if(f!=null){
            return 0;
        }
        return flagDao.insert(flag);
    }

    @Override
    public int DeleteFlags(String flag_user, String flag_url) {

        Flag flag = flagDao.selectBytwo(flag_user, flag_url);
        if(flag == null){
            return 0;
        }

        return flagDao.deleteBytwo(flag_user, flag_url);
    }

    @Override
    public int UpdateFlags(String flag_user, String flag_url, String flag_name) {
        Flag flag = flagDao.selectBytwo(flag_user, flag_url);
        if(flag == null){
            return 0;
        }
        return flagDao.updateBytwo(flag_user,flag_url,flag_name);
    }

    @Override
    public List<Flag> getFlagsByKey(String flag_user,String point) {

        String point1 = "%"+point+"%";
        return flagDao.selectByKey(flag_user,point1);
    }
}
