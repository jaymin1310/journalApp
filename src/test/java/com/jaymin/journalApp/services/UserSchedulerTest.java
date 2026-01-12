package com.jaymin.journalApp.services;

import com.jaymin.journalApp.scheduler.UserScheduler;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest

public class UserSchedulerTest {
    @Autowired
    UserScheduler userScheduler;
    @Test
    void userSaEmailSendingTest(){
        userScheduler.UserSaEmailSending();
    }
}
