package com.example.backend.controller;



import com.example.backend.BO.RegisterBO;
import com.example.backend.VO.Result;
import com.example.backend.pojo.User;
import com.example.backend.service.UserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Api(tags = "UserController",description = "用户的操作接口")
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    @ResponseBody
    public Result userLogin(@RequestBody Map<String, Object> params) {
        return userService.userLogin(params);
    }

/*    *//**//**
     *//**//*
    @PutMapping("/user")
    @ResponseBody
    public Result userUpdate(@RequestBody Map<String, Object> params) {
*//**//*        int update = userService.updateUserRole(params);
        if (update > 0) {
            return Result.OK().build();
        }*//**//*
        return Result.BAD().build();
    }*/

    @PostMapping("/register")
    @ResponseBody
    public Result userRegister(@RequestBody RegisterBO register) {
        int insert = userService.register(register);
        if (insert > 0) {
            return Result.OK().build();
        }
        return Result.BAD().build();
    }


/*    @PutMapping("/verify")
    @ResponseBody
    public Result verifyUser(@RequestBody Map<String, Object> params) {
        int update = userService.verifyUser(params);
        if (update > 0) {
            return Result.OK().build();
        }
        return Result.BAD().build();
    }*/

}