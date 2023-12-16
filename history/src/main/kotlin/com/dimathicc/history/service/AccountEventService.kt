package com.dimathicc.history.service

import com.dimathicc.history.model.AccountEvent
import com.dimathicc.history.repository.AccountEventRepository
import org.springframework.stereotype.Service

@Service
class AccountEventService(private val repository: AccountEventRepository, private val userService: ResolveUserService) {
    fun findAllAccountEvents(accountId: Long): List<AccountEvent> {
        val userId = userService.resolveUserId()
        return repository.findAllByAccountIdAndUserIdOrderByCreatedDesc(accountId, userId)
    }
}