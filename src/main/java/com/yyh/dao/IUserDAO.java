package com.yyh.dao;

import com.mongodb.DBObject;
import com.yyh.pojo.User;

import java.util.List;

public interface IUserDAO {

    List<DBObject> findAll(Integer pageNum, Integer pageSize, String username);

    Long getAllCount();

    void insertUser(User user);

    void updateUser(User user);

    void deleteById(String id);

    User findByUsername(String username);

    User findById(String id);
}
