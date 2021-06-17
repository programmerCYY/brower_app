package com.example.mywebdemo.database.Database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.mywebdemo.database.Dao.UserDao;
import com.example.mywebdemo.database.Entity.User;

@Database(entities = {User.class},version = 1,exportSchema = false)
public abstract class UserDatabase extends RoomDatabase {
    public abstract UserDao getUserDao();
}
