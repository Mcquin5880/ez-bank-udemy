package com.mcq.accountsvc.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

@Configuration
@ConfigurationProperties(prefix = "ez-bank")
@Data
public class SneakyFeesProperties {

    private FeeConfig fees = new FeeConfig();
    private FeeConfig premiumFees = new FeeConfig();

    @Data
    public static class FeeConfig {
        private BigDecimal hidden = new BigDecimal("0.00");
        private BigDecimal monthlyScam = new BigDecimal("0.00");
    }
}
