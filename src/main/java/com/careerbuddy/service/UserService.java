package com.careerbuddy.service;

import com.careerbuddy.bo.UserBO;
import com.careerbuddy.domain.UserDAO;
import com.careerbuddy.dto.AuthResponse;
import com.careerbuddy.dto.UserDTO;
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
public class UserService {

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

    public void createUser(UserDTO UserDTO) {
        if (UserDTO == null) {
            log.info("UserDTO is null");
            return;
        }
        UserBO userBO = UserTransformer.toUserBO(UserDTO);
        // check if user already exists
        if (userRepository.findByEmail(userBO.getUsername()).isPresent()) {
            log.info("User already exists: {}", userBO.getUsername());
            throw new RuntimeException("User already exists");
        }
        userBO.setRoles(List.of("ROLE_USER"));
        userBO.setPassword(passwordEncoder.encode(userBO.getPassword()));
        userRepository.save(UserTransformer.toUserDAO(userBO));
        log.info("New user created: {}", userBO.getUsername());
    }

    public AuthResponse authenticate(UserDTO userDTO) {
        log.info("Authenticating user: {}", userDTO.getUsername());
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userDTO.getUsername(), userDTO.getPassword()));

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String token = jwtTokenUtil.generateToken(userDetails);

        return AuthResponse.builder().token(token).build();
    }
}
