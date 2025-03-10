package com.careerbuddy.utils.transformers;

import com.careerbuddy.bo.UserBO;
import com.careerbuddy.dto.UserDTO;
import com.careerbuddy.model.UserDAO;

public class UserTransformer {

    public static UserBO toUserBO(UserDTO userDTO) {
        return UserBO.builder()
                .userEmail(userDTO.getUserEmail())
                .password(userDTO.getPassword())
                .build();
    }

    public static UserDTO toUserDTO(UserBO userBO) {
        return UserDTO.builder()
                .userEmail(userBO.getUserEmail())
                .password(userBO.getPassword())
                .build();
    }

    public static UserDAO toUserDAO(UserBO userBO) {
        return UserDAO.builder()
                .userEmail(userBO.getUserEmail())
                .password(userBO.getPassword())
                .roles(userBO.getRoles())
                .build();
    }

    public static UserBO toUserBO(UserDAO userDAO) {
        return UserBO.builder()
                .userEmail(userDAO.getUserEmail())
                .password(userDAO.getPassword())
                .roles(userDAO.getRoles())
                .build();
    }
}
