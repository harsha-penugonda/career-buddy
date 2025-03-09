package com.careerbuddy.controller;

import com.careerbuddy.dto.InfoDTO;
import com.careerbuddy.interfaces.AdminServiceExternal;
import java.net.UnknownHostException;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/admin")
@Slf4j
@RequiredArgsConstructor
@Secured("ROLE_ADMIN")
@Tag(name = "Admin", description = "Admin API")
public class AdminController {

    private final AdminServiceExternal adminServiceExternal;

    @GetMapping("/info")
    public ResponseEntity<InfoDTO> getAdminInfo() throws UnknownHostException {
        return ResponseEntity.ok().body(adminServiceExternal.getAdminInfo());
    }
}
