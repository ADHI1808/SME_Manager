package com.smemanager.service;

import com.smemanager.dto.CohortRequestDto;
import com.smemanager.dto.SmeDto;
import com.smemanager.dto.SmeResponseDto;
import com.smemanager.entity.CohortRequest;
import com.smemanager.entity.Sme;
import com.smemanager.entity.SmeResponse;
import com.smemanager.entity.User;
import com.smemanager.enums.AvailabilityStatus;
import com.smemanager.enums.RequestStatus;
import com.smemanager.enums.SmeResponseStatus;
import com.smemanager.exception.ResourceNotFoundException;
import com.smemanager.repository.CohortRequestRepository;
import com.smemanager.repository.SmeRepository;
import com.smemanager.repository.SmeResponseRepository;
import com.smemanager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class CohortRequestService {

    @Autowired
    private CohortRequestRepository cohortRepo;

    @Autowired
    private SmeRepository smeRepo;

    @Autowired
    private SmeResponseRepository responseRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private SmeService smeService;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private ActivityLogService logService;

    // ── POC creates a request ─────────────────────────────────────────────────
    public CohortRequestDto.Response createRequest(CohortRequestDto.CreateRequest req, String pocEmail) {
        User poc = userRepo.findByEmail(pocEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + pocEmail));

        String cohortId = generateCohortId();

        List<Sme> proposed = new ArrayList<>();
        for (Long smeId : req.getProposedSmeIds()) {
            proposed.add(smeService.findEntityById(smeId));
        }

        CohortRequest cohort = new CohortRequest();
        cohort.setCohortId(cohortId);
        cohort.setEvaluationType(req.getEvaluationType());
        cohort.setRequiredSpecialties(req.getRequiredSpecialties());
        cohort.setSmesRequired(req.getSmesRequired());
        cohort.setParticipantCount(req.getParticipantCount());
        cohort.setPreferredDateFrom(req.getPreferredDateFrom());
        cohort.setPreferredDateTo(req.getPreferredDateTo());
        cohort.setSlotDuration(req.getSlotDuration());
        cohort.setMode(req.getMode());
        cohort.setPriority(req.getPriority() != null ? req.getPriority() : "MEDIUM");
        cohort.setNotes(req.getNotes());
        cohort.setPoc(poc);
        cohort.setProposedSmes(proposed);
        cohort.setStatus(RequestStatus.SUBMITTED);
        cohort = cohortRepo.save(cohort);

        logService.log("REQUEST_SUBMITTED", cohortId + " submitted for admin review", pocEmail, cohortId);
        return toResponse(cohort);
    }

    // ── Admin approves / rejects ──────────────────────────────────────────────
    public CohortRequestDto.Response processApproval(Long id,
                                                      CohortRequestDto.ApprovalDecision decision,
                                                      String adminEmail) {
        CohortRequest cohort = findEntityById(id);
        String dec = decision.getDecision().toUpperCase();

        if ("APPROVE".equals(dec)) {
            cohort.setAdminNote(decision.getAdminNote());
            cohort.setApprovedAt(LocalDateTime.now());

            if (decision.getSelectedSmeIds() != null && !decision.getSelectedSmeIds().isEmpty()) {
                List<Sme> selected = new ArrayList<>();
                for (Long smeId : decision.getSelectedSmeIds()) {
                    selected.add(smeService.findEntityById(smeId));
                }
                cohort.setProposedSmes(selected);
            }
            cohort = cohortRepo.save(cohort);
            sendFirstPings(cohort, adminEmail);
            logService.log("REQUEST_APPROVED", cohort.getCohortId() + " approved", adminEmail, cohort.getCohortId());

        } else if ("REJECT".equals(dec)) {
            cohort.setStatus(RequestStatus.REJECTED);
            cohort.setAdminNote(decision.getAdminNote());
            cohortRepo.save(cohort);
            logService.log("REQUEST_REJECTED", cohort.getCohortId() + " rejected", adminEmail, cohort.getCohortId());

        } else if ("REQUEST_CHANGES".equals(dec)) {
            cohort.setStatus(RequestStatus.CHANGES_REQUESTED);
            cohort.setAdminNote(decision.getAdminNote());
            cohortRepo.save(cohort);

        } else {
            throw new IllegalArgumentException("Invalid decision: " + decision.getDecision());
        }
        return toResponse(cohort);
    }

    // ── Send First Ping to each SME ───────────────────────────────────────────
    private void sendFirstPings(CohortRequest cohort, String adminEmail) {
        for (Sme sme : cohort.getProposedSmes()) {
            String token = UUID.randomUUID().toString();

            SmeResponse smeResponse = new SmeResponse();
            smeResponse.setCohortRequest(cohort);
            smeResponse.setSme(sme);
            smeResponse.setResponseStatus(SmeResponseStatus.PENDING);
            smeResponse.setResponseToken(token);
            smeResponse.setPingSentAt(LocalDateTime.now());
            smeResponse.setTokenExpiresAt(LocalDateTime.now().plusDays(3));
            responseRepo.save(smeResponse);

            sme.setAvailabilityStatus(AvailabilityStatus.PENDING);
            smeRepo.save(sme);

            notificationService.sendFirstPing(sme, cohort, token);
            logService.log("PING_SENT", "Teams ping sent to " + sme.getFullName(), adminEmail, sme.getFullName());
        }
        cohort.setStatus(RequestStatus.APPROVED);
        cohort.setPingsSentAt(LocalDateTime.now());
        cohortRepo.save(cohort);
    }

    // ── SME responds via token ────────────────────────────────────────────────
    public String handleSmeResponse(SmeResponseDto.RespondRequest req) {
        SmeResponse smeResponse = responseRepo.findByResponseToken(req.getToken())
                .orElseThrow(() -> new ResourceNotFoundException("Invalid or expired token"));

        if (smeResponse.getTokenExpiresAt().isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("Response link has expired");
        }
        if (smeResponse.getResponseStatus() != SmeResponseStatus.PENDING) {
            throw new IllegalStateException("You have already responded to this request");
        }

        Sme sme = smeResponse.getSme();
        CohortRequest cohort = smeResponse.getCohortRequest();
        String dec = req.getDecision().toUpperCase();

        if ("ACCEPT".equals(dec)) {
            smeResponse.setResponseStatus(SmeResponseStatus.ACCEPTED);
            smeResponse.setPreferredDate(req.getPreferredDate());
            sme.setAvailabilityStatus(AvailabilityStatus.BOOKED);
            sme.setLastBooked(LocalDate.now());
            logService.log("SME_ACCEPTED", sme.getFullName() + " accepted " + cohort.getCohortId(), sme.getEmail(), sme.getFullName());

        } else if ("DECLINE".equals(dec)) {
            smeResponse.setResponseStatus(SmeResponseStatus.DECLINED);
            smeResponse.setDeclineReason(req.getDeclineReason());
            sme.setAvailabilityStatus(AvailabilityStatus.AVAILABLE);
            logService.log("SME_DECLINED", sme.getFullName() + " declined " + cohort.getCohortId(), sme.getEmail(), sme.getFullName());

        } else {
            throw new IllegalArgumentException("Invalid decision: " + req.getDecision());
        }

        smeResponse.setRespondedAt(LocalDateTime.now());
        responseRepo.save(smeResponse);
        smeRepo.save(sme);
        checkAndUpdateCohortStatus(cohort);

        return "ACCEPT".equals(dec) ? "accepted" : "declined";
    }

    // ── Re-ping ───────────────────────────────────────────────────────────────
    public void rePing(Long cohortId, Long smeId, String adminEmail) {
        CohortRequest cohort = findEntityById(cohortId);
        Sme sme = smeService.findEntityById(smeId);

        List<SmeResponse> pending = responseRepo
                .findByCohortRequestIdAndResponseStatus(cohortId, SmeResponseStatus.PENDING);
        SmeResponse existing = null;
        for (SmeResponse r : pending) {
            if (r.getSme().getId().equals(smeId)) {
                existing = r;
                break;
            }
        }
        if (existing == null) {
            throw new ResourceNotFoundException("Pending response not found");
        }

        String newToken = UUID.randomUUID().toString();
        existing.setResponseToken(newToken);
        existing.setPingSentAt(LocalDateTime.now());
        existing.setTokenExpiresAt(LocalDateTime.now().plusDays(3));
        responseRepo.save(existing);

        notificationService.sendFirstPing(sme, cohort, newToken);
        logService.log("REPINGED", "Re-ping sent to " + sme.getFullName(), adminEmail, sme.getFullName());
    }

    private void checkAndUpdateCohortStatus(CohortRequest cohort) {
        List<SmeResponse> responses = responseRepo.findByCohortRequestId(cohort.getId());
        long accepted = 0;
        for (SmeResponse r : responses) {
            if (r.getResponseStatus() == SmeResponseStatus.ACCEPTED) {
                accepted++;
            }
        }
        if (accepted >= cohort.getSmesRequired()) {
            cohort.setStatus(RequestStatus.CONFIRMED);
            cohortRepo.save(cohort);
        }
    }

    // ── Read operations ───────────────────────────────────────────────────────
    @Transactional(readOnly = true)
    public List<CohortRequestDto.Response> getAllRequests() {
        List<CohortRequest> all = cohortRepo.findAll();
        List<CohortRequestDto.Response> result = new ArrayList<>();
        for (CohortRequest c : all) {
            result.add(toResponse(c));
        }
        return result;
    }

    @Transactional(readOnly = true)
    public List<CohortRequestDto.Response> getRequestsByPoc(String pocEmail) {
        User poc = userRepo.findByEmail(pocEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + pocEmail));
        List<CohortRequest> all = cohortRepo.findByPocOrderByCreatedAtDesc(poc);
        List<CohortRequestDto.Response> result = new ArrayList<>();
        for (CohortRequest c : all) {
            result.add(toResponse(c));
        }
        return result;
    }

    @Transactional(readOnly = true)
    public List<CohortRequestDto.Response> getPendingRequests() {
        List<CohortRequest> all = cohortRepo.findByStatus(RequestStatus.SUBMITTED);
        List<CohortRequestDto.Response> result = new ArrayList<>();
        for (CohortRequest c : all) {
            result.add(toResponse(c));
        }
        return result;
    }

    @Transactional(readOnly = true)
    public CohortRequestDto.Response getRequestById(Long id) {
        return toResponse(findEntityById(id));
    }

    @Transactional(readOnly = true)
    public SmeResponse getResponseByToken(String token) {
        return responseRepo.findByResponseToken(token)
                .orElseThrow(() -> new ResourceNotFoundException("Token not found or expired"));
    }

    private CohortRequest findEntityById(Long id) {
        return cohortRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cohort request not found: " + id));
    }

    private String generateCohortId() {
        long count = cohortRepo.count() + 1;
        return String.format("COH-%04d", 2023 + count);
    }

    // ── Mapper ────────────────────────────────────────────────────────────────
    public CohortRequestDto.Response toResponse(CohortRequest c) {
        List<SmeResponse> responses = responseRepo.findByCohortRequestId(c.getId());

        int confirmed = 0;
        for (SmeResponse r : responses) {
            if (r.getResponseStatus() == SmeResponseStatus.ACCEPTED) {
                confirmed++;
            }
        }

        List<SmeResponseDto.Response> responseDtos = new ArrayList<>();
        for (SmeResponse r : responses) {
            SmeResponseDto.Response dto = new SmeResponseDto.Response();
            dto.setId(r.getId());
            dto.setSmeId(r.getSme().getId());
            dto.setSmeName(r.getSme().getFullName());
            dto.setSmeEmail(r.getSme().getEmail());
            dto.setSmeSpecialties(r.getSme().getSpecialties());
            dto.setResponseStatus(r.getResponseStatus());
            dto.setPreferredDate(r.getPreferredDate());
            dto.setDeclineReason(r.getDeclineReason());
            dto.setPingSentAt(r.getPingSentAt());
            dto.setRespondedAt(r.getRespondedAt());
            dto.setCohortId(c.getCohortId());
            responseDtos.add(dto);
        }

        List<SmeDto.Summary> proposedDtos = new ArrayList<>();
        for (Sme s : c.getProposedSmes()) {
            proposedDtos.add(smeService.toSummary(s));
        }

        CohortRequestDto.Response resp = new CohortRequestDto.Response();
        resp.setId(c.getId());
        resp.setCohortId(c.getCohortId());
        resp.setEvaluationType(c.getEvaluationType());
        resp.setRequiredSpecialties(c.getRequiredSpecialties());
        resp.setSmesRequired(c.getSmesRequired());
        resp.setParticipantCount(c.getParticipantCount());
        resp.setPreferredDateFrom(c.getPreferredDateFrom());
        resp.setPreferredDateTo(c.getPreferredDateTo());
        resp.setSlotDuration(c.getSlotDuration());
        resp.setMode(c.getMode());
        resp.setPriority(c.getPriority());
        resp.setNotes(c.getNotes());
        resp.setAdminNote(c.getAdminNote());
        resp.setStatus(c.getStatus());
        resp.setPocName(c.getPoc().getFullName());
        resp.setPocEmail(c.getPoc().getEmail());
        resp.setPocId(c.getPoc().getId());
        resp.setProposedSmes(proposedDtos);
        resp.setSmeResponses(responseDtos);
        resp.setConfirmedCount(confirmed);
        resp.setCreatedAt(c.getCreatedAt());
        resp.setApprovedAt(c.getApprovedAt());
        resp.setPingsSentAt(c.getPingsSentAt());
        return resp;
    }
}
