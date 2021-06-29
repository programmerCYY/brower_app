package com.example.backend.controller;

import com.example.backend.BO.DeleteBO;
import com.example.backend.BO.FlagBO;
import com.example.backend.VO.Result;
import com.example.backend.pojo.Flag;
import com.example.backend.service.FlagService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author: rain
 * @date: 2021/6/21 20:27
 * @description:
 */
@Api(tags = "flagController",description = "书签的操作接口")
@RestController
@RequestMapping("/flag")
public class FlagController {
    @Autowired
    FlagService flagService;

    @PostMapping("/add")
    @ResponseBody
    public Result addFlags(@RequestBody Flag flag) {
        int add = flagService.AddFlags(flag);
        if (add > 0) {
            return Result.OK().build();
        }
        return Result.BAD().build();
    }

    @GetMapping("/get")
    @ResponseBody
    public Result getFlags(@RequestParam String phone) {
        List<Flag> list = flagService.getFlagsByUserId(phone);
        if (list!=null) {
            return Result.OK().data(list).build();
        }
        return Result.BAD().build();
    }

    @PostMapping("/delete")
    @ResponseBody
    public Result deleteFlags(@RequestBody DeleteBO deleteBO) {
        int delete = flagService.DeleteFlags(deleteBO);
        if (delete > 0) {
            return Result.OK().build();
        }
        return Result.BAD().build();
    }


    @PutMapping("/update")
    @ResponseBody
    public Result updateFlags(@RequestParam String phone,
                              @RequestParam String old_url,
                              @RequestParam String flag_name,
                              @RequestParam String new_url) {
        int update = flagService.UpdateFlags(phone, old_url,flag_name,new_url);
        if (update>0) {
            return Result.OK().build();
        }
        return Result.BAD().build();
    }

    @GetMapping("/search")
    @ResponseBody
    public Result searchFlags(@RequestParam String flag_user,
                              @RequestParam String flag_name) {
        List<Flag> list = flagService.getFlagsByKey(flag_user, flag_name);
        if (list!=null) {
            return Result.OK().data(list).build();
        }
        return Result.BAD().build();
    }

}
