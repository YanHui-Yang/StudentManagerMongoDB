package com.yyh.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yyh.dao.IStudentDAO;
import com.yyh.dao.impl.StudentDAOImpl;
import com.yyh.pojo.Student;
import com.yyh.util.LayuiResponseUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@WebServlet("/student")
public class StudentController extends HttpServlet {

    private final IStudentDAO studentDAO = new StudentDAOImpl();

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
        Integer pageNum = -1;
        Integer pageSize = -1;
        String deptName = req.getParameter("searchParams[deptName]");
        String majorName = req.getParameter("searchParams[majorName]");
        String clazzName = req.getParameter("searchParams[clazzName]");
        String stuNumber = req.getParameter("searchParams[stuNumber]");
        String stuName = req.getParameter("searchParams[stuName]");
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
        if (req.getParameter("searchParams[stuNumber]") != null)
            stuNumber = req.getParameter("searchParams[stuNumber]");
        if (req.getParameter("searchParams[stuName]") != null)
            stuName = req.getParameter("searchParams[stuName]");
        resp.getWriter().write(responseJSON(LayuiResponseUtil.data(studentDAO.getAllCount(), studentDAO.findAll(pageNum, pageSize, deptName, majorName, clazzName, stuNumber, stuName))));
    }

    private void insert(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Student student = (Student) parseJSON(req, Student.class);
        String stuNumber = student.getStuNumber();
        String stuName = student.getStuName();
        String deptName = student.getDeptName();
        String majorName = student.getMajorName();
        String clazzName = student.getClazzName();
        Student vo = new Student(null, stuNumber, stuName, deptName, majorName, clazzName);
        studentDAO.insertStudent(vo);
    }

    private void update(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Student student = (Student) parseJSON(req, Student.class);
        String id = student.getId();
        String stuNumber = student.getStuNumber();
        String stuName = student.getStuName();
        String deptName = student.getDeptName();
        String majorName = student.getMajorName();
        String clazzName = student.getClazzName();
        Student vo = new Student(id, stuNumber, stuName, deptName, majorName, clazzName);
        studentDAO.updateStudent(vo);
    }

    private void delete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Student student = (Student) parseJSON(req, Student.class);
        String id = student.getId();
        studentDAO.deleteById(id);
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
