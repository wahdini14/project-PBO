package wahdini.getajobcopy.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "job_applications")
public class JobApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // --- RELASI KE USER (PELAMAR) ---
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // --- RELASI KE JOB YANG DILAMAR ---
    @ManyToOne
    @JoinColumn(name = "job_id", nullable = false)
    private Job job;

    // Status lamaran
    private String status; // contoh: "DILAMAR", "DITERIMA", "SELESAI"

    private LocalDateTime appliedDate;

    public JobApplication() {}

    public JobApplication(User user, Job job, String status) {
        this.user = user;
        this.job = job;
        this.status = status;
        this.appliedDate = LocalDateTime.now();
    }

    // GETTER & SETTER
    public Long getId() { return id; }

    public User getUser() { return user; }

    public Job getJob() { return job; }

    public String getStatus() { return status; }

    public LocalDateTime getAppliedDate() { return appliedDate; }

    public void setId(Long id) { this.id = id; }

    public void setUser(User user) { this.user = user; }

    public void setJob(Job job) { this.job = job; }

    public void setStatus(String status) { this.status = status; }

    public void setAppliedDate(LocalDateTime appliedDate) { this.appliedDate = appliedDate; }
}
