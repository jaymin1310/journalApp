//package com.jaymin.journalApp.controller;
//
//import java.util.HashMap;
//import java.util.Map;
//import java.util.ArrayList;
//import java.util.List;
//import com.jaymin.journalApp.entity.JournalEntry;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/_journal")
//public class JournalEntryController {
//    private Map<Long, JournalEntry> journalEntries=new HashMap<>();
//    @GetMapping
//    public List<JournalEntry>getAll(){
//        return new ArrayList<>(journalEntries.values());
//    }
//    @GetMapping("id/{myId}")
//    public JournalEntry entryById(@PathVariable long myId){
//        return journalEntries.get(myId);
//    }
//    @PostMapping
//    public boolean createEntry(@RequestBody JournalEntry myEntry){
//        journalEntries.put(myEntry.getId(), myEntry);
//        return true;
//    }
//    @DeleteMapping("id/{myId}")
//    public JournalEntry deleteEntryById(@PathVariable long myId){
//        return journalEntries.remove(myId);
//    }
//    @PutMapping("id/{myId}")
//    public JournalEntry editEntry(@PathVariable long myId,@RequestBody JournalEntry myEntry){
//        return journalEntries.put(myId,myEntry);
//    }
//
//
//}
//
