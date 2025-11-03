package com.jaymin.journalApp.controller;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.jaymin.journalApp.entity.JournalEntry;
import com.jaymin.journalApp.entity.User;
import com.jaymin.journalApp.services.JournalEntryService;
import com.jaymin.journalApp.services.UserEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/journal")
public class JournalEntryControllerV2 {

    @Autowired
    JournalEntryService journalEntryService;

    @Autowired
    UserEntryService userService;

    @GetMapping("{userName}")
    public ResponseEntity<List<JournalEntry>> getAllJournalEntriesOfUser(@PathVariable String userName) {
        User user=userService.findByUser(userName);
        List<JournalEntry> all = user.getJournalEntries();
        if (all.isEmpty()) {
            return ResponseEntity.noContent().build(); // 204 No Content
        } else {
            return ResponseEntity.ok(all); // 200 OK
        }
    }
    @GetMapping("id/{myId}")
    public ResponseEntity<JournalEntry> entryById(@PathVariable ObjectId myId) {
        Optional<JournalEntry>myEnt=journalEntryService.entryById(myId);
        if(myEnt.isPresent()){
            return new ResponseEntity<>(myEnt.get(),HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("{userName}")
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry myEntry,@PathVariable String userName){
        try {
            myEntry.setDate(LocalDateTime.now());
            journalEntryService.saveEntry(myEntry,userName);
            return new ResponseEntity<>(myEntry, HttpStatus.CREATED);
        }catch(Exception e){
            return new  ResponseEntity<>(myEntry, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping("id/{userName}/{myId}")
    public ResponseEntity<Void> deleteEntryById(@PathVariable ObjectId myId,@PathVariable String userName){
        Optional<JournalEntry>myEnt=journalEntryService.entryById(myId);
        if(myEnt.isPresent()) {
            journalEntryService.deleteEntry(myId,userName);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @PutMapping("id/{userName}/{myId}")
    public ResponseEntity<JournalEntry> editEntry(@PathVariable ObjectId myId,@RequestBody JournalEntry newEntry,@PathVariable String userName){
        JournalEntry myEnt = journalEntryService.entryById(myId).orElse(null);
        if(myEnt!=null) {
            myEnt.setContent((newEntry.getContent()!=null && !newEntry.getContent().isEmpty())?newEntry.getContent():myEnt.getContent());
            myEnt.setTitle((newEntry.getTitle()!=null && !newEntry.getTitle().isEmpty())?newEntry.getTitle():myEnt.getTitle());
            journalEntryService.saveEntry(myEnt);
            return new  ResponseEntity<>(myEnt, HttpStatus.OK);
        }
        return new  ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


}

