package com.jaymin.journalApp.controller;

import com.jaymin.journalApp.cache.AppCache;
import com.jaymin.journalApp.entity.User;
import com.jaymin.journalApp.services.UserEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.apache.logging.log4j.util.LambdaUtil.getAll;
@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    AppCache appCache;
    @Autowired
    private UserEntryService userEntryService;

    @GetMapping("all-users")
    public ResponseEntity<?> getAllUser(){
        List<User>all=userEntryService.getAll();
        if(all!=null && !all.isEmpty()){
            return new ResponseEntity<>(all,HttpStatus.OK );
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @PostMapping("add-admin")
    public void createUser(@RequestBody User user){
        userEntryService.saveNewAdmin(user);
    }
    @DeleteMapping("delete-user/{userName}")
    public ResponseEntity<?> DeleteUser(@PathVariable String userName){
        User user=userEntryService.findByUser(userName);
        if(user!=null){
            userEntryService.deleteEntry(userName);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @GetMapping("clear-cache")
    public void clearCache(){
        appCache.init();
    }
}
