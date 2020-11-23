package com.yyh.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yyh.dao.IUserDAO;
import com.yyh.dao.impl.UserDAOImpl;
import com.yyh.pojo.User;
import com.yyh.util.LayuiResponseUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@WebServlet("/user")
public class UserController extends HttpServlet {

    private final IUserDAO userDAO = new UserDAOImpl();

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
        } else if ("login".equals(method)) {
            login(req, resp);
        } else if ("logout".equals(method)) {
            logout(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }

    private void findAll(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Integer pageNum = -1;
        Integer pageSize = -1;
        String username = "";
        if (req.getParameter("page") != null)
            pageNum = Integer.parseInt(req.getParameter("page"));
        if (req.getParameter("limit") != null)
            pageSize = Integer.parseInt(req.getParameter("limit"));
        if (req.getParameter("searchParams[username]") != null)
            username = req.getParameter("searchParams[username]");
        resp.getWriter().write(responseJSON(LayuiResponseUtil.data(userDAO.getAllCount(), userDAO.findAll(pageNum, pageSize, username))));
    }

    private void insert(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        User user = (User) parseJSON(req, User.class);
        String username = user.getUsername();
        String password = user.getPassword();
        User vo = new User(null, username, password);
        userDAO.insertUser(vo);
    }

    private void update(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        User user = (User) parseJSON(req, User.class);
        String id = user.getId();
        String username = user.getUsername();
        String password = user.getPassword();
        User vo = new User(id, username, password);
        userDAO.updateUser(vo);
    }

    private void delete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        User user = (User) parseJSON(req, User.class);
        String id = user.getId();
        userDAO.deleteById(id);
    }

    private void login(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        User user = (User) parseJSON(req, User.class);
        String username = user.getUsername();
        String password = user.getPassword();

        User vo = userDAO.findByUsername(username);

        if (vo != null) {
            if (password.equals(vo.getPassword())) {
                req.getSession().setAttribute("user", vo);
                System.out.println("登录成功");
                resp.getWriter().write("loginSuccess");
            } else {
                System.out.println("密码错误");
                resp.getWriter().write("passwordError");
            }
        } else {
            System.out.println("用户不存在");
            resp.getWriter().write("userNotFound");
        }
    }

    private void logout(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.getSession().invalidate();
        resp.sendRedirect("page?forward=login");
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
