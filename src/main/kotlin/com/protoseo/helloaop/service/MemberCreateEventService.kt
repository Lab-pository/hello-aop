package com.protoseo.helloaop.service

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation.REQUIRES_NEW
import org.springframework.transaction.annotation.Transactional
import org.springframework.transaction.support.TransactionSynchronizationManager
import com.protoseo.helloaop.entity.MemberCreateEvent
import com.protoseo.helloaop.repository.MemberCreateEventRepository

@Service
class MemberCreateEventService(
    private val memberCreateEventRepository: MemberCreateEventRepository
) {

    private val log = LoggerFactory.getLogger(this.javaClass)

    @Transactional(propagation = REQUIRES_NEW)
    fun innerRequiresNew(memberId: Long) {
        log.info("${TransactionSynchronizationManager.getCurrentTransactionName()}")
        memberCreateEventRepository.save(MemberCreateEvent(memberId = memberId))
    }
}
