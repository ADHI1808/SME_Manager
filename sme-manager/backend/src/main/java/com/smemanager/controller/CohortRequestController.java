package com.smemanager.controller;

import com.smemanager.dto.CohortRequestDto;
import com.smemanager.dto.SmeResponseDto;
import com.smemanager.entity.SmeResponse;
import com.smemanager.service.CohortRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class CohortRequestController {

    @Autowired
    private CohortRequestService service;

    // ── Admin ─────────────────────────────────────────────────────────────────
    @GetMapping("/admin/requests")
    public ResponseEntity<List<CohortRequestDto.Response>> getAllRequests() {
        return ResponseEntity.ok(service.getAllRequests());
    }

    @GetMapping("/admin/requests/pending")
    public ResponseEntity<List<CohortRequestDto.Response>> getPendingRequests() {
        return ResponseEntity.ok(service.getPendingRequests());
    }

    @GetMapping("/admin/requests/{id}")
    public ResponseEntity<CohortRequestDto.Response> getRequest(@PathVariable Long id) {
        return ResponseEntity.ok(service.getRequestById(id));
    }

    @PostMapping("/admin/requests/{id}/decision")
    public ResponseEntity<CohortRequestDto.Response> processDecision(
            @PathVariable Long id,
            @RequestBody CohortRequestDto.ApprovalDecision decision,
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(service.processApproval(id, decision, userDetails.getUsername()));
    }

    @PostMapping("/admin/requests/{cohortId}/smes/{smeId}/reping")
    public ResponseEntity<Void> rePing(@PathVariable Long cohortId,
                                        @PathVariable Long smeId,
                                        @AuthenticationPrincipal UserDetails ud) {
        service.rePing(cohortId, smeId, ud.getUsername());
        return ResponseEntity.ok().build();
    }

    // ── POC ───────────────────────────────────────────────────────────────────
    @GetMapping("/poc/requests")
    public ResponseEntity<List<CohortRequestDto.Response>> getMyRequests(
            @AuthenticationPrincipal UserDetails ud) {
        return ResponseEntity.ok(service.getRequestsByPoc(ud.getUsername()));
    }

    @PostMapping("/poc/requests")
    public ResponseEntity<CohortRequestDto.Response> createRequest(
            @RequestBody CohortRequestDto.CreateRequest req,
            @AuthenticationPrincipal UserDetails ud) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.createRequest(req, ud.getUsername()));
    }

    @GetMapping("/poc/requests/{id}")
    public ResponseEntity<CohortRequestDto.Response> getMyRequest(@PathVariable Long id) {
        return ResponseEntity.ok(service.getRequestById(id));
    }

    // ── Public: SME Response ──────────────────────────────────────────────────
    @PostMapping("/sme-responses/respond")
    public ResponseEntity<Map<String, String>> respond(
            @RequestBody SmeResponseDto.RespondRequest req) {
        String result = service.handleSmeResponse(req);
        Map<String, String> body = new HashMap<>();
        body.put("status", result);
        body.put("message", "ACCEPT".equalsIgnoreCase(req.getDecision())
                ? "Thank you! Your availability has been confirmed."
                : "Response recorded. Thank you for letting us know.");
        return ResponseEntity.ok(body);
    }

    @GetMapping("/sme-responses/token/{token}")
    public ResponseEntity<Map<String, Object>> getResponseByToken(@PathVariable String token) {
        SmeResponse r = service.getResponseByToken(token);
        Map<String, Object> body = new HashMap<>();
        body.put("smeName",           r.getSme().getFullName());
        body.put("smeSpecialties",    r.getSme().getSpecialties());
        body.put("cohortId",          r.getCohortRequest().getCohortId());
        body.put("evaluationType",    r.getCohortRequest().getEvaluationType());
        body.put("preferredDateFrom", r.getCohortRequest().getPreferredDateFrom().toString());
        body.put("preferredDateTo",   r.getCohortRequest().getPreferredDateTo().toString());
        body.put("slotDuration",      r.getCohortRequest().getSlotDuration());
        body.put("mode",              r.getCohortRequest().getMode());
        body.put("participantCount",  r.getCohortRequest().getParticipantCount());
        body.put("status",            r.getResponseStatus().name());
        return ResponseEntity.ok(body);
    }
}
