package com.example.mywebdemo.database;

import android.content.Context;

import androidx.room.Room;

import com.example.mywebdemo.database.Dao.HistoryDao;
import com.example.mywebdemo.database.Dao.UserDao;
import com.example.mywebdemo.database.Database.HistoryDatabase;
import com.example.mywebdemo.database.Database.UserDatabase;
import com.example.mywebdemo.database.Entity.History;
import com.example.mywebdemo.database.Entity.User;

import java.util.List;

public class DataBaseManager {

    private Context m_Context;
    private static User nowUser;

    private static final int FLAG_HISTORY = 0;
    private static final int FLAG_BOOKMARK = 1;

    DataBaseManager(Context context){
        m_Context = context;
    }

    /*-----------------------------about user------------------------*/
    // 添加用户 如果成功返回 succ ， 否则返回account exist
    String AddUser(String sAccount,String sPassword){
        UserDao userDao = GetUserDao();
        List<User> userList =  userDao.getUser(sAccount);
        if( userList.isEmpty() == false ){
            return "account exist";
        }

        User user = new User();
        user.setAccount(sAccount);
        user.setPassword(sPassword);

        userDao.insert(user);
        return "succ";
    }

    // 用户登录 , 成功则返回succ ， 否则返回对应错误码
    String LoginUser(String sAccount,String sPassword){
        UserDao userDao = GetUserDao();
        List<User> userList =  userDao.getUser(sAccount);
        if( userList.isEmpty() == false ){
            return "account not exist";
        }

        if(userList.get(0).getPassword().equals(sPassword) == false){
            return "password error";
        }
        nowUser.setPassword(sPassword);
        nowUser.setAccount(sAccount);
        return "succ";
    }

    // 查询当前是哪个用户在使用
    public User QueryNowUser(){
        return nowUser;
    }

    /*-------------------------history---------------------------*/
    // 添加历史记录
    public void AddHistory(String sAccount,String sUrl,String sTitle){
        HistoryDao historyDao = GetHistoryDao();
        History history = new History();
        history.setAccount(sAccount);
        history.setTitle(sTitle);
        history.setUrl(sUrl);
        history.setFlag(FLAG_HISTORY);
    }

    // 获取用户所有记录
    public List<History> GetHistory(String sAccount){
        HistoryDao historyDao = GetHistoryDao();
        List<History> historyList = historyDao.getAllHistory(sAccount,FLAG_HISTORY);
        return historyList;
    }
    // 删除全部历史
    public void DeleteAllHistory(String sAccount){
        HistoryDao historyDao = GetHistoryDao();
        historyDao.deleteAllHistory(sAccount,FLAG_HISTORY);
    }

    /*-------------------------bookmark---------------------------*/
    // 添加标签
    public void AddBookMark(String sAccount,String sUrl,String sTitle){
        HistoryDao historyDao = GetHistoryDao();
        History history = new History();
        history.setAccount(sAccount);
        history.setTitle(sTitle);
        history.setUrl(sUrl);
        history.setFlag(FLAG_HISTORY);
    }

    // 获取用户所有标签
    public List<History> GetBookMark(String sAccount){
        HistoryDao historyDao = GetHistoryDao();
        List<History> historyList = historyDao.getAllHistory(sAccount,FLAG_BOOKMARK);
        return historyList;
    }

    //删除某些标签
    public void deleteBookMark(List<History> historyList){
        HistoryDao dao = GetHistoryDao();
        for(int i = 0;i<historyList.size();++i){
            History history = historyList.get(i);
            dao.deleteTheHistory(history.getId());
        }
    }
    /*------------DAO*------------------*/
    private UserDao GetUserDao(){
        // 通过Room的databaseBuilder，获取到UserDataBase的对象。
        UserDatabase db = Room.databaseBuilder(m_Context,
                UserDatabase.class, "UserDatabase").build();

        // 通过UserDataBase，获取UserDao对象。
        UserDao userDao = db.getUserDao();
        return userDao;
    }

    private HistoryDao GetHistoryDao(){
        HistoryDatabase db = Room.databaseBuilder(m_Context,
                HistoryDatabase.class, "HistoryDatabase").build();

        // 通过UserDataBase，获取UserDao对象。
        HistoryDao historyDao = db.getHistoryDao();
        return historyDao;
    }
}
