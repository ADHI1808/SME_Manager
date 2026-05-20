package com.smemanager.entity;

import com.smemanager.enums.RequestStatus;
import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cohort_requests")
public class CohortRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String cohortId;

    @Column(nullable = false)
    private String evaluationType;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "cohort_specialties",
                     joinColumns = @JoinColumn(name = "cohort_request_id"))
    @Column(name = "specialty")
    private List<String> requiredSpecialties = new ArrayList<>();

    @Column(nullable = false)
    private Integer smesRequired;

    private Integer  participantCount;
    private LocalDate preferredDateFrom;
    private LocalDate preferredDateTo;
    private String   slotDuration;
    private String   mode;

    @Column(nullable = false)
    private String priority = "MEDIUM";

    @Column(columnDefinition = "TEXT")
    private String notes;

    @Column(columnDefinition = "TEXT")
    private String adminNote;

    @Enumerated(EnumType.STRING)
    private RequestStatus status = RequestStatus.SUBMITTED;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "poc_id", nullable = false)
    private User poc;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "cohort_proposed_smes",
               joinColumns = @JoinColumn(name = "cohort_request_id"),
               inverseJoinColumns = @JoinColumn(name = "sme_id"))
    private List<Sme> proposedSmes = new ArrayList<>();

    @OneToMany(mappedBy = "cohortRequest", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SmeResponse> smeResponses = new ArrayList<>();

    @Column(updatable = false)
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime approvedAt;
    private LocalDateTime pingsSentAt;

    public CohortRequest() {}

    @PrePersist
    void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public Long              getId()                  { return id; }
    public String            getCohortId()            { return cohortId; }
    public String            getEvaluationType()      { return evaluationType; }
    public List<String>      getRequiredSpecialties() { return requiredSpecialties; }
    public Integer           getSmesRequired()        { return smesRequired; }
    public Integer           getParticipantCount()    { return participantCount; }
    public LocalDate         getPreferredDateFrom()   { return preferredDateFrom; }
    public LocalDate         getPreferredDateTo()     { return preferredDateTo; }
    public String            getSlotDuration()        { return slotDuration; }
    public String            getMode()                { return mode; }
    public String            getPriority()            { return priority; }
    public String            getNotes()               { return notes; }
    public String            getAdminNote()           { return adminNote; }
    public RequestStatus     getStatus()              { return status; }
    public User              getPoc()                 { return poc; }
    public List<Sme>         getProposedSmes()        { return proposedSmes; }
    public List<SmeResponse> getSmeResponses()        { return smeResponses; }
    public LocalDateTime     getCreatedAt()           { return createdAt; }
    public LocalDateTime     getUpdatedAt()           { return updatedAt; }
    public LocalDateTime     getApprovedAt()          { return approvedAt; }
    public LocalDateTime     getPingsSentAt()         { return pingsSentAt; }

    public void setId(Long id)                                           { this.id                = id; }
    public void setCohortId(String cohortId)                             { this.cohortId          = cohortId; }
    public void setEvaluationType(String evaluationType)                 { this.evaluationType    = evaluationType; }
    public void setRequiredSpecialties(List<String> requiredSpecialties) { this.requiredSpecialties = requiredSpecialties; }
    public void setSmesRequired(Integer smesRequired)                    { this.smesRequired      = smesRequired; }
    public void setParticipantCount(Integer participantCount)            { this.participantCount  = participantCount; }
    public void setPreferredDateFrom(LocalDate preferredDateFrom)        { this.preferredDateFrom = preferredDateFrom; }
    public void setPreferredDateTo(LocalDate preferredDateTo)            { this.preferredDateTo   = preferredDateTo; }
    public void setSlotDuration(String slotDuration)                     { this.slotDuration      = slotDuration; }
    public void setMode(String mode)                                     { this.mode              = mode; }
    public void setPriority(String priority)                             { this.priority          = priority; }
    public void setNotes(String notes)                                   { this.notes             = notes; }
    public void setAdminNote(String adminNote)                           { this.adminNote         = adminNote; }
    public void setStatus(RequestStatus status)                          { this.status            = status; }
    public void setPoc(User poc)                                         { this.poc               = poc; }
    public void setProposedSmes(List<Sme> proposedSmes)                  { this.proposedSmes      = proposedSmes; }
    public void setSmeResponses(List<SmeResponse> smeResponses)          { this.smeResponses      = smeResponses; }
    public void setCreatedAt(LocalDateTime createdAt)                    { this.createdAt         = createdAt; }
    public void setUpdatedAt(LocalDateTime updatedAt)                    { this.updatedAt         = updatedAt; }
    public void setApprovedAt(LocalDateTime approvedAt)                  { this.approvedAt        = approvedAt; }
    public void setPingsSentAt(LocalDateTime pingsSentAt)                { this.pingsSentAt       = pingsSentAt; }
}
