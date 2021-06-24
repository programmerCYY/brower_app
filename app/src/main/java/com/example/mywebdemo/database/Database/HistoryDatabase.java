package com.example.mywebdemo.database.Database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.mywebdemo.database.Dao.HistoryDao;
import com.example.mywebdemo.database.Entity.History;
import com.example.mywebdemo.database.Entity.User;

@Database(entities = {History.class},version = 1,exportSchema = false)
public abstract class HistoryDatabase extends RoomDatabase {
    public abstract HistoryDao getHistoryDao();
}
