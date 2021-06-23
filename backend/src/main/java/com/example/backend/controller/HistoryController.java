package com.example.backend.controller;

import com.example.backend.BO.HistoryBO;
import com.example.backend.VO.Result;
import com.example.backend.pojo.History;
import com.example.backend.service.HistoryService;
import io.swagger.annotations.Api;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author: rain
 * @date: 2021/6/21 20:27
 * @description:
 */
@Api(tags = "historyController",description = "历史记录的操作接口")
@RestController
@RequestMapping("/history")
public class HistoryController {


    @Autowired
    HistoryService historyService;

    @PostMapping("/add")
    @ResponseBody
    public Result addHistory(@RequestBody HistoryBO historyBO) {
        int add = historyService.AddHistory(historyBO);
        if (add > 0) {
            return Result.OK().build();
        }
        return Result.BAD().build();
    }

    @GetMapping("/get")
    @ResponseBody
    public Result getHistory(@RequestParam String phone) {
        List<History> list = historyService.getHistoryByUserId(phone);
        if (list!=null) {
            return Result.OK().data(list).build();
        }
        return Result.BAD().build();
    }

    @DeleteMapping("/delete")
    @ResponseBody
    public Result deleteHistory(@RequestParam String phone,
                                @RequestParam String history_url) {
        int delete = historyService.DeleteHistory(phone, history_url);
        if (delete > 0) {
            return Result.OK().build();
        }
        return Result.BAD().build();
    }


    @PutMapping("/update")
    @ResponseBody
    public Result updateHistory(@RequestParam String phone,
                             @RequestParam String history_url) {
        int update = historyService.UpdateHistory(phone, history_url);
        if (update>0) {
            return Result.OK().build();
        }
        return Result.BAD().build();
    }


}
