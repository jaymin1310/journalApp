package com.jaymin.journalApp.services;

import com.jaymin.journalApp.entity.User;
import com.jaymin.journalApp.journalRepo.UserRepository;
import com.mongodb.assertions.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import static org.mockito.Mockito.*;
public class UserDetailServiceImplTest {
    @InjectMocks
    private UserDetailServiceImpl serviceImpl;

    @Mock
    UserRepository userRepo;
    @BeforeEach
    void setup(){
        MockitoAnnotations.initMocks(this);
    }
    @Test
    void loadUserByUsernameTest(){
        when(userRepo.findByUserName(ArgumentMatchers.anyString())).thenReturn(User.builder().userName("singham").password("enncnd").build());
        UserDetails user=  serviceImpl.loadUserByUsername("singham");
        Assertions.assertNotNull(user);
    }
}
