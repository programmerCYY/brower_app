package com.example.mywebdemo.user;

public class User {
    private String phone;
    private String username;
    private String nickname;
    private String email;
    private String avatar;
    private String password;


    public User(String phone, String username, String nickname, String email, String avatar, String password) {
        this.phone = phone;
        this.username = username;
        this.nickname = nickname;
        this.email = email;
        this.avatar = avatar;
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public String getUsername() {
        return username;
    }

    public String getNickname() {
        return nickname;
    }

    public String getEmail() {
        return email;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getPassword() {
        return password;
    }
}
