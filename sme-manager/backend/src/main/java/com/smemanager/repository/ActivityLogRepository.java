package com.smemanager.repository;

import com.smemanager.entity.ActivityLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface ActivityLogRepository extends JpaRepository<ActivityLog, Long> {
    List<ActivityLog> findAllByOrderByCreatedAtDesc(Pageable pageable);
    List<ActivityLog> findByPerformedByOrderByCreatedAtDesc(String performedBy);
}
