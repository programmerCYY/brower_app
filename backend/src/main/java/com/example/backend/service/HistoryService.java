package com.example.backend.service;


import com.example.backend.BO.DeleteBO;
import com.example.backend.BO.HistoryBO;
import com.example.backend.pojo.Flag;
import com.example.backend.pojo.History;

import java.util.List;

/**
 * @author: rain
 * @date: 2021/6/21 16:22
 * @description:
 */
public interface HistoryService {
    List<History> getHistoryByUserId(String history_user);

    int AddHistory(HistoryBO historyBO);

    int DeleteHistory(DeleteBO deleteBO);

    int UpdateHistory(String history_user,String history_url);

}
