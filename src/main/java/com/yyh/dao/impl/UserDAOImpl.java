package com.yyh.dao.impl;

import com.mongodb.*;
import com.yyh.dao.IUserDAO;
import com.yyh.pojo.User;
import com.yyh.util.MongoUtil;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class UserDAOImpl implements IUserDAO {
    @Override
    public List<DBObject> findAll(Integer pageNum, Integer pageSize, String username) {
        //模糊查询
        Pattern usernamePattern = Pattern.compile("^.*" + username + ".*$", Pattern.CASE_INSENSITIVE);
        DBObject dbObject = new BasicDBObject();
        if (username != null && !"".equals(username))
            dbObject.put("username", usernamePattern);
        DBCursor dbCursor = MongoUtil.getCollection("user").find(dbObject);
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
        return MongoUtil.getCollection("user").count();
    }

    @Override
    public void insertUser(User user) {
        DBObject dbObject = new BasicDBObject();
        dbObject.put("username", user.getUsername());
        dbObject.put("password", user.getPassword());
        MongoUtil.getCollection("user").insert(dbObject);
    }

    @Override
    public void updateUser(User user) {
        DBCollection dbCollection = MongoUtil.getCollection("user");
        //旧记录
        DBObject oldObject = new BasicDBObject();
        oldObject.put("_id", new ObjectId(user.getId()));
        // 找到这一条记录   新纪录
        DBObject newObject = dbCollection.findOne(new ObjectId(user.getId()));
        if (user.getUsername() != null) {
            newObject.put("username", user.getUsername());
        }
        if (user.getPassword() != null) {
            newObject.put("password", user.getPassword());
        }
        dbCollection.update(oldObject, newObject);
    }

    @Override
    public void deleteById(String id) {
        DBCollection dbCollection = MongoUtil.getCollection("user");
        DBObject dbObject = new BasicDBObject();
        dbObject.put("_id", new ObjectId(id));
        dbCollection.remove(dbObject);
    }

    @Override
    public User findByUsername(String username) {
        DBCollection dbCollection = MongoUtil.getCollection("user");
        DBObject dbObject = new QueryBuilder().put("username").is(username).get();
        DBObject res = dbCollection.findOne(dbObject);
        if (res != null) return new User(String.valueOf(res.get("_id")), (String) res.get("username"), (String) res.get("password"));
        else return null;
    }

    @Override
    public User findById(String id) {
        DBCollection dbCollection = MongoUtil.getCollection("user");
        DBObject dbObject = new QueryBuilder().put("_id").is(new ObjectId(id)).get();
        DBObject res = dbCollection.findOne(dbObject);
        if (res != null) return new User(String.valueOf(res.get("_id"))
                , String.valueOf(res.get("username"))
                , String.valueOf(res.get("password")));
        else return null;
    }
}
