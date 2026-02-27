package ru.itis.dis403.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;

@Controller
public class HelloController {
    @GetMapping("/hello")
    public void hello(HttpServletResponse response) throws IOException {
        response.getWriter().write("<h1> hello </h1>");
    }
}
