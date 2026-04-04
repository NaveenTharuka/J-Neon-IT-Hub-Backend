package com.SE.ITHub.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(
        name = "contact_messages",
        indexes = {
                @Index(name = "idx_status_created", columnList = "status, created_at"),
                @Index(name = "idx_contact_service", columnList = "service_id"),
                @Index(name = "idx_email", columnList = "email")
        }
)
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(length = 120, nullable = false)
    private String name;

    @Email
    @Column(length = 190, nullable = false)
    private String email;

    @Column(length = 40)
    private String phone;

    @Column(length = 160)
    private String subject;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String message;

    /* -------- Service relationship -------- */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "service_id",
            foreignKey = @ForeignKey(name = "fk_contact_service")
    )
    private Service service;

    @Column(length = 20)
    private String status = "new";

    @Column(name = "source_page", length = 160)
    private String sourcePage;

    @Column(name = "ip_address", length = 45)
    private String ipAddress;

    @Column(name = "user_agent", length = 255)
    private String userAgent;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    /* -------- Lifecycle -------- */
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

}
