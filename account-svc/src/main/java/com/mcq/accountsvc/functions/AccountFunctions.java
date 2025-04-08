package com.mcq.accountsvc.functions;

import com.mcq.accountsvc.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class AccountFunctions {

    private final AccountService accountService;

    @Bean
    public Consumer<String> updateCommunication() {
        return accountNumber -> {
            log.info("Updating communication status for account number: {}", accountNumber);
            accountService.updateCommunicationStatus(accountNumber);
        };
    }
}
