package com.careerbuddy.controller;

import com.careerbuddy.dto.InfoDTO;
import com.careerbuddy.interfaces.AdminServiceExternal;
import java.net.UnknownHostException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/admin")
@Slf4j
@RequiredArgsConstructor
public class AdminController {

    private final AdminServiceExternal adminServiceExternal;

    @GetMapping("/info")
    public ResponseEntity<InfoDTO> getAdminInfo() throws UnknownHostException {
        return ResponseEntity.ok().body(adminServiceExternal.getAdminInfo());
    }
}
