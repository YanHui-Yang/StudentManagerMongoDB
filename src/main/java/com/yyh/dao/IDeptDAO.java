package com.yyh.dao;

import com.mongodb.DBObject;
import com.yyh.pojo.Dept;

import java.util.List;

public interface IDeptDAO {

    List<DBObject> findAll(Integer pageNum, Integer pageSize, String deptName);

    Long getAllCount();

    Dept findById(String id);

    void insertDept(Dept dept);

    void updateDept(Dept dept);

    void deleteById(String id);
}
