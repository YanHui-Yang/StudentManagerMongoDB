package com.yyh.dao;

import com.mongodb.DBObject;
import com.yyh.pojo.Clazz;

import java.util.List;

public interface IClazzDAO {

    List<DBObject> findAll(Integer pageNum, Integer pageSize, String deptName, String majorName, String clazzName);

    Long getAllCount();

    Clazz findById(String id);

    void insertClazz(Clazz clazz);

    void updateClazz(Clazz clazz);

    void deleteById(String id);
}
