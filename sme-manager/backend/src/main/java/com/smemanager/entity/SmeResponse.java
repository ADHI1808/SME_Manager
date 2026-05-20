package com.smemanager.entity;

import com.smemanager.enums.SmeResponseStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "sme_responses")
public class SmeResponse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cohort_request_id", nullable = false)
    private CohortRequest cohortRequest;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sme_id", nullable = false)
    private Sme sme;

    @Enumerated(EnumType.STRING)
    private SmeResponseStatus responseStatus = SmeResponseStatus.PENDING;

    private LocalDate    preferredDate;
    private String       declineReason;
    private String       responseToken;
    private LocalDateTime pingSentAt;
    private LocalDateTime respondedAt;
    private LocalDateTime tokenExpiresAt;

    @Column(updatable = false)
    private LocalDateTime createdAt;

    public SmeResponse() {}

    @PrePersist
    void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    public Long              getId()             { return id; }
    public CohortRequest     getCohortRequest()  { return cohortRequest; }
    public Sme               getSme()            { return sme; }
    public SmeResponseStatus getResponseStatus() { return responseStatus; }
    public LocalDate         getPreferredDate()  { return preferredDate; }
    public String            getDeclineReason()  { return declineReason; }
    public String            getResponseToken()  { return responseToken; }
    public LocalDateTime     getPingSentAt()     { return pingSentAt; }
    public LocalDateTime     getRespondedAt()    { return respondedAt; }
    public LocalDateTime     getTokenExpiresAt() { return tokenExpiresAt; }
    public LocalDateTime     getCreatedAt()      { return createdAt; }

    public void setId(Long id)                                      { this.id             = id; }
    public void setCohortRequest(CohortRequest cohortRequest)       { this.cohortRequest  = cohortRequest; }
    public void setSme(Sme sme)                                     { this.sme            = sme; }
    public void setResponseStatus(SmeResponseStatus responseStatus) { this.responseStatus = responseStatus; }
    public void setPreferredDate(LocalDate preferredDate)           { this.preferredDate  = preferredDate; }
    public void setDeclineReason(String declineReason)              { this.declineReason  = declineReason; }
    public void setResponseToken(String responseToken)              { this.responseToken  = responseToken; }
    public void setPingSentAt(LocalDateTime pingSentAt)             { this.pingSentAt     = pingSentAt; }
    public void setRespondedAt(LocalDateTime respondedAt)           { this.respondedAt    = respondedAt; }
    public void setTokenExpiresAt(LocalDateTime tokenExpiresAt)     { this.tokenExpiresAt = tokenExpiresAt; }
    public void setCreatedAt(LocalDateTime createdAt)               { this.createdAt      = createdAt; }
}
