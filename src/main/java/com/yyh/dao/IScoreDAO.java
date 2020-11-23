package com.yyh.dao;

import com.mongodb.DBObject;
import com.yyh.pojo.Score;

import java.util.List;

public interface IScoreDAO {

    List<DBObject> findAll(Integer pageNum, Integer pageSize, String courseName, String stuName);

    Long getAllCount();

    Score findById(String id);

    void insertScore(Score score);

    void updateScore(Score score);

    void deleteById(String id);
}
