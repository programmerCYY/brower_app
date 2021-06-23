package com.example.backend.BO;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Map;

@Data
public class RegisterBO {

    private String userId;

    @NotBlank(message = "username不能为空")
    private String username;

    @NotBlank(message = "telephone不能为空")
    private String phone;


    @NotBlank(message = "nickname不能为空")
    private String nickname;

    @NotBlank(message = "avatar不能为空")
    private String avatar;

    @NotBlank(message = "email不能为空")
    private String email;

    @NotBlank(message = "password不能为空")
    private String password;

}
