package com.careerbuddy.utils.transformers;

import com.careerbuddy.bo.UserBO;
import com.careerbuddy.domain.UserDAO;
import com.careerbuddy.dto.UserDTO;

public class UserTransformer {

    public static UserBO toUserBO(UserDTO userDTO) {
        return UserBO.builder()
                .username(userDTO.getUsername())
                .password(userDTO.getPassword())
                .roles(userDTO.getRoles())
                .build();
    }

    public static UserDTO toUserDTO(UserBO userBO) {
        return UserDTO.builder()
                .username(userBO.getUsername())
                .password(userBO.getPassword())
                .roles(userBO.getRoles())
                .build();
    }

    public static UserDAO toUserDAO(UserBO userBO) {
        UserDAO userDAO = new UserDAO();
        userDAO.setEmail(userBO.getUsername());
        userDAO.setPassword(userBO.getPassword());
        userDAO.setRoles(userBO.getRoles());
        return userDAO;
    }

    public static UserBO toUserBO(UserDAO userDAO) {
        return UserBO.builder()
                .username(userDAO.getEmail())
                .password(userDAO.getPassword())
                .roles(userDAO.getRoles())
                .build();
    }
}
