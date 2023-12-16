package com.dimathicc.history.controller

import com.dimathicc.history.model.AccountEvent
import com.dimathicc.history.repository.AccountEventRepository
import com.dimathicc.history.service.AccountEventService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/history")
class AccountHistoryController(private val historyService: AccountEventService) {
    @GetMapping("/account/{id}")
    fun retrieveAccountHistory(@PathVariable("id") accountId: Long) : List<AccountEvent> =
            historyService.findAllAccountEvents(accountId)
}