package com.dimathicc.exchangeprocessing.service;

import com.dimathicc.exchangeprocessing.dto.NewAccountDTO;
import com.dimathicc.exchangeprocessing.model.AccountEntity;
import com.dimathicc.exchangeprocessing.repository.AccountRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Transactional
    public AccountEntity createNewAccount(NewAccountDTO dto) {
        var account = new AccountEntity();
        account.setCurrencyCode(dto.getCurrencyCode());
        account.setUserId(dto.getUserId());
        account.setBalance(new BigDecimal(0));

        return accountRepository.save(account);
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
    public AccountEntity addMoneyToAccount(String uid, Long accountId, BigDecimal money) {
        Optional<AccountEntity> account =  accountRepository.findById(accountId);
        var result = account.map(acc -> {
            var balance = acc.getBalance().add(money);
            acc.setBalance(balance);
            return accountRepository.save(acc);
        }).orElseThrow(() -> new IllegalArgumentException("Account with id " + accountId + " not found"));
        return result;
    }

    public AccountEntity getAccountById(Long id) {
        return accountRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Account with id " + id + " not found"));
    }

    public List<AccountEntity> getAllAccount(Long userId) {
        return accountRepository.findByUserId(userId);
    }

}
