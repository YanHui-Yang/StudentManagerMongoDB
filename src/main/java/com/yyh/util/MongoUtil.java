package com.yyh.util;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;

public class MongoUtil {

    private static final String HOST = "127.0.0.1";
    private static final Integer PORT = 27017;
    private static final String DB_NAME = "student_manager";
    private static MongoClient mongo;
    public static DB db;

    static {
        mongo = new MongoClient(HOST, PORT);
        db = mongo.getDB(DB_NAME);
    }

    public static DBCollection getCollection(String name) {
        return db.getCollection(name);
    }
}
