package com.dimathicc.history.repository

import com.dimathicc.history.model.AccountEvent
import com.dimathicc.history.model.EventKey
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AccountEventRepository: JpaRepository<AccountEvent, EventKey> {
    fun findAllByAccountIdAndUserIdOrderByCreatedDesc(accountId: Long, userId: Long?): List<AccountEvent>
}