package com.example.demo1.controller;

import com.example.demo1.entity.User;
import com.example.demo1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * <p>
 * Created by xiangjiangcheng on 2018/8/20 13:40.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;


    // @GetMapping
    // public HttpEntity getUsers() {
    //     List<User>  userList = userService.queryUserList();
    //
    //
    //     return new HttpEntity(userList, HttpStatus.OK);
    // }

    @GetMapping
    public Map<String, Object> getUsers() {
        List<User>  userList = userService.queryUserList();

        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("userList", userList);

        return returnMap;
    }
}