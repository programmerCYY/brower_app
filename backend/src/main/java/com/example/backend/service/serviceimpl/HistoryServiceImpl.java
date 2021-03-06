package com.example.backend.service.serviceimpl;


import com.example.backend.BO.DeleteBO;
import com.example.backend.BO.HistoryBO;
import com.example.backend.mapper.HistoryDao;
import com.example.backend.pojo.Flag;
import com.example.backend.pojo.History;
import com.example.backend.service.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author: rain
 * @date: 2021/6/21 16:23
 * @description:
 */
@Service
public class HistoryServiceImpl implements HistoryService {

    @Autowired(required = false)
    HistoryDao historyDao;


    @Override
    public List<History> getHistoryByUserId(String history_user) {
        return historyDao.selectByUserId(history_user);
    }

    @Override
    public int AddHistory(HistoryBO historyBO) {
        History history = historyDao.selectByTwo(historyBO.getHistoryUser(),historyBO.getHistoryUrl());
        if(history!=null){
            history.setHistoryTime(new Date());
            return historyDao.updateByPrimaryKeySelective(history);
        }
        history = new History();
        history.setHistoryIcon(historyBO.getHistoryIcon());

        history.setHistoryName(historyBO.getHistoryName());
        history.setHistoryUser(historyBO.getHistoryUser());
        history.setHistoryUrl(historyBO.getHistoryUrl());
        history.setHistoryTime(new Date());

        return historyDao.insert(history);
    }

    @Override
    public int DeleteHistory(DeleteBO deleteBO) {
        String history_user = deleteBO.getUser();
        String history_url = deleteBO.getUrl();
        return historyDao.deleteBytwo(history_user, history_url);
    }

    @Override
    public int UpdateHistory(String history_user, String history_url) {

        Date newDate = new Date();
        return historyDao.updateBytwo(history_user,history_url,newDate);
    }
}
