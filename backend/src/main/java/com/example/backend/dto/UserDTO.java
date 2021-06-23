package com.example.backend.dto;



import com.example.backend.pojo.User;
import lombok.Data;

@Data
public class UserDTO {


    private String nickname;

    private String avatar;

    private String username;

    private String email;

    private String telephone;

    private String password;

    private String userId;

    public UserDTO(User user) {
        this.nickname = user.getNickname();
        this.avatar = user.getAvatar();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.telephone = user.getPhone();
        this.userId = user.getUserId();
        this.password = user.getPassword();
    }
}
