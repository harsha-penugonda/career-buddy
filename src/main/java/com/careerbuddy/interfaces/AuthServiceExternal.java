package com.careerbuddy.interfaces;

import com.careerbuddy.dto.AuthResponse;
import com.careerbuddy.dto.UserDTO;

public interface AuthServiceExternal {
    void createUser(UserDTO UserDTO);

    AuthResponse authenticate(UserDTO userDTO);
}
