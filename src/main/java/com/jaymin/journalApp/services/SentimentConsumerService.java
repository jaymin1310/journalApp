package com.jaymin.journalApp.services;

import com.jaymin.journalApp.model.SentimentData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class SentimentConsumerService {
    @Autowired
    private EmailService emailService;

    @KafkaListener(topics="weekly-sentiment",groupId="weekly-sentiment-groupV2")
    public void consume(SentimentData sentimentData) {
        sendEmail(sentimentData);
    }
    private void sendEmail(SentimentData sentimentData) {
        emailService.sendEmail(sentimentData.getEmail(), "sentiment for previous week", sentimentData.getSentiment());
    }
}
