package com.jaymin.journalApp.services;

import com.jaymin.journalApp.entity.JournalEntry;
import com.jaymin.journalApp.entity.User;
import com.jaymin.journalApp.journalRepo.JournalRepository;
import com.jaymin.journalApp.journalRepo.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.lang.reflect.Array;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class UserEntryService {
    @Autowired
    private UserRepository userRepo;
    //instead we use slf4j lobok->private static final Logger logger= LoggerFactory.getLogger(UserEntryService.class);
    private static final PasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
    public void saveEntry(User userEntry){
        userRepo.save(userEntry);
    }
    public boolean saveNewUser(User userEntry) {
        try{
            if(userRepo.findByUserName(userEntry.getUserName())!=null)throw new Exception("Username already exists");
            userEntry.setPassword(passwordEncoder.encode(userEntry.getPassword()));
            userEntry.getRole().add("USER");
            userRepo.save(userEntry);
            return true;
        }catch(Exception e){
            log.error("error for {}",userEntry.getUserName(),e);
            return false;
        }
    }
    public void saveExistingUser(User userEntry) {
        userEntry.setPassword(passwordEncoder.encode(userEntry.getPassword()));
        userRepo.save(userEntry);
    }
    public void saveNewAdmin(User userEntry){
        userEntry.setPassword(passwordEncoder.encode(userEntry.getPassword()));
        userEntry.getRole().add("USER");
        userEntry.getRole().add("ADMIN");
        userRepo.save(userEntry);
    }
    public List<User> getAll(){
        return userRepo.findAll();
    }
    public Optional<User> entryById(ObjectId id) {
        return userRepo.findById(id);
    }
    public void deleteEntry(String userName){
    userRepo.deleteByUserName(userName);
    }
    public User findByUser(String userName){
        return userRepo.findByUserName(userName);
    }
}
