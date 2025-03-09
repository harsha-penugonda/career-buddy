package com.careerbuddy.controller;

import com.careerbuddy.dto.AuthResponse;
import com.careerbuddy.dto.UserDTO;
import com.careerbuddy.interfaces.AuthServiceExternal;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Authorization", description = "Authorization API")
public class AuthController {

    private final AuthServiceExternal authServiceExternal;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody UserDTO userDTO) {
        authServiceExternal.createUser(userDTO);
        return new ResponseEntity<>(authServiceExternal.authenticate(userDTO), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody UserDTO userDTO) {
        return new ResponseEntity<>(authServiceExternal.authenticate(userDTO), HttpStatus.OK);
    }
}
