package com.careerbuddy.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class RoleBO {
    private String username;
    private String password;
    private String role;
}
