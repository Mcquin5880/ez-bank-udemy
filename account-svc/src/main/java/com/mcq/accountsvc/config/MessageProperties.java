package com.mcq.accountsvc.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "ez-bank.messages")
@Data
public class MessageProperties {

    private String welcomeTemplate;
}
