package ru.skypro.lessons.springboot.test.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/info")

public class InfoController {
    @Value("${app.env}")
    private String app;

    @GetMapping("/appInfo")
public String returnApp(){
return app;}
}
