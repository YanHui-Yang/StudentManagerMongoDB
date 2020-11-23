package com.yyh.dao.impl;

import com.mongodb.*;
import com.yyh.dao.IClazzDAO;
import com.yyh.pojo.Clazz;
import com.yyh.util.MongoUtil;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class ClazzDAOImpl implements IClazzDAO {

    @Override
    public List<DBObject> findAll(Integer pageNum, Integer pageSize, String deptName, String majorName, String clazzName) {
        //模糊查询
        Pattern clazzPattern = Pattern.compile("^.*" + clazzName + ".*$", Pattern.CASE_INSENSITIVE);
        DBObject dbObject = new BasicDBObject();
        if (deptName != null && !"".equals(deptName))
            dbObject.put("deptName", deptName);
        if (majorName != null && !"".equals(majorName))
            dbObject.put("majorName", majorName);
        if (clazzName != null && !"".equals(clazzName))
            dbObject.put("clazzName", clazzPattern);
        DBCursor dbCursor = MongoUtil.getCollection("clazz").find(dbObject);
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
        return MongoUtil.getCollection("clazz").count();
    }

    @Override
    public Clazz findById(String id) {
        DBCollection dbCollection = MongoUtil.getCollection("clazz");
        // QueryBuilder().is()中is的参数如果是_id，需要传入new ObjectId(id)
        DBObject dbObject = new QueryBuilder().put("_id").is(new ObjectId(id)).get();
        DBObject res = dbCollection.findOne(dbObject);
        if (res != null) return new Clazz(String.valueOf(res.get("_id"))
                , String.valueOf(res.get("deptName"))
                , String.valueOf(res.get("majorName"))
                , String.valueOf(res.get("clazzName")));
        else return null;
    }

    @Override
    public void insertClazz(Clazz clazz) {
        DBObject dbObject = new BasicDBObject();
        dbObject.put("deptName", clazz.getDeptName());
        dbObject.put("majorName", clazz.getMajorName());
        dbObject.put("clazzName", clazz.getClazzName());
        MongoUtil.getCollection("clazz").insert(dbObject);
    }

    @Override
    public void updateClazz(Clazz clazz) {
        DBCollection dbCollection = MongoUtil.getCollection("clazz");
        //旧记录
        DBObject oldObject = new BasicDBObject();
        oldObject.put("_id", new ObjectId(clazz.getId()));
        // 找到这一条记录   新纪录
        DBObject newObject = dbCollection.findOne(new ObjectId(clazz.getId()));
        if (clazz.getDeptName() != null)
            newObject.put("deptName", clazz.getDeptName());
        if (clazz.getMajorName() != null)
            newObject.put("majorName", clazz.getMajorName());
        if (clazz.getClazzName() != null)
            newObject.put("clazzName", clazz.getClazzName());
        dbCollection.update(oldObject, newObject);
    }

    @Override
    public void deleteById(String id) {
        DBCollection dbCollection = MongoUtil.getCollection("clazz");
        DBObject dbObject = new BasicDBObject();
        dbObject.put("_id", new ObjectId(id));
        dbCollection.remove(dbObject);
    }
}
