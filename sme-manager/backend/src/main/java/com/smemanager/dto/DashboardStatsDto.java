package com.smemanager.dto;

import java.util.List;

public class DashboardStatsDto {

    private Long                          totalSmes;
    private Long                          availableSmes;
    private Long                          bookedSmes;
    private Long                          pendingSmes;
    private Long                          pendingApprovals;
    private Long                          bookedThisMonth;
    private Long                          activeCohorts;
    private Long                          smeConfirmed;
    private Long                          awaitingApproval;
    private Long                          declined;
    private List<ActivityLogDto>          recentActivity;
    private List<CohortRequestDto.Response> recentRequests;

    public DashboardStatsDto() {}

    public Long                           getTotalSmes()        { return totalSmes; }
    public Long                           getAvailableSmes()    { return availableSmes; }
    public Long                           getBookedSmes()       { return bookedSmes; }
    public Long                           getPendingSmes()      { return pendingSmes; }
    public Long                           getPendingApprovals() { return pendingApprovals; }
    public Long                           getBookedThisMonth()  { return bookedThisMonth; }
    public Long                           getActiveCohorts()    { return activeCohorts; }
    public Long                           getSmeConfirmed()     { return smeConfirmed; }
    public Long                           getAwaitingApproval() { return awaitingApproval; }
    public Long                           getDeclined()         { return declined; }
    public List<ActivityLogDto>           getRecentActivity()   { return recentActivity; }
    public List<CohortRequestDto.Response> getRecentRequests()  { return recentRequests; }

    public void setTotalSmes(Long totalSmes)                          { this.totalSmes        = totalSmes; }
    public void setAvailableSmes(Long availableSmes)                  { this.availableSmes    = availableSmes; }
    public void setBookedSmes(Long bookedSmes)                        { this.bookedSmes       = bookedSmes; }
    public void setPendingSmes(Long pendingSmes)                       { this.pendingSmes      = pendingSmes; }
    public void setPendingApprovals(Long pendingApprovals)             { this.pendingApprovals = pendingApprovals; }
    public void setBookedThisMonth(Long bookedThisMonth)               { this.bookedThisMonth  = bookedThisMonth; }
    public void setActiveCohorts(Long activeCohorts)                   { this.activeCohorts    = activeCohorts; }
    public void setSmeConfirmed(Long smeConfirmed)                     { this.smeConfirmed     = smeConfirmed; }
    public void setAwaitingApproval(Long awaitingApproval)             { this.awaitingApproval = awaitingApproval; }
    public void setDeclined(Long declined)                             { this.declined         = declined; }
    public void setRecentActivity(List<ActivityLogDto> recentActivity) { this.recentActivity   = recentActivity; }
    public void setRecentRequests(List<CohortRequestDto.Response> recentRequests) { this.recentRequests = recentRequests; }
}
