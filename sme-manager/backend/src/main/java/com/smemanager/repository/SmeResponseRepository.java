package com.smemanager.repository;

import com.smemanager.entity.SmeResponse;
import com.smemanager.enums.SmeResponseStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface SmeResponseRepository extends JpaRepository<SmeResponse, Long> {
    List<SmeResponse> findByCohortRequestId(Long cohortRequestId);
    Optional<SmeResponse> findByResponseToken(String token);
    List<SmeResponse> findBySmeId(Long smeId);
    List<SmeResponse> findByCohortRequestIdAndResponseStatus(Long id, SmeResponseStatus status);
}
