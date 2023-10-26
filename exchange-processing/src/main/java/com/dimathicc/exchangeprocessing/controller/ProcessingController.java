package com.dimathicc.exchangeprocessing.controller;

import com.dimathicc.exchangeprocessing.dto.ExchangeMoneyDTO;
import com.dimathicc.exchangeprocessing.dto.NewAccountDTO;
import com.dimathicc.exchangeprocessing.dto.PutAccountMoneyDTO;
import com.dimathicc.exchangeprocessing.model.AccountEntity;
import com.dimathicc.exchangeprocessing.service.AccountService;
import com.dimathicc.exchangeprocessing.service.ExchangeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/processing")
@RequiredArgsConstructor
public class ProcessingController {

    private final AccountService accountService;
    private final ExchangeService exchangeService;

    @PostMapping("/account")
    public AccountEntity createAccount(@RequestBody NewAccountDTO account) {
        return accountService.createNewAccount(account);
    }

    @PutMapping("/account/{id}")
    public AccountEntity putMoney(@PathVariable("id") Long accountId, @RequestBody PutAccountMoneyDTO data) {
        return accountService.addMoneyToAccount(data.getUid(), accountId, data.getMoney());
    }

    @PutMapping("/exchange/{uid}")
    public BigDecimal exchange(@PathVariable("uid") String uid, @RequestBody ExchangeMoneyDTO data) {
        return exchangeService.exchangeCurrency(uid, data.getFromAccountId(), data.getToAccountId(), data.getMoney());
    }

    @GetMapping("/accounts/{userId}")
    public List<AccountEntity> getAllAccount(@PathVariable("userId") Long userId) {
        return accountService.getAllAccount(userId);
    }
}
