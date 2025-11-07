package com.jaymin.journalApp.services;

import com.jaymin.journalApp.entity.JournalEntry;
import com.jaymin.journalApp.entity.User;
import com.jaymin.journalApp.journalRepo.JournalRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryService {
    @Autowired
    private JournalRepository journalRepo;
    @Autowired
    private UserEntryService userService;
    @Transactional
    public void saveEntry(JournalEntry journalEntry,String userName){
        try {
            User user = userService.findByUser(userName);
            JournalEntry saved = journalRepo.save(journalEntry);
            user.getJournalEntries().add(saved);
            userService.saveEntry(user);
        }catch(Exception e){
            System.out.println(e);
            throw new RuntimeException("Error Occuring while Saving");
        }
    }
    public void saveEntry(JournalEntry journalEntry){
        journalRepo.save(journalEntry);
    }
    public List<JournalEntry> getAll(){
        return journalRepo.findAll();
    }
    public Optional<JournalEntry> entryById(ObjectId id) {
        return journalRepo.findById(id);
    }
    @Transactional
    public boolean deleteEntry(ObjectId id,String userName){
        try{
            User user=userService.findByUser(userName);
            boolean removed= user.getJournalEntries().removeIf(x->x.getId().equals(id));
            if(removed) {
                userService.saveEntry(user);
                journalRepo.deleteById(id);
                return true;
            }
            return false;
        }catch(Exception e){
            System.out.println(e);
            throw new RuntimeException("Error Occuring while Saving");
        }
    }
}
