<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html>
<html lang="en">

<head>
    <base href="<%=basePath%>">
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/lib/layui-v2.5.5/css/layui.css" media="all">
    <style>
        body {
            background-image: linear-gradient(to top, #fbc2eb 0%, #a6c1ee 100%);
        }

        .blo {
            box-shadow: 0 5px 15px -5px rgba(0,0,0,.5);
        }

        .mycard {
            transition: box-shadow .25s;
        }

        .mycard:hover {
            cursor: pointer;
            box-shadow: 0 5px 15px -5px rgba(0,0,0,.5);
            transform: scale(1.03);
        }
    </style>
</head>

<body>
<div class="layui-container">
    <blockquote class="layui-elem-quote blo" style="margin-top: 10px;">项目名称：学生管理系统</blockquote>
    <div class="layui-row layui-col-space10">
        <div class="layui-col-md4">
            <div class="layui-card mycard">
                <div class="layui-card-header"><b>前端技术</b></div><hr>
                <div class="layui-card-body">
                    - Layui Mini<br>
                    - JSP<br>
                    - JQuery<br>
                </div>
            </div>
        </div>
        <div class="layui-col-md4">
            <div class="layui-card mycard">
                <div class="layui-card-header"><b>后端技术</b></div><hr>
                <div class="layui-card-body">
                    - Servlet<br>
                    - Maven<br>
                    - Tomcat 9<br>
                </div>
            </div>
        </div>
        <div class="layui-col-md4">
            <div class="layui-card mycard">
                <div class="layui-card-header"><b>数据库</b></div><hr>
                <div class="layui-card-body">
                    - MongoDB<br><br><br>
                </div>
            </div>
        </div>
    </div>

    <pre class="layui-code" id="myCode">
package com.yyh.util;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;

public class MongoUtil {

    private static final String HOST = "127.0.0.1";
    private static final Integer PORT = 27017;
    private static final String DB_NAME = "student_manage";
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
        </pre>
</div>


<script src="${pageContext.request.contextPath}/static/lib/layui-v2.5.5/layui.js" charset="utf-8"></script>
<script>
    layui.use('code', function () { //加载code模块
        layui.code({
            elem: "#myCode",
            title: "连接MongoDB代码",
            about: false,
            skin: "notepad"
        }); //引用code方法
    });
</script>
</body>

</html>

