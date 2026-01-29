package com.jaymin.journalApp.scheduler;

import com.jaymin.journalApp.cache.AppCache;
import com.jaymin.journalApp.entity.JournalEntry;
import com.jaymin.journalApp.entity.User;
import com.jaymin.journalApp.enums.Sentiment;
import com.jaymin.journalApp.journalRepo.UserRepository;
import com.jaymin.journalApp.journalRepo.UserRepositoryImpl;
import com.jaymin.journalApp.model.SentimentData;
import com.jaymin.journalApp.services.EmailService;
import com.jaymin.journalApp.services.SentimentAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    private KafkaTemplate<String, SentimentData>kafkaTemplate;

    @Autowired
    private UserRepositoryImpl userRepository;

    // @Scheduled(cron = "0 0 22 * * SUN")//Every sunday 10pm
   // @Scheduled(cron = "0 * * * * *")//permin
    public void UserSaEmailSending(){
        List<User> users=userRepository.getUserBySA();
        for(User user:users){
            List<JournalEntry> journalEntries=user.getJournalEntries();
            if (journalEntries == null || journalEntries.isEmpty()) {
                continue;
            }
            List<Sentiment> sentiments =
                    journalEntries.stream()
                            .map(x -> x.getSentimentAnalysis())
                            .collect(Collectors.toList());

            Map<Sentiment,Integer> sentimentCounts=new HashMap<>();
            for(Sentiment sentiment:sentiments) {
                if (sentiment != null) {
                    sentimentCounts.put(sentiment, sentimentCounts.getOrDefault(sentiment, 0) + 1);
                }
            }
            Sentiment mostFrequentSentiment = null;
            int maxCount = 0;
            for (Map.Entry<Sentiment, Integer> entry : sentimentCounts.entrySet()) {
                if (entry.getValue() > maxCount) {
                    maxCount = entry.getValue();
                    mostFrequentSentiment = entry.getKey();
                }
            }
            if (mostFrequentSentiment != null) {
                SentimentData sentimentData= SentimentData.builder().email(user.getEmail()).sentiment("Sentiment for last 7 days "+ mostFrequentSentiment).build();
                kafkaTemplate.send("weekly-sentiment",sentimentData.getEmail(),sentimentData);
            }
        }
    }
    @Scheduled(cron = "0 */5 * * * *")
    public void clearCache(){
        appCache.init();
    }
}
