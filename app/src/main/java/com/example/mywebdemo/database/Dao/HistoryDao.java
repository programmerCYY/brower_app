package com.example.mywebdemo.database.Dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.mywebdemo.database.Entity.History;
import com.example.mywebdemo.database.Entity.User;

import java.util.List;

@Dao
public interface HistoryDao {
    @Insert
    void insert(History history);

    @Query("SELECT * FROM history WHERE account=:account and flag=:flag")
    List<History> getAllHistory(String account,int flag);

    @Query("DELETE FROM history WHERE account=:account and flag=:flag")
    void deleteAllHistory(String account,int flag);

    @Query("DELETE FROM history WHERE id=:id")
    void deleteTheHistory(int id);
}
