package com.yyh.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yyh.dao.IDeptDAO;
import com.yyh.dao.impl.DeptDAOImpl;
import com.yyh.pojo.Dept;
import com.yyh.util.LayuiResponseUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@WebServlet("/dept")
public class DeptController extends HttpServlet {

    private final IDeptDAO deptDAO = new DeptDAOImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=UTF-8");
        String method = req.getParameter("method");
        if ("findAll".equals(method)) {
            findAll(req, resp);
        } else if ("insert".equals(method)) {
            insert(req, resp);
        } else if ("update".equals(method)) {
            update(req, resp);
        } else if ("delete".equals(method)) {
            delete(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }

    private void findAll(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int pageNum = -1;
        int pageSize = -1;
        String deptName = req.getParameter("searchParams[deptName]");
        if (req.getParameter("page") != null)
            pageNum = Integer.parseInt(req.getParameter("page"));
        if (req.getParameter("limit") != null)
            pageSize = Integer.parseInt(req.getParameter("limit"));
        if (req.getParameter("searchParams[deptName]") != null)
            deptName = req.getParameter("searchParams[deptName]");
        resp.getWriter().write(responseJSON(LayuiResponseUtil.data(deptDAO.getAllCount(), deptDAO.findAll(pageNum, pageSize, deptName))));
    }

    private void insert(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Dept dept = (Dept) parseJSON(req, Dept.class);
        String deptName = dept.getDeptName();
        Dept vo = new Dept(null, deptName);
        deptDAO.insertDept(vo);
    }

    private void update(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Dept dept = (Dept) parseJSON(req, Dept.class);
        String id = dept.getId();
        String deptName = dept.getDeptName();
        Dept vo = new Dept(id, deptName);
        deptDAO.updateDept(vo);
    }

    private void delete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Dept dept = (Dept) parseJSON(req, Dept.class);
        String id = dept.getId();
        deptDAO.deleteById(id);
    }

    //将JSON解析成Java对象
    private Object parseJSON(HttpServletRequest req, Class<?> clazz) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(req.getInputStream(), "UTF-8"));
        String line = null;
        StringBuilder sb = new StringBuilder();
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return mapper.readValue(sb.toString(), clazz);
    }

    //响应json字符串
    private String responseJSON(Object obj) {
        ObjectMapper mapper = new ObjectMapper();
        String json = null;
        try {
            json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return json;
    }
}
