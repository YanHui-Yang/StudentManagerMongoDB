package com.yyh.dao.impl;

import com.mongodb.*;
import com.yyh.dao.IDeptDAO;
import com.yyh.pojo.Dept;
import com.yyh.util.MongoUtil;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class DeptDAOImpl implements IDeptDAO {

    @Override
    public List<DBObject> findAll(Integer pageNum, Integer pageSize, String deptName) {
        //模糊查询
        Pattern deptPattern = Pattern.compile("^.*" + deptName + ".*$", Pattern.CASE_INSENSITIVE);
        DBObject dbObject = new BasicDBObject();
        if (deptName != null && !"".equals(deptName))
            dbObject.put("deptName", deptPattern);
        DBCursor dbCursor = MongoUtil.getCollection("dept").find(dbObject);
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
        return MongoUtil.getCollection("dept").count();
    }

    @Override
    public Dept findById(String id) {
        DBCollection dbCollection = MongoUtil.getCollection("dept");
        DBObject dbObject = new QueryBuilder().put("_id").is(new ObjectId(id)).get();
        DBObject res = dbCollection.findOne(dbObject);
        if (res != null) return new Dept(String.valueOf(res.get("_id"))
                , String.valueOf(res.get("deptName")));
        else return null;
    }

    @Override
    public void insertDept(Dept dept) {
        DBObject dbObject = new BasicDBObject();
        dbObject.put("deptName", dept.getDeptName());
        MongoUtil.getCollection("dept").insert(dbObject);
    }

    @Override
    public void updateDept(Dept dept) {
        DBCollection dbCollection = MongoUtil.getCollection("dept");
        //旧记录
        DBObject oldObject = new BasicDBObject();
        oldObject.put("_id", new ObjectId(dept.getId()));
        // 找到这一条记录   新纪录
        DBObject newObject = dbCollection.findOne(new ObjectId(dept.getId()));
        if (dept.getDeptName() != null)
            newObject.put("deptName", dept.getDeptName());
        dbCollection.update(oldObject, newObject);
    }

    @Override
    public void deleteById(String id) {
        DBCollection dbCollection = MongoUtil.getCollection("dept");
        DBObject dbObject = new BasicDBObject();
        dbObject.put("_id", new ObjectId(id));
        dbCollection.remove(dbObject);
    }
}
