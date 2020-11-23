package com.yyh.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yyh.dao.IScoreDAO;
import com.yyh.dao.impl.ScoreDAOImpl;
import com.yyh.pojo.Score;
import com.yyh.util.LayuiResponseUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@WebServlet("/score")
public class ScoreController extends HttpServlet {

    private final IScoreDAO scoreDAO = new ScoreDAOImpl();

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
        String stuName = "";
        String courseName = "";
        if (req.getParameter("page") != null)
            pageNum = Integer.parseInt(req.getParameter("page"));
        if (req.getParameter("limit") != null)
            pageSize = Integer.parseInt(req.getParameter("limit"));
        if (req.getParameter("searchParams[courseName]") != null)
            courseName = req.getParameter("searchParams[courseName]");
        if (req.getParameter("searchParams[stuName]") != null)
            stuName = req.getParameter("searchParams[stuName]");
        resp.getWriter().write(responseJSON(LayuiResponseUtil.data(scoreDAO.getAllCount(), scoreDAO.findAll(pageNum, pageSize, courseName, stuName))));
    }

    private void insert(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Score score = (Score) parseJSON(req, Score.class);
        String stuName = score.getStuName();
        String courseName = score.getCourseName();
        Double score1 = score.getScore();
        Score vo = new Score(null, stuName, courseName, score1);
        scoreDAO.insertScore(vo);
    }

    private void update(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Score score = (Score) parseJSON(req, Score.class);
        String id = score.getId();
        String stuName = score.getStuName();
        String courseName = score.getCourseName();
        Double score1 = score.getScore();
        Score vo = new Score(id, stuName, courseName, score1);
        scoreDAO.updateScore(vo);
    }

    private void delete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Score score = (Score) parseJSON(req, Score.class);
        String id = score.getId();
        scoreDAO.deleteById(id);
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
