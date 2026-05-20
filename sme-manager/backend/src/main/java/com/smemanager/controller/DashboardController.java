package com.smemanager.controller;

import com.smemanager.dto.DashboardStatsDto;
import com.smemanager.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @GetMapping("/admin/dashboard")
    public ResponseEntity<DashboardStatsDto> adminDashboard() {
        return ResponseEntity.ok(dashboardService.getAdminStats());
    }

    @GetMapping("/poc/dashboard")
    public ResponseEntity<DashboardStatsDto> pocDashboard(
            @AuthenticationPrincipal UserDetails ud) {
        return ResponseEntity.ok(dashboardService.getPocStats(ud.getUsername()));
    }
}
