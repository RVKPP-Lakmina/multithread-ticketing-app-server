package com.example.ticketManager.repository;

import com.example.ticketing.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    // Additional query methods can be added here if needed
}