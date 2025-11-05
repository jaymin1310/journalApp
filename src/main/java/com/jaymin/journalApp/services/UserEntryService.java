package com.jaymin.journalApp.services;

import com.jaymin.journalApp.entity.JournalEntry;
import com.jaymin.journalApp.entity.User;
import com.jaymin.journalApp.journalRepo.JournalRepository;
import com.jaymin.journalApp.journalRepo.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserEntryService {
    @Autowired
    private UserRepository userRepo;

    private static final PasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
    public void saveEntry(User userEntry){
        userRepo.save(userEntry);
    }
    public void saveNewUser(User userEntry){
        userEntry.setPassword(passwordEncoder.encode(userEntry.getPassword()));
        userRepo.save(userEntry);
    }
   /* public List<User> getAll(){
        return userRepo.findAll();
    }*/
    public Optional<User> entryById(ObjectId id) {
        return userRepo.findById(id);
    }
//    public void deleteEntry(ObjectId id){
//        userRepo.deleteById(id);
//    }
    public void deleteEntry(String userName){
    userRepo.deleteByUserName(userName);
    }
    public User findByUser(String userName){
        return userRepo.findByUserName(userName);
    }
}
