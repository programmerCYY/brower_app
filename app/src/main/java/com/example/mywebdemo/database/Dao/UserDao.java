package com.example.mywebdemo.database.Dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.mywebdemo.database.Entity.User;

import java.util.List;

@Dao
public interface UserDao {
    @Insert
    void insert(User users);

    @Query("SELECT * FROM user WHERE account=:account")
    List<User> getUser(String account);
}
