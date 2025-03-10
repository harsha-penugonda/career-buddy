package com.careerbuddy.repository;

import com.careerbuddy.model.UserDAO;
import java.util.Optional;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<UserDAO, ObjectId> {
    Optional<UserDAO> findByUserEmail(String email);
}
