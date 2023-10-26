package com.dimathicc.currencyapp;

import com.dimathicc.currencyapp.config.CurrencyClientConfig;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

@SpringBootApplication
@EnableConfigurationProperties(CurrencyClientConfig.class)
public class CurrencyAppApplication {


    public static void main(String[] args) {
        SpringApplication.run(CurrencyAppApplication.class, args);
    }

}
