package com.example.t2303e.controllers;

import com.example.t2303e.dao.ClassDAO;
import com.example.t2303e.dao.StudentDAO;
import com.example.t2303e.entity.Classroom;
import com.example.t2303e.entity.Student;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(value = "/create-student")
public class CreateStudentController extends HttpServlet {
    private StudentDAO _studentDAO;
    private ClassDAO _classDAO;

    @Override
    public void init() throws ServletException {
        _studentDAO = new StudentDAO();
        _classDAO = new ClassDAO();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Classroom> classes = _classDAO.all();
        req.setAttribute("classes",classes);
        RequestDispatcher dispatcher = req.getRequestDispatcher("student/create.jsp");
        dispatcher.forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Student s = new Student(
                null,
                req.getParameter("name"),
                req.getParameter("email"),
                req.getParameter("address"),
                req.getParameter("telephone"),
                Integer.parseInt(req.getParameter("class_id"))
        );
        if(_studentDAO.create(s)){
            resp.sendRedirect("student");
            return;
        }
        resp.sendRedirect("create-student");
    }
}
