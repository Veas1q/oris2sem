package ru.itis.dis403.lab_01.controller;

import ru.itis.dis403.lab_01.annotation.Controller;
import ru.itis.dis403.lab_01.annotation.GetMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@Controller
public class AdminController {

    @GetMapping("/admin")
    public void admin(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.getWriter().write("ADMIN PAGE");
    }
}