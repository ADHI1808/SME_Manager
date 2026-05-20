package com.smemanager.service;

import com.smemanager.dto.CohortRequestDto;
import com.smemanager.dto.DashboardStatsDto;
import com.smemanager.enums.AvailabilityStatus;
import com.smemanager.enums.RequestStatus;
import com.smemanager.repository.CohortRequestRepository;
import com.smemanager.repository.SmeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class DashboardService {

    @Autowired
    private SmeRepository smeRepo;

    @Autowired
    private CohortRequestRepository cohortRepo;

    @Autowired
    private ActivityLogService logService;

    @Autowired
    private CohortRequestService cohortService;

    public DashboardStatsDto getAdminStats() {
        List<CohortRequestDto.Response> pending = cohortService.getPendingRequests();

        DashboardStatsDto dto = new DashboardStatsDto();
        dto.setTotalSmes(smeRepo.count());
        dto.setAvailableSmes(smeRepo.countByAvailabilityStatus(AvailabilityStatus.AVAILABLE));
        dto.setBookedSmes(smeRepo.countByAvailabilityStatus(AvailabilityStatus.BOOKED));
        dto.setPendingSmes(smeRepo.countByAvailabilityStatus(AvailabilityStatus.PENDING));
        dto.setPendingApprovals((long) pending.size());
        dto.setBookedThisMonth(smeRepo.countByAvailabilityStatus(AvailabilityStatus.BOOKED));
        dto.setRecentActivity(logService.getRecentActivity(10));
        dto.setRecentRequests(pending);
        return dto;
    }

    public DashboardStatsDto getPocStats(String pocEmail) {
        List<CohortRequestDto.Response> all = cohortService.getRequestsByPoc(pocEmail);

        long confirmed  = 0, pending = 0, declined = 0, active = 0, smeConfirmed = 0;
        for (CohortRequestDto.Response r : all) {
            if (r.getStatus() == RequestStatus.CONFIRMED)    confirmed++;
            if (r.getStatus() == RequestStatus.SUBMITTED)    pending++;
            if (r.getStatus() == RequestStatus.REJECTED)     declined++;
            if (r.getStatus() == RequestStatus.APPROVED
             || r.getStatus() == RequestStatus.CONFIRMED)    active++;
            if (r.getConfirmedCount() != null)               smeConfirmed += r.getConfirmedCount();
        }

        DashboardStatsDto dto = new DashboardStatsDto();
        dto.setActiveCohorts(active);
        dto.setSmeConfirmed(smeConfirmed);
        dto.setAwaitingApproval(pending);
        dto.setDeclined(declined);
        dto.setRecentActivity(logService.getRecentActivity(6));
        return dto;
    }
}
