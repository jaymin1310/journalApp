package com.jaymin.journalApp.controller;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.jaymin.journalApp.entity.JournalEntry;
import com.jaymin.journalApp.entity.User;
import com.jaymin.journalApp.services.JournalEntryService;
import com.jaymin.journalApp.services.UserEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/journal")
public class JournalEntryControllerV2 {

    @Autowired
    JournalEntryService journalEntryService;

    @Autowired
    UserEntryService userService;

    @GetMapping
    public ResponseEntity<List<JournalEntry>> getAllJournalEntriesOfUser() {
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String userName=authentication.getName();
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
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String userName=authentication.getName();
        User user=userService.findByUser(userName);
        JournalEntry myEnt = user.getJournalEntries()
                .stream()
                .filter(x -> x.getId().equals(myId))
                .findFirst()
                .orElse(null);
        if(myEnt!=null) {
            return new ResponseEntity<>(myEnt, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry myEntry){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String userName=authentication.getName();
        try {
            myEntry.setDate(LocalDateTime.now());
            journalEntryService.saveEntry(myEntry,userName);
            return new ResponseEntity<>(myEntry, HttpStatus.CREATED);
        }catch(Exception e){
            return new  ResponseEntity<>(myEntry, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping("id/{myId}")
    public ResponseEntity<Void> deleteEntryById(@PathVariable ObjectId myId){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String userName=authentication.getName();
        User user=userService.findByUser(userName);
        boolean is_delete=journalEntryService.deleteEntry(myId,userName);
        if(is_delete)
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @PutMapping("id/{myId}")
    public ResponseEntity<JournalEntry> editEntry(@PathVariable ObjectId myId,@RequestBody JournalEntry newEntry){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String userName=authentication.getName();
        User user=userService.findByUser(userName);
        JournalEntry myEnt = user.getJournalEntries()
                .stream()
                .filter(x -> x.getId().equals(myId))
                .findFirst()
                .orElse(null);
        if(myEnt!=null) {
            myEnt.setContent((newEntry.getContent()!=null && !newEntry.getContent().isEmpty())?newEntry.getContent():myEnt.getContent());
            myEnt.setTitle(!newEntry.getTitle().isEmpty() ?newEntry.getTitle():myEnt.getTitle());
            journalEntryService.saveEntry(myEnt);
            return new  ResponseEntity<>(myEnt, HttpStatus.OK);
        }
        return new  ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}

