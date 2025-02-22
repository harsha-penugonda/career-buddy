package com.CareerBuddy.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@Builder
@Document(collection = "Role")
public class Role {
    @Id
    private String id;

    private String username;
    private String password;
    private String role;
}
