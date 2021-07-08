package com.example.backend.service.serviceimpl;


import com.example.backend.BO.RegisterBO;
import com.example.backend.VO.Result;
import com.example.backend.BO.UserDTO;
import com.example.backend.mapper.UserDao;
import com.example.backend.pojo.User;
import com.example.backend.service.TokenService;
import com.example.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    @Autowired(required = false)
    private UserDao userDao;

    @Autowired
    private TokenService tokenService;


    @Override
    public Result userLogin(Map<String, Object> params) {
        String param_telephone = params.get("phone").toString();
        String param_password = params.get("password").toString();
        String password = userDao.selectPasswordByPhone(param_telephone);
        if (password == null) {
            return Result.BAD().msg("账户不存在").build();
        } else if (!password.equals(param_password)) {
            return Result.BAD().msg("密码错误").build();
        }else{
            return Result.OK().build();
        }
    }


    @Override
    public int register(RegisterBO register) {

        String p = userDao.selectPasswordByPhone(register.getPhone());
        if(p!=null){
            return 0;
        }

        User user = new User();
        user.setAvatar(register.getAvatar());
        user.setEmail(register.getEmail());
        user.setNickname(register.getNickname());
        user.setPassword(register.getPassword());
        user.setPhone(register.getPhone());
        user.setUsername(register.getUsername());
        user.setUserId(register.getUserId());


        return userDao.insert(user);
    }

    @Override
    public User getUser(String phone) {
        return userDao.selectByphone(phone);
    }

    @Override
    public int modifyUser(UserDTO userdto) {


        User user1 = userDao.selectByphone(userdto.getPhone());
        System.out.println(user1);
        if (user1 == null) {
            return 0;
        }
        user1.setUsername(userdto.getUsername());
        user1.setPhone(userdto.getPhone());
        user1.setPassword(userdto.getPassword());
        user1.setEmail(userdto.getEmail());
        user1.setAvatar(userdto.getAvatar());
        user1.setNickname(userdto.getNickname());
        return userDao.updateByPrimaryKeySelective(user1);
    }

}
