package com.mcq.accountsvc.controller;

import com.mcq.accountsvc.dto.CustomerAccountRequest;
import com.mcq.accountsvc.dto.CustomerAccountResponse;
import com.mcq.accountsvc.entity.Account;
import com.mcq.accountsvc.entity.Customer;
import com.mcq.accountsvc.service.AccountService;
import com.mcq.accountsvc.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;
    private final CustomerService customerService;

    @PostMapping("/create-with-customer")
    public ResponseEntity<CustomerAccountResponse> createCustomerWithAccount(
            @Valid @RequestBody CustomerAccountRequest request) {
        CustomerAccountResponse response = accountService.createCustomerWithAccount(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Account> getAccountById(@PathVariable Long id) {
        Account account = accountService.getAccountById(id);
        return ResponseEntity.ok(account);
    }

    @GetMapping("/number/{accountNumber}")
    public ResponseEntity<Account> getAccountByNumber(@PathVariable String accountNumber) {
        Account account = accountService.getAccountByNumber(accountNumber);
        return ResponseEntity.ok(account);
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<Account>> getAccountsByCustomer(@PathVariable Long customerId) {
        Customer customer = customerService.getCustomerById(customerId);
        List<Account> accounts = accountService.getAccountsByCustomer(customer);
        return ResponseEntity.ok(accounts);
    }
}
