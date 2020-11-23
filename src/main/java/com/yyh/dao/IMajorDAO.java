package com.yyh.dao;

import com.mongodb.DBObject;
import com.yyh.pojo.Major;

import java.util.List;

public interface IMajorDAO {

    List<DBObject> findAll(Integer pageNum, Integer pageSize, String deptName, String majorName);

    Long getAllCount();

    Major findById(String id);

    void insertMajor(Major major);

    void updateMajor(Major major);

    void deleteById(String id);
}
