package com.smemanager.dto;

import com.smemanager.enums.RequestStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class CohortRequestDto {

    public static class CreateRequest {
        private String       evaluationType;
        private List<String> requiredSpecialties;
        private Integer      smesRequired;
        private Integer      participantCount;
        private LocalDate    preferredDateFrom;
        private LocalDate    preferredDateTo;
        private String       slotDuration;
        private String       mode;
        private String       priority;
        private String       notes;
        private List<Long>   proposedSmeIds;

        public CreateRequest() {}

        public String       getEvaluationType()      { return evaluationType; }
        public List<String> getRequiredSpecialties() { return requiredSpecialties; }
        public Integer      getSmesRequired()        { return smesRequired; }
        public Integer      getParticipantCount()    { return participantCount; }
        public LocalDate    getPreferredDateFrom()   { return preferredDateFrom; }
        public LocalDate    getPreferredDateTo()     { return preferredDateTo; }
        public String       getSlotDuration()        { return slotDuration; }
        public String       getMode()                { return mode; }
        public String       getPriority()            { return priority; }
        public String       getNotes()               { return notes; }
        public List<Long>   getProposedSmeIds()      { return proposedSmeIds; }

        public void setEvaluationType(String evaluationType)            { this.evaluationType      = evaluationType; }
        public void setRequiredSpecialties(List<String> requiredSpecialties){ this.requiredSpecialties = requiredSpecialties; }
        public void setSmesRequired(Integer smesRequired)                { this.smesRequired        = smesRequired; }
        public void setParticipantCount(Integer participantCount)        { this.participantCount    = participantCount; }
        public void setPreferredDateFrom(LocalDate preferredDateFrom)    { this.preferredDateFrom   = preferredDateFrom; }
        public void setPreferredDateTo(LocalDate preferredDateTo)        { this.preferredDateTo     = preferredDateTo; }
        public void setSlotDuration(String slotDuration)                 { this.slotDuration        = slotDuration; }
        public void setMode(String mode)                                 { this.mode                = mode; }
        public void setPriority(String priority)                         { this.priority            = priority; }
        public void setNotes(String notes)                               { this.notes               = notes; }
        public void setProposedSmeIds(List<Long> proposedSmeIds)         { this.proposedSmeIds      = proposedSmeIds; }
    }

    public static class Response {
        private Long                        id;
        private String                      cohortId;
        private String                      evaluationType;
        private List<String>                requiredSpecialties;
        private Integer                     smesRequired;
        private Integer                     participantCount;
        private LocalDate                   preferredDateFrom;
        private LocalDate                   preferredDateTo;
        private String                      slotDuration;
        private String                      mode;
        private String                      priority;
        private String                      notes;
        private String                      adminNote;
        private RequestStatus               status;
        private String                      pocName;
        private String                      pocEmail;
        private Long                        pocId;
        private List<SmeDto.Summary>        proposedSmes;
        private List<SmeResponseDto.Response> smeResponses;
        private Integer                     confirmedCount;
        private LocalDateTime               createdAt;
        private LocalDateTime               approvedAt;
        private LocalDateTime               pingsSentAt;

        public Response() {}

        public Long                         getId()                  { return id; }
        public String                       getCohortId()            { return cohortId; }
        public String                       getEvaluationType()      { return evaluationType; }
        public List<String>                 getRequiredSpecialties() { return requiredSpecialties; }
        public Integer                      getSmesRequired()        { return smesRequired; }
        public Integer                      getParticipantCount()    { return participantCount; }
        public LocalDate                    getPreferredDateFrom()   { return preferredDateFrom; }
        public LocalDate                    getPreferredDateTo()     { return preferredDateTo; }
        public String                       getSlotDuration()        { return slotDuration; }
        public String                       getMode()                { return mode; }
        public String                       getPriority()            { return priority; }
        public String                       getNotes()               { return notes; }
        public String                       getAdminNote()           { return adminNote; }
        public RequestStatus                getStatus()              { return status; }
        public String                       getPocName()             { return pocName; }
        public String                       getPocEmail()            { return pocEmail; }
        public Long                         getPocId()               { return pocId; }
        public List<SmeDto.Summary>         getProposedSmes()        { return proposedSmes; }
        public List<SmeResponseDto.Response> getSmeResponses()       { return smeResponses; }
        public Integer                      getConfirmedCount()      { return confirmedCount; }
        public LocalDateTime                getCreatedAt()           { return createdAt; }
        public LocalDateTime                getApprovedAt()          { return approvedAt; }
        public LocalDateTime                getPingsSentAt()         { return pingsSentAt; }

        public void setId(Long id)                                            { this.id                = id; }
        public void setCohortId(String cohortId)                              { this.cohortId          = cohortId; }
        public void setEvaluationType(String evaluationType)                  { this.evaluationType    = evaluationType; }
        public void setRequiredSpecialties(List<String> requiredSpecialties)  { this.requiredSpecialties = requiredSpecialties; }
        public void setSmesRequired(Integer smesRequired)                     { this.smesRequired      = smesRequired; }
        public void setParticipantCount(Integer participantCount)             { this.participantCount  = participantCount; }
        public void setPreferredDateFrom(LocalDate preferredDateFrom)         { this.preferredDateFrom = preferredDateFrom; }
        public void setPreferredDateTo(LocalDate preferredDateTo)             { this.preferredDateTo   = preferredDateTo; }
        public void setSlotDuration(String slotDuration)                      { this.slotDuration      = slotDuration; }
        public void setMode(String mode)                                      { this.mode              = mode; }
        public void setPriority(String priority)                              { this.priority          = priority; }
        public void setNotes(String notes)                                    { this.notes             = notes; }
        public void setAdminNote(String adminNote)                            { this.adminNote         = adminNote; }
        public void setStatus(RequestStatus status)                           { this.status            = status; }
        public void setPocName(String pocName)                                { this.pocName           = pocName; }
        public void setPocEmail(String pocEmail)                              { this.pocEmail          = pocEmail; }
        public void setPocId(Long pocId)                                      { this.pocId             = pocId; }
        public void setProposedSmes(List<SmeDto.Summary> proposedSmes)        { this.proposedSmes      = proposedSmes; }
        public void setSmeResponses(List<SmeResponseDto.Response> smeResponses){ this.smeResponses     = smeResponses; }
        public void setConfirmedCount(Integer confirmedCount)                 { this.confirmedCount    = confirmedCount; }
        public void setCreatedAt(LocalDateTime createdAt)                     { this.createdAt         = createdAt; }
        public void setApprovedAt(LocalDateTime approvedAt)                   { this.approvedAt        = approvedAt; }
        public void setPingsSentAt(LocalDateTime pingsSentAt)                 { this.pingsSentAt       = pingsSentAt; }
    }

    public static class ApprovalDecision {
        private String     decision;
        private String     adminNote;
        private List<Long> selectedSmeIds;

        public ApprovalDecision() {}

        public String     getDecision()       { return decision; }
        public String     getAdminNote()      { return adminNote; }
        public List<Long> getSelectedSmeIds() { return selectedSmeIds; }

        public void setDecision(String decision)             { this.decision       = decision; }
        public void setAdminNote(String adminNote)           { this.adminNote      = adminNote; }
        public void setSelectedSmeIds(List<Long> selectedSmeIds) { this.selectedSmeIds = selectedSmeIds; }
    }
}
