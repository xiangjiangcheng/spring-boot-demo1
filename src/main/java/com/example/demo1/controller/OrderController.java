package com.example.demo1.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * <p>
 * Created by xiangjiangcheng on 2018/8/21 10:25.
 */
@RestController
@RequestMapping("/orders")
public class OrderController {

    @GetMapping("/findAll")
    public String findAll() {
        return "findAll";
    }

    @GetMapping("/home")
    public String home() {
        return "forward:/home.html";
    }

}
