package com.jaymin.journalApp.controller;

import com.jaymin.journalApp.entity.User;
import com.jaymin.journalApp.services.UserEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicController {
    @GetMapping("/health")
    public String healthCheck(){
        return "ok";
    }
    @Autowired
    private UserEntryService userService;
    @PostMapping("/create-user")
    public void createUser(@RequestBody User user){
        userService.saveNewUser(user);
    }

}
