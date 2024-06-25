package com.example.demo.v1.models;

import com.example.demo.v1.enumerations.ETransactionType;
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
@Table(name = "savings")
public class Saving {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    // Relationship with Customer
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    // Account Number
    private String account;

    // Transaction Type
    private ETransactionType type;

    // Transaction Amount
    private Double amount;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "banking_date_time")
    private Date bankingDateTime;

    // Last Updated Timestamp date
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_updated")
    private Date lastUpdatedDateTime;

    @PrePersist
    protected void onCreate() {
        bankingDateTime = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        lastUpdatedDateTime = new Date();
    }
}
