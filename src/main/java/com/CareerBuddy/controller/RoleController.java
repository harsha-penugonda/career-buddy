package com.CareerBuddy.controller;

import com.CareerBuddy.dto.RoleDTO;
import com.CareerBuddy.service.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/role")
@Slf4j
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public RoleDTO getRoleInfo(@RequestParam String id) {
        return roleService.getRoleInfo(id);
    }

    @PostMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public RoleDTO createRole(@RequestBody RoleDTO roleDTO) {
        return roleService.createRole(roleDTO);
    }
}
