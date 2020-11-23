package com.yyh.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yyh.dao.IClazzDAO;
import com.yyh.dao.impl.ClazzDAOImpl;
import com.yyh.pojo.Clazz;
import com.yyh.util.LayuiResponseUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@WebServlet("/clazz")
public class ClazzController extends HttpServlet {

    private final IClazzDAO clazzDAO = new ClazzDAOImpl();

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
        } else if ("findById".equals(method)) {
            findById(req, resp);
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
        String majorName = req.getParameter("searchParams[majorName]");
        String clazzName = req.getParameter("searchParams[clazzName]");
        if (req.getParameter("page") != null)
            pageNum = Integer.parseInt(req.getParameter("page"));
        if (req.getParameter("limit") != null)
            pageSize = Integer.parseInt(req.getParameter("limit"));
        if (req.getParameter("searchParams[deptName]") != null)
            deptName = req.getParameter("searchParams[deptName]");
        if (req.getParameter("searchParams[majorName]") != null)
            majorName = req.getParameter("searchParams[majorName]");
        if (req.getParameter("searchParams[clazzName]") != null)
            clazzName = req.getParameter("searchParams[clazzName]");
        resp.getWriter().write(responseJSON(LayuiResponseUtil.data(clazzDAO.getAllCount(), clazzDAO.findAll(pageNum, pageSize, deptName, majorName, clazzName))));
    }

    private void findById(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Clazz clazz = (Clazz) parseJSON(req, Clazz.class);
        String id = clazz.getId();
        resp.getWriter().write(responseJSON(LayuiResponseUtil.data(0L, clazzDAO.findById(id))));
    }

    private void insert(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Clazz clazz = (Clazz) parseJSON(req, Clazz.class);
        System.out.println(clazz);
        String deptName = clazz.getDeptName();
        String majorName = clazz.getMajorName();
        String clazzName = clazz.getClazzName();
        Clazz vo = new Clazz(null, deptName, majorName, clazzName);
        System.out.println(vo);
        clazzDAO.insertClazz(vo);
    }

    private void update(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Clazz clazz = (Clazz) parseJSON(req, Clazz.class);
        String id = clazz.getId();
        String deptName = clazz.getDeptName();
        String majorName = clazz.getMajorName();
        String clazzName = clazz.getClazzName();
        Clazz vo = new Clazz(id, deptName, majorName, clazzName);
        clazzDAO.updateClazz(vo);
    }

    private void delete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Clazz clazz = (Clazz) parseJSON(req, Clazz.class);
        String id = clazz.getId();
        clazzDAO.deleteById(id);
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
