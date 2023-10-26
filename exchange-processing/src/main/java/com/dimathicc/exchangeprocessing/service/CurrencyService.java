package com.dimathicc.exchangeprocessing.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CurrencyService {

    @Autowired
    private RestTemplate restClient;

    @Value("${service.currency.url}")
    private String currencyUrl;

    public BigDecimal loadCurrencyRate(String code) {
        return restClient.getForObject(currencyUrl + "/currency/rate/{code}", BigDecimal.class, code);
    }
}
