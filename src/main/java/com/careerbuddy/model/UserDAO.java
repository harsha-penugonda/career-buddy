package com.careerbuddy.model;

import java.util.List;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "users")
public class UserDAO {
    @Id
    private ObjectId id;

    @Indexed(unique = true)
    private String email;

    private String password;
    private List<String> roles;
}
