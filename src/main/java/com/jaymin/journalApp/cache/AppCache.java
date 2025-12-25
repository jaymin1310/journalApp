package com.jaymin.journalApp.cache;

import com.jaymin.journalApp.entity.ConfigJournalApp;
import com.jaymin.journalApp.journalRepo.ConfigJournalAppRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class AppCache{
    public enum keys{
        WEATHER_API,
    }
    @Autowired
    private ConfigJournalAppRepository configJournalAppRepository;
    public Map<String,String>appCache;
    @PostConstruct
    public void init(){
        appCache=new HashMap<>();
        List<ConfigJournalApp> all= configJournalAppRepository.findAll();
        for(ConfigJournalApp configJournalApp:all) {
            appCache.put(configJournalApp.getKey(), configJournalApp.getValue());
        }
    }
}
