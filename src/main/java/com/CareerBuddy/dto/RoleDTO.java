package com.CareerBuddy.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class RoleDTO {
    private String username;
    private String password;
    private String role;
}
