package com.smemanager.entity;

import com.smemanager.enums.AvailabilityStatus;
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
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "smes")
public class Sme {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String department;

    private String designation;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "sme_specialties", joinColumns = @JoinColumn(name = "sme_id"))
    @Column(name = "specialty")
    private List<String> specialties = new ArrayList<>();

    private Integer yearsOfExperience;
    private String  phone;

    @Column(name = "teams_id")
    private String teamsId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AvailabilityStatus availabilityStatus = AvailabilityStatus.AVAILABLE;

    @Column(columnDefinition = "TEXT")
    private String bio;

    private LocalDate lastBooked;

    @Column(updatable = false)
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Sme() {}

    @PrePersist
    void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public Long               getId()                 { return id; }
    public String             getFullName()           { return fullName; }
    public String             getEmail()              { return email; }
    public String             getDepartment()         { return department; }
    public String             getDesignation()        { return designation; }
    public List<String>       getSpecialties()        { return specialties; }
    public Integer            getYearsOfExperience()  { return yearsOfExperience; }
    public String             getPhone()              { return phone; }
    public String             getTeamsId()            { return teamsId; }
    public AvailabilityStatus getAvailabilityStatus() { return availabilityStatus; }
    public String             getBio()                { return bio; }
    public LocalDate          getLastBooked()         { return lastBooked; }
    public LocalDateTime      getCreatedAt()          { return createdAt; }
    public LocalDateTime      getUpdatedAt()          { return updatedAt; }

    public void setId(Long id)                                   { this.id                = id; }
    public void setFullName(String fullName)                     { this.fullName          = fullName; }
    public void setEmail(String email)                           { this.email             = email; }
    public void setDepartment(String department)                 { this.department        = department; }
    public void setDesignation(String designation)               { this.designation       = designation; }
    public void setSpecialties(List<String> specialties)         { this.specialties       = specialties; }
    public void setYearsOfExperience(Integer yearsOfExperience)  { this.yearsOfExperience = yearsOfExperience; }
    public void setPhone(String phone)                           { this.phone             = phone; }
    public void setTeamsId(String teamsId)                       { this.teamsId           = teamsId; }
    public void setAvailabilityStatus(AvailabilityStatus status) { this.availabilityStatus = status; }
    public void setBio(String bio)                               { this.bio               = bio; }
    public void setLastBooked(LocalDate lastBooked)              { this.lastBooked        = lastBooked; }
    public void setCreatedAt(LocalDateTime createdAt)            { this.createdAt         = createdAt; }
    public void setUpdatedAt(LocalDateTime updatedAt)            { this.updatedAt         = updatedAt; }
}
