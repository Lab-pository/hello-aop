package com.protoseo.helloaop.service

import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.transaction.support.TransactionSynchronizationManager
import com.protoseo.helloaop.entity.MemberCreateEvent
import com.protoseo.helloaop.repository.MemberCreateEventRepository

@Service
class MemberCreateEventAsyncService(
    private val memberCreateEventRepository: MemberCreateEventRepository
) {

    private val log = LoggerFactory.getLogger(this.javaClass)

    @Async
    @Transactional
    fun innerAsync(memberId: Long) {
        log.info("${TransactionSynchronizationManager.getCurrentTransactionName()}")
        log.info(Thread.currentThread().name)
        memberCreateEventRepository.save(MemberCreateEvent(memberId = memberId))
    }
}
