package com.cxp.room;

import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.cxp.room.db.BookDao;
import com.cxp.room.db.MyDatabase;
import com.cxp.room.db.UserDao;
import com.cxp.room.entity.Book;
import com.cxp.room.entity.User;
import com.cxp.room.entity.UserAndBook;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView tv;

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            tv.setText(msg.obj.toString());
            return true;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = findViewById(R.id.main_tv);
    }

    public void clickLis(View view) {

        MyDatabase mUserDatabase = Room.databaseBuilder(getApplicationContext(), MyDatabase.class, "room.db").build();
        final UserDao dao = mUserDatabase.getUserDao();
        final BookDao bookDao = mUserDatabase.getBookDao();
        switch (view.getId()) {
            case R.id.main_bt1:
                //必须要在子线程里执行数据库操作
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        //增加数据
                        addData(dao);
                    }
                }).start();
                break;
            case R.id.main_bt2:
                //必须要在子线程里执行数据库操作
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        //查询数据
                        queryData(dao);
                    }
                }).start();
                break;
            case R.id.main_bt3:
                //必须要在子线程里执行数据库操作
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        //修改数据
                        updateData(dao);
                    }
                }).start();
                break;
            case R.id.main_bt4:
                //必须要在子线程里执行数据库操作
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        //删除数据
                        deleteData(dao);
                    }
                }).start();
                break;
            case R.id.main_bt5:
                //必须要在子线程里执行数据库操作
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        //批量增加数据
                        addDataAll(dao);
                    }
                }).start();
                break;
            case R.id.main_bt6:
                //必须要在子线程里执行数据库操作
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        //批量修改数据
                        updateDataAll(dao);
                    }
                }).start();
                break;
            case R.id.main_bt7:
                //必须要在子线程里执行数据库操作
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        //批量删除数据
                        deleteDataAll(dao);
                    }
                }).start();
                break;
            case R.id.main_bt8:
                //必须要在子线程里执行数据库操作
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        //增加Book数据
                        addBook(bookDao);
                    }
                }).start();
                break;
            case R.id.main_bt9:
                //必须要在子线程里执行数据库操作
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        //查询Book数据
                        queryBook(bookDao);
                    }
                }).start();
                break;
            case R.id.main_bt10:
                //必须要在子线程里执行数据库操作
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        //多表查询
                        queryBookAndUser(bookDao);
                    }
                }).start();
                break;
            case R.id.main_bt11:
                //必须要在子线程里执行数据库操作
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        //条件查询
                        queryUserInfo(dao);
                    }
                }).start();
                break;
        }


    }


    /**
     * 增加数据
     */
    private void addData(UserDao dao) {
        User user = new User();
        user.setName("CXP");
        user.setAge(18);
        user.setUpdateTime(System.currentTimeMillis());
        user.setSex(1);
        dao.insertUser(user);
    }

    /**
     * 批量增加数据
     */
    private void addDataAll(UserDao dao) {
        List<User> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            User user = new User();
            user.setName("CXP");
            user.setAge(i);
            user.setUpdateTime(System.currentTimeMillis());
            user.setSex(i);
            list.add(user);
        }
        dao.insertUserAll(list);
    }


    /**
     * 条件查询数据
     */
    private void queryUserInfo(UserDao dao) {
        User user = dao.queryUser(10);
        Message message = new Message();
        message.obj = user.getName() + "，" + user.getAge() + "\n";
        handler.sendMessage(message);
    }

    /**
     * 查询数据
     */
    private void queryData(UserDao dao) {
        List<User> list = dao.queryUserAll();
        Message message = new Message();
        StringBuffer sbStr = new StringBuffer();
        if (list != null && list.size() != 0) {
            for (User user : list) {
                sbStr.append(user.getName() + "，" + user.getAge() + "\n");
            }
        }
        message.obj = sbStr.toString();
        handler.sendMessage(message);
    }

    /**
     * 修改数据
     */
    private void updateData(UserDao dao) {
        User user = new User();
        user.setId(1);
        user.setName("CXP1");
        user.setAge(10);
        user.setUpdateTime(System.currentTimeMillis());
        dao.updateUser(user);
    }

    /**
     * 批量修改数据
     */
    private void updateDataAll(UserDao dao) {
        List<User> userList = dao.queryUserAll();
        for (User user : userList) {
            user.setName("CXP1");
            user.setAge(12);
            user.setUpdateTime(System.currentTimeMillis());
        }
        dao.updateUserAll(userList);
    }

    /**
     * 删除数据
     */
    private void deleteData(UserDao dao) {
        User user = new User();
        user.setId(1);
        dao.deleteUser(user);
    }

    /**
     * 批量删除数据
     */
    private void deleteDataAll(UserDao dao) {
        List<User> userList = dao.queryUserAll();
        dao.deleteUserAll(userList);
    }

    /**
     * 增加Book数据
     */
    private void addBook(BookDao dao) {
        for (int i = 0; i < 5; i++) {
            Book book = new Book();
            book.setBookName("小说");
            book.setBookType("玄幻");
            book.setBookId(i+1);
            dao.insertBook(book);
        }
    }

    /**
     * 查询Book数据
     */
    private void queryBook(BookDao dao) {
        List<Book> list = dao.queryBookAll();
        Message message = new Message();
        StringBuffer sbStr = new StringBuffer();
        if (list != null && list.size() != 0) {
            for (Book book : list) {
                sbStr.append(book.getBookName() + "，" + book.getBookType() + "\n");
            }
        }
        message.obj = sbStr.toString();
        handler.sendMessage(message);
    }

    /**
     * 多表查询
     */
    private void queryBookAndUser(BookDao dao) {
        List<UserAndBook> list = dao.queryBookAndUser();
        Message message = new Message();
        StringBuffer sbStr = new StringBuffer();
        if (list != null && list.size() != 0) {
            for (UserAndBook ub : list) {
                sbStr.append(ub.getName() + "，" + ub.getAge() + "，" + ub.getBookName() + "，" + ub.getBookType() + "\n");
            }
        }
        message.obj = sbStr.toString();
        handler.sendMessage(message);
    }

}
