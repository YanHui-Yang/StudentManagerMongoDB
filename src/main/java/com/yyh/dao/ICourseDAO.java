package com.yyh.dao;

import com.mongodb.DBObject;
import com.yyh.pojo.Course;

import java.util.List;

public interface ICourseDAO {

    List<DBObject> findAll(Integer pageNum, Integer pageSize, String courseName);

    Long getAllCount();

    Course findById(String id);

    void insertCourse(Course course);

    void updateCourse(Course course);

    void deleteById(String id);
}
