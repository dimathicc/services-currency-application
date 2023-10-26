package com.dimathicc.exchangeprocessing.service;

import com.dimathicc.exchangeprocessing.model.AccountEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
@Slf4j
@RequiredArgsConstructor
public class ExchangeService {
    public static final String CURRENCY_RUB = "RUB";

    private final AccountService accountService;
    private final CurrencyService currencyService;

    @Lazy
    @Autowired
    private ExchangeService exchangeService;

    public BigDecimal exchangeCurrency(String uuid, Long fromAccount, Long toAccount, BigDecimal amount) {
        AccountEntity source = accountService.getAccountById(fromAccount);
        AccountEntity target = accountService.getAccountById(toAccount);

        log.info("Exchange operation {} from account {} to account {} started.", uuid, fromAccount, toAccount);

        BigDecimal result;
        if (!CURRENCY_RUB.equals(source.getCurrencyCode()) && CURRENCY_RUB.equals(target.getCurrencyCode())) {
            BigDecimal rate = currencyService.loadCurrencyRate(source.getCurrencyCode());
            result = exchangeService.exchangeWithMultiply(uuid, source, target, rate, amount);
        } else if (CURRENCY_RUB.equals(source.getCurrencyCode()) && !CURRENCY_RUB.equals(target.getCurrencyCode())) {
            BigDecimal rate = currencyService.loadCurrencyRate(target.getCurrencyCode());
            BigDecimal multiplier = new BigDecimal(1).divide(rate, 4, RoundingMode.HALF_DOWN);
            result = exchangeService.exchangeWithMultiply(uuid, source, target, multiplier, amount);
        } else if (!CURRENCY_RUB.equals(source.getCurrencyCode()) && !CURRENCY_RUB.equals(target.getCurrencyCode())) {
            BigDecimal rateFrom = currencyService.loadCurrencyRate(source.getCurrencyCode());
            BigDecimal rateTo = currencyService.loadCurrencyRate(target.getCurrencyCode());
            result = exchangeThroughRub(uuid, source, target, rateFrom, rateTo, amount);
        } else if (CURRENCY_RUB.equals(source.getCurrencyCode()) && CURRENCY_RUB.equals(target.getCurrencyCode())) {
            result = simpleExchange(uuid, source, target, amount);
        } else {
            throw new IllegalStateException("Unknown behavior");
        }

        log.info("Exchange operation {} from account {} to account {} completed.", uuid, fromAccount, toAccount);
        return result;
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public BigDecimal exchangeWithMultiply(String uuid, AccountEntity source, AccountEntity target, BigDecimal rate, BigDecimal amount) {
        accountService.addMoneyToAccount(uuid, source.getId(), amount.negate());
        BigDecimal result = amount.multiply(rate);
        accountService.addMoneyToAccount(uuid, target.getId(), result);
        return result;
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public BigDecimal exchangeThroughRub(String uuid, AccountEntity source, AccountEntity target, BigDecimal rateFrom, BigDecimal rateTo, BigDecimal amount) {
        accountService.addMoneyToAccount(uuid, source.getId(), amount.negate());
        BigDecimal rub = amount.multiply(rateFrom);
        BigDecimal result = rub.divide(rateTo, 4, RoundingMode.HALF_DOWN);
        accountService.addMoneyToAccount(uuid, target.getId(), result);
        return result;
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public BigDecimal simpleExchange(String uuid, AccountEntity source, AccountEntity target, BigDecimal amount) {
        accountService.addMoneyToAccount(uuid, source.getId(), amount.negate());
        accountService.addMoneyToAccount(uuid, target.getId(), amount);
        return amount;
    }

}