package com.protoseo.helloaop.service

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import com.protoseo.helloaop.repository.MemberRepository

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    lateinit var memberService: MemberService

    @Autowired
    lateinit var memberRepository: MemberRepository

    @Test
    fun `Transactional 메서드 내부 호출 시 테스트`() {
        // 호출하는 트랜잭션의 속성은 `REQUIRE_NEW`로 설정해도 새로운 트랜잭션이 생성되지 않고, 기존 트랜잭션을 따라간다.
        memberService.outerWithInternalCall()
        val findAll = memberRepository.findAll()
        Assertions.assertThat(findAll.size).isEqualTo(1)
    }

    @Test
    fun `Transactional 메서드 외부 호출 시 테스트`() {
        // 정상적으로 새로운 트랜잭션을 생성한다.
        memberService.outerWithExternalCall()
        val findAll = memberRepository.findAll()
        Assertions.assertThat(findAll.size).isEqualTo(1)
    }
}
