package com.example.backend.service;




import com.example.backend.BO.RegisterBO;
import com.example.backend.VO.Result;
import com.example.backend.BO.UserDTO;
import com.example.backend.pojo.User;

import java.util.Map;

public interface UserService {

    Result userLogin(Map<String, Object> params);

    int register(RegisterBO register);

    User getUser(String phone);

    int modifyUser(UserDTO userDTO);

}
