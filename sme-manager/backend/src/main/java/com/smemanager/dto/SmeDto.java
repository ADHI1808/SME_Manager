package com.smemanager.dto;

import com.smemanager.enums.AvailabilityStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class SmeDto {

    public static class Request {
        private String             fullName;
        private String             email;
        private String             department;
        private String             designation;
        private List<String>       specialties;
        private Integer            yearsOfExperience;
        private String             phone;
        private String             teamsId;
        private AvailabilityStatus availabilityStatus;
        private String             bio;

        public Request() {}

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

        public void setFullName(String fullName)                       { this.fullName          = fullName; }
        public void setEmail(String email)                             { this.email             = email; }
        public void setDepartment(String department)                   { this.department        = department; }
        public void setDesignation(String designation)                 { this.designation       = designation; }
        public void setSpecialties(List<String> specialties)           { this.specialties       = specialties; }
        public void setYearsOfExperience(Integer yearsOfExperience)    { this.yearsOfExperience = yearsOfExperience; }
        public void setPhone(String phone)                             { this.phone             = phone; }
        public void setTeamsId(String teamsId)                         { this.teamsId           = teamsId; }
        public void setAvailabilityStatus(AvailabilityStatus status)   { this.availabilityStatus = status; }
        public void setBio(String bio)                                 { this.bio               = bio; }
    }

    public static class Response {
        private Long               id;
        private String             fullName;
        private String             email;
        private String             department;
        private String             designation;
        private List<String>       specialties;
        private Integer            yearsOfExperience;
        private String             phone;
        private String             teamsId;
        private AvailabilityStatus availabilityStatus;
        private String             bio;
        private LocalDate          lastBooked;
        private LocalDateTime      createdAt;

        public Response() {}

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
    }

    public static class Summary {
        private Long               id;
        private String             fullName;
        private String             email;
        private List<String>       specialties;
        private String             department;
        private Integer            yearsOfExperience;
        private AvailabilityStatus availabilityStatus;

        public Summary() {}

        public Long               getId()                 { return id; }
        public String             getFullName()           { return fullName; }
        public String             getEmail()              { return email; }
        public List<String>       getSpecialties()        { return specialties; }
        public String             getDepartment()         { return department; }
        public Integer            getYearsOfExperience()  { return yearsOfExperience; }
        public AvailabilityStatus getAvailabilityStatus() { return availabilityStatus; }

        public void setId(Long id)                                   { this.id                = id; }
        public void setFullName(String fullName)                     { this.fullName          = fullName; }
        public void setEmail(String email)                           { this.email             = email; }
        public void setSpecialties(List<String> specialties)         { this.specialties       = specialties; }
        public void setDepartment(String department)                 { this.department        = department; }
        public void setYearsOfExperience(Integer yearsOfExperience)  { this.yearsOfExperience = yearsOfExperience; }
        public void setAvailabilityStatus(AvailabilityStatus status) { this.availabilityStatus = status; }
    }
}
