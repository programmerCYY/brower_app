package com.example.backend.BO;



import com.example.backend.pojo.User;
import lombok.Data;

@Data
public class UserDTO {


    private String nickname;

    private String avatar;

    private String username;

    private String email;

    private String phone;

    private String password;

}
