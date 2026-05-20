package com.smemanager.controller;

import com.smemanager.dto.SmeDto;
import com.smemanager.enums.AvailabilityStatus;
import com.smemanager.service.SmeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/smes")
public class SmeController {

    @Autowired
    private SmeService smeService;

    @GetMapping
    public ResponseEntity<List<SmeDto.Response>> getAllSmes(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String dept,
            @RequestParam(required = false) AvailabilityStatus status) {
        if (name != null || dept != null || status != null) {
            return ResponseEntity.ok(smeService.searchSmes(name, dept, status));
        }
        return ResponseEntity.ok(smeService.getAllSmes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SmeDto.Response> getSme(@PathVariable Long id) {
        return ResponseEntity.ok(smeService.getSmeById(id));
    }

    @PostMapping
    public ResponseEntity<SmeDto.Response> createSme(@RequestBody SmeDto.Request req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(smeService.createSme(req));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SmeDto.Response> updateSme(@PathVariable Long id,
                                                      @RequestBody SmeDto.Request req) {
        return ResponseEntity.ok(smeService.updateSme(id, req));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSme(@PathVariable Long id) {
        smeService.deleteSme(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/availability")
    public ResponseEntity<Void> updateAvailability(@PathVariable Long id,
                                                    @RequestBody Map<String, String> body) {
        smeService.updateAvailabilityStatus(id, AvailabilityStatus.valueOf(body.get("status")));
        return ResponseEntity.ok().build();
    }

    @GetMapping("/available")
    public ResponseEntity<List<SmeDto.Summary>> getAvailableBySpecialties(
            @RequestParam List<String> specialties) {
        return ResponseEntity.ok(smeService.findAvailableBySpecialties(specialties));
    }
}
