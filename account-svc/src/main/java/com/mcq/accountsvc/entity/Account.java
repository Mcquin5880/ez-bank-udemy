package com.mcq.accountsvc.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

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

    @CreationTimestamp
    private LocalDate createdAt;

    private boolean isActive;

    private boolean communicationSwitch;

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
