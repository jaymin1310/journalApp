package com.jaymin.journalApp.services;

import com.jaymin.journalApp.entity.User;
import com.jaymin.journalApp.journalRepo.UserRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserEntryServiceTests {
    @Autowired
    UserEntryService userService;
    @Autowired
    UserRepository userRepo;
//    @ParameterizedTest
////    @ValueSource(strings ={ "ram", "jaymin1310", "temp" })
//    @ArgumentsSource(UserArgumentsProvider.class)
//    public void testSaveUser(User user){
//        assertTrue(userService.saveNewUser(user));
//    }
    @ParameterizedTest
    @CsvSource({
            "1,1,2",
            "2,3,5",
            "2,2,4"
    })
    public void testParam(int a,int b,int exp){
        assertEquals(exp,a+b);
    }
//  Other important annotations
//    @BeforeAll-run after all tests methods run
//    @BeforeEach
//    @AfterAll
//    @AfterEach
}
