package com.yyh.controller;

import com.yyh.dao.*;
import com.yyh.dao.impl.*;
import com.yyh.pojo.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/page")
public class PageController extends HttpServlet {

    private IClazzDAO clazzDAO = new ClazzDAOImpl();
    private IMajorDAO majorDAO = new MajorDAOImpl();
    private IDeptDAO deptDAO = new DeptDAOImpl();
    private IStudentDAO studentDAO = new StudentDAOImpl();
    private ICourseDAO courseDAO = new CourseDAOImpl();
    private IScoreDAO scoreDAO = new ScoreDAOImpl();
    private IUserDAO userDAO = new UserDAOImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=UTF-8");
        String forward = req.getParameter("forward");
        if ("index".equals(forward)) {
            index(req, resp);
        } else if ("login".equals(forward)) {
            login(req, resp);
        } else if ("welcome".equals(forward)) {
            welcome(req, resp);
        } else if("clazz".equals(forward)) {
            clazz(req, resp);
        } else if("addClazz".equals(forward)) {
            addClazz(req, resp);
        } else if("editClazz".equals(forward)) {
            editClazz(req, resp);
        } else if("course".equals(forward)) {
            course(req, resp);
        } else if("addCourse".equals(forward)) {
            addCourse(req, resp);
        } else if("editCourse".equals(forward)) {
            editCourse(req, resp);
        } else if("dept".equals(forward)) {
            dept(req, resp);
        } else if("addDept".equals(forward)) {
            addDept(req, resp);
        } else if("editDept".equals(forward)) {
            editDept(req, resp);
        } else if("major".equals(forward)) {
            major(req, resp);
        } else if("addMajor".equals(forward)) {
            addMajor(req, resp);
        } else if("editMajor".equals(forward)) {
            editMajor(req, resp);
        } else if("score".equals(forward)) {
            score(req, resp);
        } else if("addScore".equals(forward)) {
            addScore(req, resp);
        } else if("editScore".equals(forward)) {
            editScore(req, resp);
        } else if("student".equals(forward)) {
            student(req, resp);
        } else if("addStudent".equals(forward)) {
            addStudent(req, resp);
        } else if("editStudent".equals(forward)) {
            editStudent(req, resp);
        } else if("user".equals(forward)) {
            user(req, resp);
        } else if("addUser".equals(forward)) {
            addUser(req, resp);
        } else if("editUser".equals(forward)) {
            editUser(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }

    //主页
    private void index(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(req.getContextPath() + "WEB-INF/pages/index/index.jsp").forward(req, resp);
    }

    //登录页面
    private void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(req.getContextPath() + "WEB-INF/pages/login/login.jsp").forward(req, resp);
    }

    //欢迎页面
    private void welcome(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(req.getContextPath() + "WEB-INF/pages/index/welcome.jsp").forward(req, resp);
    }

    //班级页面
    private void clazz(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(req.getContextPath() + "WEB-INF/pages/clazz/clazz.jsp").forward(req, resp);
    }

    private void addClazz(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(req.getContextPath() + "WEB-INF/pages/clazz/add.jsp").forward(req, resp);
    }

    private void editClazz(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        Clazz clazz = clazzDAO.findById(id);
        req.getSession().setAttribute("clazz", clazz);
        req.getRequestDispatcher(req.getContextPath() + "WEB-INF/pages/clazz/edit.jsp").forward(req, resp);
    }

    //课程页面
    private void course(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(req.getContextPath() + "WEB-INF/pages/course/course.jsp").forward(req, resp);
    }

    private void addCourse(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(req.getContextPath() + "WEB-INF/pages/course/add.jsp").forward(req, resp);
    }

    private void editCourse(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        Course course = courseDAO.findById(id);
        req.getSession().setAttribute("course", course);
        req.getRequestDispatcher(req.getContextPath() + "WEB-INF/pages/course/edit.jsp").forward(req, resp);
    }

    //学院页面
    private void dept(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(req.getContextPath() + "WEB-INF/pages/dept/dept.jsp").forward(req, resp);
    }

    private void addDept(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(req.getContextPath() + "WEB-INF/pages/dept/add.jsp").forward(req, resp);
    }

    private void editDept(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        Dept dept = deptDAO.findById(id);
        req.getSession().setAttribute("dept", dept);
        req.getRequestDispatcher(req.getContextPath() + "WEB-INF/pages/dept/edit.jsp").forward(req, resp);
    }

    //专业页面
    private void major(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(req.getContextPath() + "WEB-INF/pages/major/major.jsp").forward(req, resp);
    }

    private void addMajor(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(req.getContextPath() + "WEB-INF/pages/major/add.jsp").forward(req, resp);
    }

    private void editMajor(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        Major major = majorDAO.findById(id);
        req.getSession().setAttribute("major", major);
        req.getRequestDispatcher(req.getContextPath() + "WEB-INF/pages/major/edit.jsp").forward(req, resp);
    }

    //成绩页面
    private void score(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(req.getContextPath() + "WEB-INF/pages/score/score.jsp").forward(req, resp);
    }

    private void addScore(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(req.getContextPath() + "WEB-INF/pages/score/add.jsp").forward(req, resp);
    }

    private void editScore(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        Score score = scoreDAO.findById(id);
        req.getSession().setAttribute("score", score);
        req.getRequestDispatcher(req.getContextPath() + "WEB-INF/pages/score/edit.jsp").forward(req, resp);
    }

    //学生页面
    private void student(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(req.getContextPath() + "WEB-INF/pages/student/student.jsp").forward(req, resp);
    }

    private void addStudent(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(req.getContextPath() + "WEB-INF/pages/student/add.jsp").forward(req, resp);
    }

    private void editStudent(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        Student student = studentDAO.findById(id);
        req.getSession().setAttribute("student", student);
        req.getRequestDispatcher(req.getContextPath() + "WEB-INF/pages/student/edit.jsp").forward(req, resp);
    }

    //用户页面
    private void user(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(req.getContextPath() + "WEB-INF/pages/user/user.jsp").forward(req, resp);
    }

    private void addUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(req.getContextPath() + "WEB-INF/pages/user/add.jsp").forward(req, resp);
    }

    private void editUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        User user = userDAO.findById(id);
        req.getSession().setAttribute("user", user);
        req.getRequestDispatcher(req.getContextPath() + "WEB-INF/pages/user/edit.jsp").forward(req, resp);
    }
}
