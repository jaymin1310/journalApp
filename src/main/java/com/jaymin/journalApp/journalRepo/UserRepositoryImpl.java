package com.jaymin.journalApp.journalRepo;

import com.jaymin.journalApp.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

public class UserRepositoryImpl {
    @Autowired
    private MongoTemplate mongoTemplate;
    public List<User> getUserBySA(){
        Query query = new Query();
//        query.addCriteria(Criteria.where("email").exists(true));
//        query.addCriteria(Criteria.where("email").ne(null).ne(""));
        query.addCriteria(Criteria.where("email").regex("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", "i"));
        query.addCriteria(Criteria.where("role").in("USER","ADMIN"));
        query.addCriteria(Criteria.where("sentimentAnaly").is(true));
        //->using or and and operator
//        Criteria criteria = new Criteria();
//        query.addCriteria(criteria.andOperator(
//                Criteria.where("email").exists(true),
//                Criteria.where("sentimentAnaly").is(true))
//        );
        List<User> users=mongoTemplate.find(query, User.class);
        return users;
    }
}
