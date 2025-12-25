package com.jaymin.journalApp.Repository;

import com.jaymin.journalApp.journalRepo.UserRepositoryImpl;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserRepositoryImplTests {
    @Autowired
    private UserRepositoryImpl userRepositoryImpl;

    @Test
    void testfindUser() {
        userRepositoryImpl.getUserBySA();
    }
}
