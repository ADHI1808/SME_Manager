package com.smemanager.repository;

import com.smemanager.entity.Sme;
import com.smemanager.enums.AvailabilityStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface SmeRepository extends JpaRepository<Sme, Long> {

    boolean existsByEmail(String email);

    List<Sme> findByAvailabilityStatus(AvailabilityStatus status);

    @Query("SELECT s FROM Sme s WHERE s.availabilityStatus = 'AVAILABLE' " +
           "AND (:specialty IS NULL OR :specialty MEMBER OF s.specialties)")
    List<Sme> findAvailableBySpecialty(@Param("specialty") String specialty);

    @Query("SELECT s FROM Sme s WHERE " +
           "(:name IS NULL OR LOWER(s.fullName) LIKE LOWER(CONCAT('%',:name,'%'))) AND " +
           "(:dept IS NULL OR LOWER(s.department) LIKE LOWER(CONCAT('%',:dept,'%'))) AND " +
           "(:status IS NULL OR s.availabilityStatus = :status)")
    List<Sme> searchSmes(@Param("name") String name,
                         @Param("dept") String dept,
                         @Param("status") AvailabilityStatus status);

    @Query("SELECT s FROM Sme s JOIN s.specialties sp " +
           "WHERE s.availabilityStatus = 'AVAILABLE' AND sp IN :specialties")
    List<Sme> findAvailableBySpecialties(@Param("specialties") List<String> specialties);

    long countByAvailabilityStatus(AvailabilityStatus status);
}
