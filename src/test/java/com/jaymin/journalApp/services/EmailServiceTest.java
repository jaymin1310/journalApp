package com.jaymin.journalApp.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EmailServiceTest {
    @Autowired
    private EmailService emailService;
    @Test
    void testSendEmail() {
        emailService.sendEmail("jvsolanki2005@gmail.com","Testing java mail sender","Hi,this is java mail sender test are you ok?");
    }
}
