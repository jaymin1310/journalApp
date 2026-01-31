package com.jaymin.journalApp.controller;

import com.jaymin.journalApp.entity.User;
import com.jaymin.journalApp.services.UserDetailServiceImpl;
import com.jaymin.journalApp.services.UserEntryService;
import com.jaymin.journalApp.util.Jwtutil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
@Slf4j
public class PublicController {
    @GetMapping("/health")
    public String healthCheck(){
        return "ok";
    }
    @Autowired
    private UserEntryService userService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserDetailServiceImpl userDetailService;
    @Autowired
    private Jwtutil jwtutil;
    @PostMapping("/signup")
    public ResponseEntity<?>  signup(@RequestBody User user){
        if(userService.saveNewUser(user)){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }
    @PostMapping("/login")
    public ResponseEntity<String>  login(@RequestBody User user){
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUserName(),user.getPassword()));
            UserDetails userDetails=userDetailService.loadUserByUsername(user.getUserName());
            String jwt=jwtutil.generateToken(userDetails.getUsername());
            return new ResponseEntity<>(jwt, HttpStatus.OK);
        }catch(Exception e){
            e.printStackTrace();
            log.error("Exception occurred while createAuthenticationToken",e);
            return new ResponseEntity<>("Incorrect username or password",HttpStatus.BAD_REQUEST);
        }
    }

}
