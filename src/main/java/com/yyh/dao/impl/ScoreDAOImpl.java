package com.yyh.dao.impl;

import com.mongodb.*;
import com.yyh.dao.IScoreDAO;
import com.yyh.pojo.Score;
import com.yyh.util.MongoUtil;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class ScoreDAOImpl implements IScoreDAO {

    @Override
    public List<DBObject> findAll(Integer pageNum, Integer pageSize, String courseName, String stuName) {
        //模糊查询
        Pattern stuNamePattern = Pattern.compile("^.*" + stuName + ".*$", Pattern.CASE_INSENSITIVE);
        DBObject dbObject = new BasicDBObject();
        if (stuName != null && !"".equals(stuName))
            dbObject.put("stuName", stuNamePattern);
        if (courseName != null && !"".equals(courseName))
            dbObject.put("courseName", courseName);
        DBCursor dbCursor = MongoUtil.getCollection("score").find(dbObject);
        List<DBObject> all = new ArrayList<>();
        if (pageNum != -1 && pageSize != -1)
            dbCursor.skip((pageNum - 1) * pageSize).limit(pageSize);
        while (dbCursor.hasNext()) {
            all.add(dbCursor.next());
        }
        // 解决ObjectId序列化问题
        for (int i = 0; i < all.size(); i++) {
            DBObject obj = all.get(i);
            obj.put("_id", String.valueOf(obj.get("_id")));
        }
        return all;
    }

    @Override
    public Long getAllCount() {
        return MongoUtil.getCollection("score").count();
    }

    @Override
    public Score findById(String id) {
        DBCollection dbCollection = MongoUtil.getCollection("score");
        DBObject dbObject = new QueryBuilder().put("_id").is(new ObjectId(id)).get();
        DBObject res = dbCollection.findOne(dbObject);
        if (res != null) return new Score(String.valueOf(res.get("_id"))
                , String.valueOf(res.get("stuName"))
                , String.valueOf(res.get("courseName"))
                , Double.parseDouble(String.valueOf(res.get("score"))));
        else return null;
    }

    @Override
    public void insertScore(Score score) {
        DBObject dbObject = new BasicDBObject();
        dbObject.put("stuName", score.getStuName());
        dbObject.put("courseName", score.getCourseName());
        dbObject.put("score", score.getScore());
        MongoUtil.getCollection("score").insert(dbObject);
    }

    @Override
    public void updateScore(Score score) {
        DBCollection dbCollection = MongoUtil.getCollection("score");
        //旧记录
        DBObject oldObject = new BasicDBObject();
        oldObject.put("_id", new ObjectId(score.getId()));
        // 找到这一条记录   新纪录
        DBObject newObject = dbCollection.findOne(new ObjectId(score.getId()));
        if (score.getStuName() != null)
            newObject.put("stuName", score.getStuName());
        if (score.getCourseName() != null)
            newObject.put("courseName", score.getCourseName());
        if (score.getScore() != null)
            newObject.put("score", score.getScore());
        dbCollection.update(oldObject, newObject);
    }

    @Override
    public void deleteById(String id) {
        DBCollection dbCollection = MongoUtil.getCollection("score");
        DBObject dbObject = new BasicDBObject();
        dbObject.put("_id", new ObjectId(id));
        dbCollection.remove(dbObject);
    }
}
