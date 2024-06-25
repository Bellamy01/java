package com.example.demo.v1.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "message")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    // Relationship with Customer
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    private String message;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "message_date_time")
    private Date messageDateTime;

    // Last Updated Timestamp date
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_updated")
    private Date lastUpdatedDateTime;

    @PrePersist
    protected void onCreate() {
        messageDateTime = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        lastUpdatedDateTime = new Date();
    }
}
