package com.example.mywebdemo.database.Entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class History {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String account;
    private String url;
    private String title;
    private int flag; // 0 是 历史 1 是书签

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getTitle() {
        return title;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }
}
