package com.careerbuddy.service;

import com.careerbuddy.bo.UserBO;
import com.careerbuddy.dto.AuthResponse;
import com.careerbuddy.dto.UserDTO;
import com.careerbuddy.interfaces.AuthServiceExternal;
import com.careerbuddy.model.UserDAO;
import com.careerbuddy.repository.UserRepository;
import com.careerbuddy.security.JwtTokenUtil;
import com.careerbuddy.utils.transformers.UserTransformer;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService implements AuthServiceExternal {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;

    public UserDTO getUserInfo(String id) {
        if (StringUtils.isBlank(id)) {
            return null;
        }
        log.info("id: {}", id);
        ObjectId objectId = new ObjectId(id);
        UserDAO userDAO = userRepository.findById(objectId).orElse(null);
        if (userDAO == null) {
            log.info("userDAO not found for id: {}", id);
            return null;
        }
        return UserTransformer.toUserDTO(UserTransformer.toUserBO(userDAO));
    }

    @Override
    public void createUser(UserDTO UserDTO) {
        if (UserDTO == null
                || StringUtils.isBlank(UserDTO.getUserEmail())
                || StringUtils.isBlank(UserDTO.getPassword())) {
            log.info("UserDTO is null");
            return;
        }
        // check if userEmail is an valid email
        if (!UserDTO.getUserEmail().matches("^(.+)@(.+)$")) {
            log.info("userEmail is not an email, userEmail: {}", UserDTO.getUserEmail());
            throw new RuntimeException("userEmail is not an valid email");
        }
        // check if password meets standard requirements
        if (!UserDTO.getPassword().matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^\\da-zA-Z]).{8,}$")) {
            log.info("Password does not meet standard requirements");
            throw new RuntimeException("Password does not meet standard requirements, Make sure it has at least 8 characters, 1 uppercase letter, 1 lowercase letter, 1 number, and 1 special character");
        }

        UserBO userBO = UserTransformer.toUserBO(UserDTO);
        // check if user already exists
        if (userRepository.findByUserEmail(userBO.getUserEmail()).isPresent()) {
            log.info("User already exists: {}", userBO.getUserEmail());
            throw new RuntimeException("User already exists");
        }
        userBO.setRoles(List.of("ROLE_USER"));
        userBO.setPassword(passwordEncoder.encode(userBO.getPassword()));
        userRepository.save(UserTransformer.toUserDAO(userBO));
        log.info("New user created: {}", userBO.getUserEmail());
    }

    @Override
    public AuthResponse authenticate(UserDTO userDTO) {
        log.info("Authenticating user: {}", userDTO.getUserEmail());
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userDTO.getUserEmail(), userDTO.getPassword()));

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String token = jwtTokenUtil.generateToken(userDetails);

        return AuthResponse.builder().token(token).build();
    }

    public static String getKey(String s) {
        return s.substring(0, s.indexOf(":"));
    }

    public static String getValue(String s) {
        return s.substring(s.indexOf(":") + 1);
    }
}
