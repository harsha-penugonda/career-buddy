package com.careerbuddy.model;

import java.util.List;

import lombok.Builder;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document(collection = "users")
public class UserDAO {
    @Id
    private ObjectId id;

    @Indexed(unique = true)
    private String userEmail;

    private String password;
    private String firstName;
    private String lastName;
    private String phone;
    private String dateOfBirth;
    private List<String> roles;
}
