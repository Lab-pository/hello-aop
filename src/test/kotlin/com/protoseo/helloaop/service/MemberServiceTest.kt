package com.protoseo.helloaop.service

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import com.protoseo.helloaop.repository.MemberCreateEventRepository
import com.protoseo.helloaop.repository.MemberRepository

@SpringBootTest
class MemberServiceTest {

    @Autowired
    lateinit var memberService: MemberService

    @Autowired
    lateinit var memberRepository: MemberRepository

    @Autowired
    lateinit var memberCreateEventRepository: MemberCreateEventRepository

    @BeforeEach
    fun setUp() {
        memberRepository.deleteAll()
        memberCreateEventRepository.deleteAll()
    }

    @Test
    fun `Transactional 메서드에서 내부 호출 시 테스트`() {
        // 호출하는 트랜잭션의 속성은 `REQUIRE_NEW`로 설정해도 새로운 트랜잭션이 생성되지 않고, 기존 트랜잭션을 따라간다.
        memberService.outerWithInternalCall()

        val members = memberRepository.findAll()
        val memberCreateEvents = memberCreateEventRepository.findAll()

        Assertions.assertThat(members.size).isEqualTo(1)
        Assertions.assertThat(memberCreateEvents.size).isEqualTo(1)
    }

    @Test
    fun `Transactional 메서드에서 외부 호출 시 테스트`() {
        // 정상적으로 새로운 트랜잭션을 생성한다.
        memberService.outerWithExternalCall()
        val members = memberRepository.findAll()
        val memberCreateEvents = memberCreateEventRepository.findAll()

        Assertions.assertThat(members.size).isEqualTo(1)
        Assertions.assertThat(memberCreateEvents.size).isEqualTo(1)
    }

    @Test
    fun `Transactional 메서드에서 내부 비동기 메서드 호출 시 테스트`() {
        // 비동기 메서드로 호출되지 않고, 같은 스레드에서 동작한다.
        memberService.outerWithInternalAsyncCall()

        val members = memberRepository.findAll()
        val memberCreateEvents = memberCreateEventRepository.findAll()

        Assertions.assertThat(members.size).isEqualTo(1)
        Assertions.assertThat(memberCreateEvents.size).isEqualTo(1)
    }

    @Test
    fun `Transactional 메서드에서 외부 비동기 메서드 호출 시 테스트`() {
        // 정상적으로 비동기 메서드를 호출해서 실행한다.
        memberService.outerWithExternalAsyncCall()
        Thread.sleep(1000)

        val members = memberRepository.findAll()
        val memberCreateEvents = memberCreateEventRepository.findAll()

        Assertions.assertThat(members.size).isEqualTo(1)
        Assertions.assertThat(memberCreateEvents.size).isEqualTo(1)
    }
}
