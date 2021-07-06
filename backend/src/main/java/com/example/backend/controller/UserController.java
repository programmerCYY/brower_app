package com.example.backend.controller;



import com.example.backend.BO.RegisterBO;
import com.example.backend.VO.Result;
import com.example.backend.BO.UserDTO;
import com.example.backend.pojo.User;
import com.example.backend.service.UserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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


    @PostMapping("/register")
    @ResponseBody
    public Result userRegister(@RequestBody RegisterBO register) {
        int insert = userService.register(register);
        if (insert > 0) {
            return Result.OK().build();
        }else if (insert == 0){
            Result.BAD().data("用户已存在").build();
        }
        return Result.BAD().build();
    }


    @PostMapping("/modify")
    @ResponseBody
    public Result verifyUser(@RequestBody UserDTO userDTO) {
        int update = userService.modifyUser(userDTO);
        if (update > 0) {
            return Result.OK().build();
        }else if (update == 0){
            Result.BAD().data("用户不存在").build();
        }
        return Result.BAD().build();
    }

    @GetMapping("/getuser")
    @ResponseBody
    public Result getUser(@RequestParam String phone){
        User user = userService.getUser(phone);
        if(user!=null){
            return Result.OK().data(user).build();
        }

        return Result.BAD().build();
    }

}