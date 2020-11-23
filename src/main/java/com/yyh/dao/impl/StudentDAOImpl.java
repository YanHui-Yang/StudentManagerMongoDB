package com.yyh.dao.impl;

import com.mongodb.*;
import com.yyh.dao.IStudentDAO;
import com.yyh.pojo.Student;
import com.yyh.util.MongoUtil;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class StudentDAOImpl implements IStudentDAO {

    @Override
    public List<DBObject> findAll(Integer pageNum, Integer pageSize, String deptName, String majorName, String clazzName, String stuNumber, String stuName) {
        //模糊查询stuName
        Pattern stuNamePattern = Pattern.compile("^.*" + stuName + ".*$", Pattern.CASE_INSENSITIVE);
        //模糊查询stuNumber
        Pattern stuNumberPattern = Pattern.compile("^.*" + stuNumber + ".*$", Pattern.CASE_INSENSITIVE);
        DBObject dbObject = new BasicDBObject();
        if (deptName != null && !"".equals(deptName))
            dbObject.put("deptName", deptName);
        if (majorName != null && !"".equals(majorName))
            dbObject.put("majorName", majorName);
        if (clazzName != null && !"".equals(clazzName))
            dbObject.put("clazzName", clazzName);
        if (stuName != null && !"".equals(stuName))
            dbObject.put("stuName", stuNamePattern);
        if (stuNumber != null && !"".equals(stuNumber))
            dbObject.put("stuNumber", stuNumberPattern);
        DBCursor dbCursor = MongoUtil.getCollection("student").find(dbObject);
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
        return MongoUtil.getCollection("student").count();
    }

    @Override
    public Student findById(String id) {
        DBCollection dbCollection = MongoUtil.getCollection("student");
        DBObject dbObject = new QueryBuilder().put("_id").is(new ObjectId(id)).get();
        DBObject res = dbCollection.findOne(dbObject);
        if (res != null) return new Student(String.valueOf(res.get("_id"))
                , String.valueOf(res.get("stuNumber"))
                , String.valueOf(res.get("stuName"))
                , String.valueOf(res.get("deptName"))
                , String.valueOf(res.get("majorName"))
                , String.valueOf(res.get("clazzName")));
        else return null;
    }

    @Override
    public void insertStudent(Student student) {
        DBObject dbObject = new BasicDBObject();
        dbObject.put("stuNumber", student.getStuNumber());
        dbObject.put("stuName", student.getStuName());
        dbObject.put("deptName", student.getDeptName());
        dbObject.put("majorName", student.getMajorName());
        dbObject.put("clazzName", student.getClazzName());
        MongoUtil.getCollection("student").insert(dbObject);
    }

    @Override
    public void updateStudent(Student student) {
        DBCollection dbCollection = MongoUtil.getCollection("student");
        //旧记录
        DBObject oldObject = new BasicDBObject();
        oldObject.put("_id", new ObjectId(student.getId()));
        // 找到这一条记录   新纪录
        DBObject newObject = dbCollection.findOne(new ObjectId(student.getId()));
        if (student.getStuNumber() != null)
            newObject.put("stuNumber", student.getStuNumber());
        if (student.getStuName() != null)
            newObject.put("stuName", student.getStuName());
        if (student.getDeptName() != null)
            newObject.put("deptName", student.getDeptName());
        if (student.getMajorName() != null)
            newObject.put("major", student.getMajorName());
        if (student.getClazzName() != null)
            newObject.put("clazzName", student.getClazzName());
        dbCollection.update(oldObject, newObject);
    }

    @Override
    public void deleteById(String id) {
        DBCollection dbCollection = MongoUtil.getCollection("student");
        DBObject dbObject = new BasicDBObject();
        dbObject.put("_id", new ObjectId(id));
        dbCollection.remove(dbObject);
    }
}
