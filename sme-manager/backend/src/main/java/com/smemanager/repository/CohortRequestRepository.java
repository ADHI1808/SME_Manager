package com.smemanager.repository;

import com.smemanager.entity.CohortRequest;
import com.smemanager.entity.User;
import com.smemanager.enums.RequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

public interface CohortRequestRepository extends JpaRepository<CohortRequest, Long> {

    Optional<CohortRequest> findByCohortId(String cohortId);
    boolean existsByCohortId(String cohortId);
    List<CohortRequest> findByPoc(User poc);
    List<CohortRequest> findByStatus(RequestStatus status);
    List<CohortRequest> findByPocAndStatus(User poc, RequestStatus status);

    @Query("SELECT COUNT(c) FROM CohortRequest c WHERE c.status = :status")
    long countByStatus(RequestStatus status);

    @Query("SELECT c FROM CohortRequest c WHERE c.poc = :poc ORDER BY c.createdAt DESC")
    List<CohortRequest> findByPocOrderByCreatedAtDesc(User poc);
}
