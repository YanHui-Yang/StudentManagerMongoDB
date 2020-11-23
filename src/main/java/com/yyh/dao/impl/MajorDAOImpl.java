package com.yyh.dao.impl;

import com.mongodb.*;
import com.yyh.dao.IMajorDAO;
import com.yyh.pojo.Major;
import com.yyh.util.MongoUtil;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class MajorDAOImpl implements IMajorDAO {

    @Override
    public List<DBObject> findAll(Integer pageNum, Integer pageSize, String deptName, String majorName) {
        //模糊查询
        Pattern majorPattern = Pattern.compile("^.*" + majorName + ".*$", Pattern.CASE_INSENSITIVE);
        DBObject dbObject = new BasicDBObject();
        if (deptName != null && !"".equals(deptName))
            dbObject.put("deptName", deptName);
        if (majorName != null && !"".equals(majorName))
            dbObject.put("majorName", majorPattern);
        DBCursor dbCursor = MongoUtil.getCollection("major").find(dbObject);
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
        return MongoUtil.getCollection("major").count();
    }

    @Override
    public Major findById(String id) {
        DBCollection dbCollection = MongoUtil.getCollection("major");
        DBObject dbObject = new QueryBuilder().put("_id").is(new ObjectId(id)).get();
        DBObject res = dbCollection.findOne(dbObject);
        if (res != null) return new Major(String.valueOf(res.get("_id"))
                , String.valueOf(res.get("deptName"))
                , String.valueOf(res.get("majorName")));
        else return null;
    }

    @Override
    public void insertMajor(Major major) {
        DBObject dbObject = new BasicDBObject();
        dbObject.put("deptName", major.getDeptName());
        dbObject.put("majorName", major.getMajorName());
        MongoUtil.getCollection("major").insert(dbObject);
    }

    @Override
    public void updateMajor(Major major) {
        DBCollection dbCollection = MongoUtil.getCollection("major");
        //旧记录
        DBObject oldObject = new BasicDBObject();
        oldObject.put("_id", new ObjectId(major.getId()));
        // 找到这一条记录   新纪录
        DBObject newObject = dbCollection.findOne(new ObjectId(major.getId()));
        if (major.getDeptName() != null)
            newObject.put("deptName", major.getDeptName());
        if (major.getMajorName() != null)
            newObject.put("majorName", major.getMajorName());
        dbCollection.update(oldObject, newObject);
    }

    @Override
    public void deleteById(String id) {
        DBCollection dbCollection = MongoUtil.getCollection("major");
        DBObject dbObject = new BasicDBObject();
        dbObject.put("_id", new ObjectId(id));
        dbCollection.remove(dbObject);
    }
}
