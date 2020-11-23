package com.yyh.dao.impl;

import com.mongodb.*;
import com.yyh.dao.ICourseDAO;
import com.yyh.pojo.Course;
import com.yyh.util.MongoUtil;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class CourseDAOImpl implements ICourseDAO {

    @Override
    public List<DBObject> findAll(Integer pageNum, Integer pageSize, String courseName) {
        //模糊查询
        Pattern courseNamePattern = Pattern.compile("^.*" + courseName + ".*$", Pattern.CASE_INSENSITIVE);
        DBObject dbObject = new BasicDBObject();
        if (courseName != null && !"".equals(courseName))
            dbObject.put("courseName", courseNamePattern);
        DBCursor dbCursor = MongoUtil.getCollection("course").find(dbObject);
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
        return MongoUtil.getCollection("course").count();
    }

    @Override
    public Course findById(String id) {
        DBCollection dbCollection = MongoUtil.getCollection("course");
        DBObject dbObject = new QueryBuilder().put("_id").is(new ObjectId(id)).get();
        DBObject res = dbCollection.findOne(dbObject);
        if (res != null) return new Course(String.valueOf(res.get("_id"))
                , String.valueOf(res.get("courseName"))
                , Double.parseDouble(String.valueOf(res.get("courseHour"))));
        else return null;
    }

    @Override
    public void insertCourse(Course course) {
        DBObject dbObject = new BasicDBObject();
        dbObject.put("courseName", course.getCourseName());
        dbObject.put("courseHour", course.getCourseHour());
        MongoUtil.getCollection("course").insert(dbObject);
    }

    @Override
    public void updateCourse(Course course) {
        DBCollection dbCollection = MongoUtil.getCollection("course");
        //旧记录
        DBObject oldObject = new BasicDBObject();
        oldObject.put("_id", new ObjectId(course.getId()));
        // 找到这一条记录   新纪录
        DBObject newObject = dbCollection.findOne(new ObjectId(course.getId()));
        if (course.getCourseName() != null)
            newObject.put("courseName", course.getCourseName());
        if (course.getCourseHour() != null)
            newObject.put("courseHour", course.getCourseHour());
        dbCollection.update(oldObject, newObject);
    }

    @Override
    public void deleteById(String id) {
        DBCollection dbCollection = MongoUtil.getCollection("course");
        DBObject dbObject = new BasicDBObject();
        dbObject.put("_id", new ObjectId(id));
        dbCollection.remove(dbObject);
    }
}
