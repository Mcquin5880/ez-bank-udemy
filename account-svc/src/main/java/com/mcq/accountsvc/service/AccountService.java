package com.mcq.accountsvc.service;

import com.mcq.accountsvc.config.MessageProperties;
import com.mcq.accountsvc.config.SneakyFeesProperties;
import com.mcq.accountsvc.dto.CustomerAccountRequest;
import com.mcq.accountsvc.dto.CustomerAccountResponse;
import com.mcq.accountsvc.entity.Account;
import com.mcq.accountsvc.entity.Customer;
import com.mcq.accountsvc.exception.ResourceNotFoundException;
import com.mcq.accountsvc.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final CustomerService customerService;
    private final SneakyFeesProperties sneakyFeesProperties;
    private final MessageProperties messageProperties;

    public Account createAccount(Account account) {

        account.setAccountNumber(generateAccountNumber());

        if (account.getHiddenFees() == null) {
            if (account.getAccountType() == Account.AccountType.PREMIUM) {
                account.setHiddenFees(sneakyFeesProperties.getPremiumFees().getHidden());
                account.setMonthlyScamFee(sneakyFeesProperties.getPremiumFees().getMonthlyScam());
            } else {
                account.setHiddenFees(sneakyFeesProperties.getFees().getHidden());
                account.setMonthlyScamFee(sneakyFeesProperties.getFees().getMonthlyScam());
            }
        }

        BigDecimal actualBalance = account.getBalance().subtract(account.getHiddenFees());
        account.setBalance(actualBalance);

        return accountRepository.save(account);
    }

    @Transactional
    public CustomerAccountResponse createCustomerWithAccount(CustomerAccountRequest request) {

        Customer customer = Customer.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .mobileNumber(request.getMobileNumber())
                .dateOfBirth(request.getDateOfBirth())
                .ssn(request.getSsn())
                .hasBeenScammed(true)
                .build();

        Customer savedCustomer = customerService.createCustomer(customer);

        Account account = Account.builder()
                .accountType(request.getAccountType())
                .balance(request.getInitialDeposit())
                .customer(savedCustomer)
                .branchCode(request.getBranchCode())
                .isActive(true)
                .build();

        Account savedAccount = createAccount(account);

        return CustomerAccountResponse.builder()
                .customerId(savedCustomer.getId())
                .firstName(savedCustomer.getFirstName())
                .lastName(savedCustomer.getLastName())
                .email(savedCustomer.getEmail())
                .mobileNumber(savedCustomer.getMobileNumber())
                .accountId(savedAccount.getId())
                .accountNumber(savedAccount.getAccountNumber())
                .accountType(savedAccount.getAccountType())
                .accountDescription(savedAccount.getAccountType().getDescription())
                .balance(savedAccount.getBalance())
                .createdAt(savedAccount.getCreatedAt())
                .message(generateWelcomeMessage(savedCustomer, savedAccount))
                .build();
    }

    public Account getAccountById(Long id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found with id: " + id));
    }

    public List<Account> getAccountsByCustomer(Customer customer) {
        return accountRepository.findByCustomer(customer);
    }

    public Account getAccountByNumber(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found with number: " + accountNumber));
    }

    private String generateAccountNumber() {
        StringBuilder accountNumber = new StringBuilder("EZSCAM");
        Random random = new Random();
        for (int i = 0; i < 8; i++) {
            accountNumber.append(random.nextInt(10));
        }
        return accountNumber.toString();
    }

    private String generateWelcomeMessage(Customer customer, Account account) {
        String template = messageProperties.getWelcomeTemplate();
        return template
                .replace("{firstName}", customer.getFirstName())
                .replace("{accountType}", account.getAccountType().getDescription())
                .replace("{accountNumber}", account.getAccountNumber())
                .replace("{balance}", account.getBalance().toString());
    }
}
