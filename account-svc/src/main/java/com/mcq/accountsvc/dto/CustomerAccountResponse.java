package com.mcq.accountsvc.dto;

import com.mcq.accountsvc.entity.Account;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerAccountResponse {

    // Customer info
    private Long customerId;
    private String firstName;
    private String lastName;
    private String email;
    private String mobileNumber;

    // Account info
    private Long accountId;
    private String accountNumber;
    private Account.AccountType accountType;
    private String accountDescription;
    private BigDecimal balance;
    private LocalDate createdAt;
    private String message;
}
