package com.yyh.dao;

import com.mongodb.DBObject;
import com.yyh.pojo.Student;

import java.util.List;

public interface IStudentDAO {

    //查询全部、分页、模糊deptName、majorName、clazzName、stuNumber、stuName
    List<DBObject> findAll(Integer pageNum, Integer pageSize, String deptName, String majorName, String clazzName, String stuNumber, String stuName);

    Long getAllCount();

    Student findById(String id);

    void insertStudent(Student student);

    void updateStudent(Student student);

    void deleteById(String id);
}
