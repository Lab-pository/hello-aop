package com.protoseo.helloaop.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import com.protoseo.helloaop.annotation.Logging
import com.protoseo.helloaop.entity.Member
import com.protoseo.helloaop.entity.MemberCreateEvent
import com.protoseo.helloaop.repository.MemberCreateEventRepository
import com.protoseo.helloaop.repository.MemberRepository

@Service
class MemberServiceV2(
    private val memberCreateEventRepository: MemberCreateEventRepository,
    private val memberRepository: MemberRepository,
) {

    @Transactional
    fun saveMember(): Long {
        val member = memberRepository.save(Member(nickname = "nickname"))
        memberCreateEventRepository.save(MemberCreateEvent(memberId = member.id!!))
        return member.id!!
    }

    @Transactional
    fun saveMemberWithThrowException(): Long {
        val member = memberRepository.save(Member(nickname = "nickname"))
        memberCreateEventRepository.save(MemberCreateEvent(memberId = member.id!!))
        throw IllegalArgumentException()
    }

    @Transactional
    @Logging
    fun saveMemberWithRunningTimeLogging(): Long {
        val member = memberRepository.save(Member(nickname = "nickname"))
        memberCreateEventRepository.save(MemberCreateEvent(memberId = member.id!!))
        return member.id!!
    }
}

