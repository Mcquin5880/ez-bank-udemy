package com.mcq.accountsvc.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "accounts")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String accountNumber;

    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    private BigDecimal balance;

    private BigDecimal hiddenFees;

    private BigDecimal monthlyScamFee;

    private String branchCode;

    @Column(name = "created_at", updatable = false)
    private LocalDate createdAt;

    private Boolean isActive;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDate.now();
        if (isActive == null) {
            isActive = true;
        }
    }

    @Getter
    public enum AccountType {
        SAVINGS("Definitely Not a Pyramid Scheme Savings"),
        CHECKING("Zero-Interest Premium Checking"),
        PREMIUM("Ultra Premium Gold Platinum Diamond Elite");

        private final String description;

        AccountType(String description) {
            this.description = description;
        }
    }
}
