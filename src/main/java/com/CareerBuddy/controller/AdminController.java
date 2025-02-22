package com.CareerBuddy.controller;

import com.CareerBuddy.service.AdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/admin")
@Slf4j
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/info")
    public String getAdminInfo() {
        return "Admin Info";
    }

    @PostMapping("/info")
    public String postAdminInfo() {
        return adminService.postAdminInfo();
    }
}
