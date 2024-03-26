package com.protoseo.helloaop.service

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import org.springframework.transaction.support.TransactionSynchronizationManager
import com.protoseo.helloaop.entity.Member
import com.protoseo.helloaop.entity.MemberCreateEvent
import com.protoseo.helloaop.repository.MemberCreateEventRepository
import com.protoseo.helloaop.repository.MemberRepository

@Service
class MemberService(
    private val memberCreateEventRepository: MemberCreateEventRepository,
    private val memberCreateEventService: MemberCreateEventService,
    private val memberRepository: MemberRepository,
) {

    private val log = LoggerFactory.getLogger(this.javaClass)

    @Transactional
    fun outerWithExternalCall() {
        log.info("${TransactionSynchronizationManager.getCurrentTransactionName()}")
        val member = memberRepository.save(Member(nickname = "nickname"))
        memberCreateEventService.innerRequiresNew(member.id!!)
    }

    @Transactional
    fun outerWithInternalCall() {
        log.info("${TransactionSynchronizationManager.getCurrentTransactionName()}")
        val member = memberRepository.save(Member(nickname = "nickname"))
        innerRequiresNew(member.id!!)
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    fun innerRequiresNew(memberId: Long) {
        log.info("${TransactionSynchronizationManager.getCurrentTransactionName()}")
        memberCreateEventRepository.save(MemberCreateEvent(memberId = memberId))
    }
}
