<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html>
<html>
<head>
    <base href="<%=basePath%>">
    <meta charset="utf-8">
    <title></title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/lib/layui-v2.5.5/css/layui.css" media="all">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/public.css" media="all">
    <style>
        body {
            background-color: #ffffff;
        }
    </style>
</head>
<body>
<div class="layui-form layuimini-form">
    <div class="layui-form-item">
        <label class="layui-form-label">所属学院</label>
        <div class="layui-input-block" lay-filter="deptSelector">
            <select name="deptName" class="" id="deptName">
                <option value="0">请选择学院</option>
            </select>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">专业信息</label>
        <div class="layui-input-block">
            <input type="text" id="majorName" name="majorName" placeholder="请输入专业名称" value="" class="layui-input">
        </div>
    </div>

    <div class="layui-form-item">
        <div class="layui-input-block">
            <button class="layui-btn layui-btn-normal" lay-submit lay-filter="saveBtn">确认保存</button>
        </div>
    </div>
</div>
<script src="${pageContext.request.contextPath}/static/lib/layui-v2.5.5/layui.js" charset="utf-8"></script>
<script>
    layui.use(['form'], function () {
        var form = layui.form,
            layer = layui.layer,
            $ = layui.$;

        //监听提交
        form.on('submit(saveBtn)', function (data) {
            var index1 = parent.layer.getFrameIndex(window.name);
            $.ajax({
                type: "post",
                url: "${pageContext.request.contextPath}/major?method=insert",
                contentType: 'application/json; charset=utf-8',
                data: JSON.stringify(data.field),
                dataType: "json",
                success: function (res) {

                }
            });
            setTimeout(function () {
                parent.layer.close(index1);
                top.layer.close(index);
                parent.location.reload();
            }, 500);
            return false;
        });
        $.ajax({
            url: '${pageContext.request.contextPath}/major?method=findAll',
            dataType: 'json',
            type: 'get',
            success: function (data) {
                $.each(data.data, function (index, item) {
                    $('#deptName').append(new Option(item.deptName, item.deptName));// 下拉菜单里添加元素
                });
                layui.form.render();
            }
        });
    });
</script>
</body>
</html>