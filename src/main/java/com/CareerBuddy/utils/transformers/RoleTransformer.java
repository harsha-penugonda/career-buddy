package com.CareerBuddy.utils.transformers;

import com.CareerBuddy.bo.RoleBO;
import com.CareerBuddy.domain.Role;
import com.CareerBuddy.dto.RoleDTO;

public class RoleTransformer {

    public static RoleBO transformToBO(RoleDTO roleDTO) {
        return RoleBO.builder()
                .username(roleDTO.getUsername())
                .password(roleDTO.getPassword())
                .role(roleDTO.getRole())
                .build();
    }

    public static Role transformToDAO(RoleBO roleBO) {
        return Role.builder()
                .username(roleBO.getUsername())
                .password(roleBO.getPassword())
                .role(roleBO.getRole())
                .build();
    }

    public static RoleBO transformToBO(Role role) {
        return RoleBO.builder()
                .username(role.getUsername())
                .password(role.getPassword())
                .role(role.getRole())
                .build();
    }

    public static RoleDTO transformToDTO(RoleBO roleBO) {
        return RoleDTO.builder()
                .username(roleBO.getUsername())
                .password(roleBO.getPassword())
                .role(roleBO.getRole())
                .build();
    }
}
