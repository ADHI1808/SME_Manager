package com.smemanager.dto;

import com.smemanager.enums.SmeResponseStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class SmeResponseDto {

    public static class RespondRequest {
        private String    token;
        private String    decision;
        private LocalDate preferredDate;
        private String    declineReason;

        public RespondRequest() {}

        public String    getToken()         { return token; }
        public String    getDecision()      { return decision; }
        public LocalDate getPreferredDate() { return preferredDate; }
        public String    getDeclineReason() { return declineReason; }

        public void setToken(String token)               { this.token         = token; }
        public void setDecision(String decision)         { this.decision      = decision; }
        public void setPreferredDate(LocalDate date)     { this.preferredDate = date; }
        public void setDeclineReason(String reason)      { this.declineReason = reason; }
    }

    public static class Response {
        private Long              id;
        private Long              smeId;
        private String            smeName;
        private String            smeEmail;
        private List<String>      smeSpecialties;
        private SmeResponseStatus responseStatus;
        private LocalDate         preferredDate;
        private String            declineReason;
        private LocalDateTime     pingSentAt;
        private LocalDateTime     respondedAt;
        private String            cohortId;

        public Response() {}

        public Long              getId()             { return id; }
        public Long              getSmeId()          { return smeId; }
        public String            getSmeName()        { return smeName; }
        public String            getSmeEmail()       { return smeEmail; }
        public List<String>      getSmeSpecialties() { return smeSpecialties; }
        public SmeResponseStatus getResponseStatus() { return responseStatus; }
        public LocalDate         getPreferredDate()  { return preferredDate; }
        public String            getDeclineReason()  { return declineReason; }
        public LocalDateTime     getPingSentAt()     { return pingSentAt; }
        public LocalDateTime     getRespondedAt()    { return respondedAt; }
        public String            getCohortId()       { return cohortId; }

        public void setId(Long id)                               { this.id             = id; }
        public void setSmeId(Long smeId)                         { this.smeId          = smeId; }
        public void setSmeName(String smeName)                   { this.smeName        = smeName; }
        public void setSmeEmail(String smeEmail)                 { this.smeEmail       = smeEmail; }
        public void setSmeSpecialties(List<String> specs)        { this.smeSpecialties = specs; }
        public void setResponseStatus(SmeResponseStatus status)  { this.responseStatus = status; }
        public void setPreferredDate(LocalDate preferredDate)    { this.preferredDate  = preferredDate; }
        public void setDeclineReason(String declineReason)       { this.declineReason  = declineReason; }
        public void setPingSentAt(LocalDateTime pingSentAt)      { this.pingSentAt     = pingSentAt; }
        public void setRespondedAt(LocalDateTime respondedAt)    { this.respondedAt    = respondedAt; }
        public void setCohortId(String cohortId)                 { this.cohortId       = cohortId; }
    }
}
