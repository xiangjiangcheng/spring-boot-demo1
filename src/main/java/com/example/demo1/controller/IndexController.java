package com.example.demo1.controller;

import com.example.demo1.model.Person;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

/**
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * <p>
 * Created by xiangjiangcheng on 2018/8/21 10:32.
 */
@Controller
@RequestMapping("/")
public class IndexController {

    @GetMapping("/home")
    public String home() {

        return "forward:/home.html";
    }

    @GetMapping("/login")
    public String login(Model model, @RequestParam(value = "error", required = false) String error) {
        if (error != null) {
            model.addAttribute("error", "用户名或密码错误");
        }
        return "login_page";
    }


    @RequestMapping(value = "/greeting")
    public ModelAndView test(ModelAndView mv) {
        Person single = new Person("aa", 11);
        List<Person> people = new ArrayList<>();
        Person p1 = new Person("zhangsan12", 11);
        Person p2 = new Person("lisi", 22);
        Person p3 = new Person("wangwu2222222313", 33);
        people.add(p1);
        people.add(p2);
        people.add(p3);
        mv.addObject("singlePerson", single);
        mv.addObject("people", people);
        mv.setViewName("/index");
        mv.addObject("title","欢迎使用Thymeleaf!");
        return mv;
    }

}