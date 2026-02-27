package ru.itis.dis403.lab_01.controller;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.itis.dis403.lab_01.annotation.Controller;
import ru.itis.dis403.lab_01.annotation.GetMapping;

import java.io.IOException;

@Controller
public class HomeController {

    public HomeController(){}

    @GetMapping("/home")
    public void home(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.getWriter().write("HOME PAGE");
    }

    @GetMapping("/hello")
    public void hello(HttpServletRequest req, HttpServletResponse resp) throws IOException{
        resp.getWriter().write("HELLO PAGE");
    }
}
