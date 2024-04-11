package com.haitao.controller;


import com.haitao.pojo.User;
import com.haitao.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/findById/{id}")
    public User findById(@PathVariable("id")Integer id){
        return  userService.findById(id);
    }

}
