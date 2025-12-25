package com.jaymin.journalApp.journalRepo;

import com.jaymin.journalApp.entity.ConfigJournalApp;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

@Component
public interface ConfigJournalAppRepository extends MongoRepository <ConfigJournalApp, ObjectId>{
}
