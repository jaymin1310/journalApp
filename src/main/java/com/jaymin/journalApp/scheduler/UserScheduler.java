package com.jaymin.journalApp.scheduler;

import com.jaymin.journalApp.cache.AppCache;
import com.jaymin.journalApp.entity.JournalEntry;
import com.jaymin.journalApp.entity.User;
import com.jaymin.journalApp.journalRepo.UserRepository;
import com.jaymin.journalApp.journalRepo.UserRepositoryImpl;
import com.jaymin.journalApp.services.EmailService;
import com.jaymin.journalApp.services.SentimentAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Component
public class UserScheduler {
    @Autowired
    EmailService emailService;
    @Autowired
    SentimentAnalysisService sentimentAnalysisService;
    @Autowired
    AppCache appCache;

    @Autowired
    private UserRepositoryImpl userRepository;
    @Scheduled(cron = "0 0 22 * * SUN")
    //@Scheduled(cron = "0 * * * * *")//permin
    public void fetchUserForSaEmailSending(){
        List<User> users=userRepository.getUserBySA();
        for(User user:users){
            List<JournalEntry> journalEntries=user.getJournalEntries();
            List<String>filterEntries=journalEntries.stream().filter(x->x.getDate().isAfter(LocalDateTime.now().minus(7, ChronoUnit.DAYS))).map(x->x.getContent()).collect(Collectors.toList());
            String entry=String.join(",",filterEntries);
            String sentiment=sentimentAnalysisService.getsentiment(entry);
            emailService.sendEmail(user.getEmail(),"sentiment of last 7 days",sentiment);

        }
    }
    @Scheduled(cron = "0 */5 * * * *")
    public void clearCache(){
        appCache.init();
    }
}
