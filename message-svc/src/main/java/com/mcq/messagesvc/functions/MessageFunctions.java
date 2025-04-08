package com.mcq.messagesvc.functions;

import com.mcq.messagesvc.dto.AccountMsgDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Function;

@Configuration
public class MessageFunctions {

    private static final Logger log = LoggerFactory.getLogger(MessageFunctions.class);

    @Bean
    public Function<AccountMsgDto, AccountMsgDto> email() {
        return accountMsgDto -> {
            log.info("Sending email with the following details: {}", accountMsgDto);
            return accountMsgDto;
        };
    }

    @Bean
    public Function<AccountMsgDto, String> sms() {
        return accountMsgDto -> {
            log.info("Sending sms with the following details: {}", accountMsgDto);
            return accountMsgDto.accountNumber();
        };
    }
}
