package com.smemanager.service;

import com.smemanager.dto.SmeDto;
import com.smemanager.entity.Sme;
import com.smemanager.enums.AvailabilityStatus;
import com.smemanager.exception.ResourceNotFoundException;
import com.smemanager.repository.SmeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class SmeService {

    @Autowired
    private SmeRepository smeRepository;

    @Autowired
    private ActivityLogService logService;

    public SmeDto.Response createSme(SmeDto.Request req) {
        if (smeRepository.existsByEmail(req.getEmail())) {
            throw new IllegalArgumentException("Email already registered: " + req.getEmail());
        }
        Sme sme = new Sme();
        sme.setFullName(req.getFullName());
        sme.setEmail(req.getEmail());
        sme.setDepartment(req.getDepartment());
        sme.setDesignation(req.getDesignation());
        sme.setSpecialties(req.getSpecialties());
        sme.setYearsOfExperience(req.getYearsOfExperience());
        sme.setPhone(req.getPhone());
        sme.setTeamsId(req.getTeamsId());
        sme.setAvailabilityStatus(
                req.getAvailabilityStatus() != null ? req.getAvailabilityStatus() : AvailabilityStatus.AVAILABLE);
        sme.setBio(req.getBio());
        sme = smeRepository.save(sme);
        logService.log("SME_ADDED", "New SME added: " + sme.getFullName(), "Admin", sme.getFullName());
        return toResponse(sme);
    }

    public SmeDto.Response updateSme(Long id, SmeDto.Request req) {
        Sme sme = findEntityById(id);
        if (!sme.getEmail().equals(req.getEmail()) && smeRepository.existsByEmail(req.getEmail())) {
            throw new IllegalArgumentException("Email already registered: " + req.getEmail());
        }
        sme.setFullName(req.getFullName());
        sme.setEmail(req.getEmail());
        sme.setDepartment(req.getDepartment());
        sme.setDesignation(req.getDesignation());
        sme.setSpecialties(req.getSpecialties());
        sme.setYearsOfExperience(req.getYearsOfExperience());
        sme.setPhone(req.getPhone());
        sme.setTeamsId(req.getTeamsId());
        if (req.getAvailabilityStatus() != null) {
            sme.setAvailabilityStatus(req.getAvailabilityStatus());
        }
        sme.setBio(req.getBio());
        return toResponse(smeRepository.save(sme));
    }

    public void deleteSme(Long id) {
        Sme sme = findEntityById(id);
        logService.log("SME_REMOVED", "SME removed: " + sme.getFullName(), "Admin", sme.getFullName());
        smeRepository.delete(sme);
    }

    @Transactional(readOnly = true)
    public List<SmeDto.Response> getAllSmes() {
        List<Sme> smes = smeRepository.findAll();
        List<SmeDto.Response> result = new ArrayList<>();
        for (Sme sme : smes) {
            result.add(toResponse(sme));
        }
        return result;
    }

    @Transactional(readOnly = true)
    public SmeDto.Response getSmeById(Long id) {
        return toResponse(findEntityById(id));
    }

    @Transactional(readOnly = true)
    public List<SmeDto.Response> searchSmes(String name, String dept, AvailabilityStatus status) {
        List<Sme> smes = smeRepository.searchSmes(name, dept, status);
        List<SmeDto.Response> result = new ArrayList<>();
        for (Sme sme : smes) {
            result.add(toResponse(sme));
        }
        return result;
    }

    @Transactional(readOnly = true)
    public List<SmeDto.Summary> findAvailableBySpecialties(List<String> specialties) {
        List<Sme> smes = smeRepository.findAvailableBySpecialties(specialties);
        List<SmeDto.Summary> result = new ArrayList<>();
        for (Sme sme : smes) {
            result.add(toSummary(sme));
        }
        return result;
    }

    public void updateAvailabilityStatus(Long id, AvailabilityStatus status) {
        Sme sme = findEntityById(id);
        sme.setAvailabilityStatus(status);
        smeRepository.save(sme);
    }

    public Sme findEntityById(Long id) {
        return smeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("SME not found: " + id));
    }

    // ── Mappers ───────────────────────────────────────────────────────────────
    public SmeDto.Response toResponse(Sme s) {
        SmeDto.Response dto = new SmeDto.Response();
        dto.setId(s.getId());
        dto.setFullName(s.getFullName());
        dto.setEmail(s.getEmail());
        dto.setDepartment(s.getDepartment());
        dto.setDesignation(s.getDesignation());
        dto.setSpecialties(s.getSpecialties());
        dto.setYearsOfExperience(s.getYearsOfExperience());
        dto.setPhone(s.getPhone());
        dto.setTeamsId(s.getTeamsId());
        dto.setAvailabilityStatus(s.getAvailabilityStatus());
        dto.setBio(s.getBio());
        dto.setLastBooked(s.getLastBooked());
        dto.setCreatedAt(s.getCreatedAt());
        return dto;
    }

    public SmeDto.Summary toSummary(Sme s) {
        SmeDto.Summary dto = new SmeDto.Summary();
        dto.setId(s.getId());
        dto.setFullName(s.getFullName());
        dto.setEmail(s.getEmail());
        dto.setSpecialties(s.getSpecialties());
        dto.setDepartment(s.getDepartment());
        dto.setYearsOfExperience(s.getYearsOfExperience());
        dto.setAvailabilityStatus(s.getAvailabilityStatus());
        return dto;
    }
}
