package com.smemanager.config;

import com.smemanager.entity.CohortRequest;
import com.smemanager.entity.Sme;
import com.smemanager.entity.User;
import com.smemanager.enums.AvailabilityStatus;
import com.smemanager.enums.RequestStatus;
import com.smemanager.enums.UserRole;
import com.smemanager.repository.CohortRequestRepository;
import com.smemanager.repository.SmeRepository;
import com.smemanager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.logging.Logger;

@Component
public class DataSeeder implements CommandLineRunner {

    private static final Logger logger = Logger.getLogger(DataSeeder.class.getName());

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private SmeRepository smeRepo;

    @Autowired
    private CohortRequestRepository cohortRepo;

    @Autowired
    private PasswordEncoder encoder;

    @Override
    public void run(String... args) {
        if (userRepo.count() > 0) {
            logger.info("Data already seeded — skipping.");
            return;
        }
        logger.info("Seeding demo data...");

        // ── Users ─────────────────────────────────────────────────────────────
        User admin = new User();
        admin.setFullName("Admin User");
        admin.setEmail("admin@sme.com");
        admin.setPassword(encoder.encode("password"));
        admin.setRole(UserRole.ADMIN);
        admin.setDepartment("Operations");
        admin.setActive(true);
        admin = userRepo.save(admin);

        User poc1 = new User();
        poc1.setFullName("Priya POC");
        poc1.setEmail("priya@sme.com");
        poc1.setPassword(encoder.encode("password"));
        poc1.setRole(UserRole.POC);
        poc1.setDepartment("Technology");
        poc1.setActive(true);
        poc1 = userRepo.save(poc1);

        User poc2 = new User();
        poc2.setFullName("Ravi POC");
        poc2.setEmail("ravi@sme.com");
        poc2.setPassword(encoder.encode("password"));
        poc2.setRole(UserRole.POC);
        poc2.setDepartment("Engineering");
        poc2.setActive(true);
        poc2 = userRepo.save(poc2);

        // ── SMEs ──────────────────────────────────────────────────────────────
        Sme sme1 = new Sme();
        sme1.setFullName("Arun Kumar");
        sme1.setEmail("arun@expert.com");
        sme1.setDepartment("Engineering");
        sme1.setDesignation("Senior Java Architect");
        sme1.setSpecialties(Arrays.asList("Java", "Python", "Spring Boot"));
        sme1.setYearsOfExperience(8);
        sme1.setTeamsId("arun@teams.com");
        sme1.setAvailabilityStatus(AvailabilityStatus.AVAILABLE);
        sme1 = smeRepo.save(sme1);

        Sme sme2 = new Sme();
        sme2.setFullName("Dr. Priya S");
        sme2.setEmail("drpriya@expert.com");
        sme2.setDepartment("Technology");
        sme2.setDesignation("Tech Lead");
        sme2.setSpecialties(Arrays.asList(".NET", "QA", "C#"));
        sme2.setYearsOfExperience(12);
        sme2.setTeamsId("drpriya@teams.com");
        sme2.setAvailabilityStatus(AvailabilityStatus.BOOKED);
        sme2.setLastBooked(LocalDate.now());
        sme2 = smeRepo.save(sme2);

        Sme sme3 = new Sme();
        sme3.setFullName("Michael Brown");
        sme3.setEmail("michael@expert.com");
        sme3.setDepartment("Infrastructure");
        sme3.setDesignation("Cloud Architect");
        sme3.setSpecialties(Arrays.asList("Cloud", "DevOps", "AWS"));
        sme3.setYearsOfExperience(6);
        sme3.setTeamsId("michael@teams.com");
        sme3.setAvailabilityStatus(AvailabilityStatus.AVAILABLE);
        sme3 = smeRepo.save(sme3);

        Sme sme4 = new Sme();
        sme4.setFullName("Anita Rao");
        sme4.setEmail("anita@expert.com");
        sme4.setDepartment("Frontend");
        sme4.setDesignation("Frontend Lead");
        sme4.setSpecialties(Arrays.asList("React", "Angular", "TypeScript"));
        sme4.setYearsOfExperience(5);
        sme4.setTeamsId("anita@teams.com");
        sme4.setAvailabilityStatus(AvailabilityStatus.AVAILABLE);
        sme4 = smeRepo.save(sme4);

        Sme sme5 = new Sme();
        sme5.setFullName("Suresh Varma");
        sme5.setEmail("suresh@expert.com");
        sme5.setDepartment("QA");
        sme5.setDesignation("QA Manager");
        sme5.setSpecialties(Arrays.asList("Java", "Testing", "Selenium"));
        sme5.setYearsOfExperience(9);
        sme5.setTeamsId("suresh@teams.com");
        sme5.setAvailabilityStatus(AvailabilityStatus.AVAILABLE);
        sme5 = smeRepo.save(sme5);

        Sme sme6 = new Sme();
        sme6.setFullName("Kavya Menon");
        sme6.setEmail("kavya@expert.com");
        sme6.setDepartment("Data Science");
        sme6.setDesignation("ML Engineer");
        sme6.setSpecialties(Arrays.asList("AI/ML", "Python", "TensorFlow"));
        sme6.setYearsOfExperience(7);
        sme6.setTeamsId("kavya@teams.com");
        sme6.setAvailabilityStatus(AvailabilityStatus.AVAILABLE);
        sme6 = smeRepo.save(sme6);

        Sme sme7 = new Sme();
        sme7.setFullName("David Chen");
        sme7.setEmail("david@expert.com");
        sme7.setDepartment("Architecture");
        sme7.setDesignation("Solution Architect");
        sme7.setSpecialties(Arrays.asList(".NET", "Cloud", "Azure"));
        sme7.setYearsOfExperience(11);
        sme7.setTeamsId("david@teams.com");
        sme7.setAvailabilityStatus(AvailabilityStatus.AVAILABLE);
        smeRepo.save(sme7);

        Sme sme8 = new Sme();
        sme8.setFullName("Meena Thomas");
        sme8.setEmail("meena@expert.com");
        sme8.setDepartment("Quality");
        sme8.setDesignation("QA Lead");
        sme8.setSpecialties(Arrays.asList("QA", "Testing", "Automation"));
        sme8.setYearsOfExperience(6);
        sme8.setTeamsId("meena@teams.com");
        sme8.setAvailabilityStatus(AvailabilityStatus.AVAILABLE);
        sme8 = smeRepo.save(sme8);

        // ── Cohort Requests ───────────────────────────────────────────────────
        CohortRequest c1 = new CohortRequest();
        c1.setCohortId("COH-2024");
        c1.setEvaluationType("SDET Evaluation");
        c1.setRequiredSpecialties(Arrays.asList("Java", "Python"));
        c1.setSmesRequired(3);
        c1.setParticipantCount(12);
        c1.setPreferredDateFrom(LocalDate.now().plusDays(5));
        c1.setPreferredDateTo(LocalDate.now().plusDays(10));
        c1.setSlotDuration("90 minutes");
        c1.setMode("Virtual");
        c1.setPriority("HIGH");
        c1.setNotes("Need senior SMEs with 5+ years experience");
        c1.setStatus(RequestStatus.CONFIRMED);
        c1.setPoc(poc1);
        c1.setProposedSmes(Arrays.asList(sme1, sme3, sme5));
        cohortRepo.save(c1);

        CohortRequest c2 = new CohortRequest();
        c2.setCohortId("COH-2025");
        c2.setEvaluationType("Panel Interview");
        c2.setRequiredSpecialties(Arrays.asList(".NET", "QA"));
        c2.setSmesRequired(2);
        c2.setParticipantCount(8);
        c2.setPreferredDateFrom(LocalDate.now().plusDays(12));
        c2.setPreferredDateTo(LocalDate.now().plusDays(16));
        c2.setSlotDuration("60 minutes");
        c2.setMode("Virtual");
        c2.setPriority("MEDIUM");
        c2.setStatus(RequestStatus.APPROVED);
        c2.setPoc(poc2);
        c2.setProposedSmes(Arrays.asList(sme2, sme8));
        cohortRepo.save(c2);

        CohortRequest c3 = new CohortRequest();
        c3.setCohortId("COH-2026");
        c3.setEvaluationType("Code Review Session");
        c3.setRequiredSpecialties(Arrays.asList("React", "Angular"));
        c3.setSmesRequired(2);
        c3.setParticipantCount(6);
        c3.setPreferredDateFrom(LocalDate.now().plusDays(20));
        c3.setPreferredDateTo(LocalDate.now().plusDays(25));
        c3.setSlotDuration("45 minutes");
        c3.setMode("On-site");
        c3.setPriority("LOW");
        c3.setStatus(RequestStatus.SUBMITTED);
        c3.setPoc(poc1);
        c3.setProposedSmes(Arrays.asList(sme4, sme6));
        cohortRepo.save(c3);

        logger.info("Demo data seeded. Login: admin@sme.com / priya@sme.com / ravi@sme.com  (password: password)");
    }
}
