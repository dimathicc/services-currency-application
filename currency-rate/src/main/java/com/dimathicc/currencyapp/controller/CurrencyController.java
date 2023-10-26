package com.dimathicc.currencyapp.controller;

import com.dimathicc.currencyapp.service.CbrService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/currency")
public class CurrencyController {

    private final CbrService currencyService;

    public CurrencyController(CbrService currencyService) {
        this.currencyService = currencyService;
    }

    @GetMapping("/rate/{code}")
    public BigDecimal currencyRate(@PathVariable("code") String code) {
        return currencyService.requestByCurrencyCode(code);
    }
}
