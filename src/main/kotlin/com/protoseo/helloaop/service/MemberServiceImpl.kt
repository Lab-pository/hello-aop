package com.protoseo.helloaop.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import com.protoseo.helloaop.entity.Member
import com.protoseo.helloaop.repository.MemberRepository

@Service
class MemberServiceImpl(
    private val memberRepository: MemberRepository
) : MemberServiceInterface {

    @Transactional
    override fun save(): Long {
        val id = memberRepository.save(Member(nickname = "test")).id
        return id!!
    }
}
