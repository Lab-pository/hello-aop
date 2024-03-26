package com.protoseo.helloaop.service

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import com.protoseo.helloaop.repository.MemberCreateEventRepository
import com.protoseo.helloaop.repository.MemberRepository

@SpringBootTest
class MemberServiceV2Test {

    @Autowired
    lateinit var memberServiceV2: MemberServiceV2

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
    fun `정상적으로 동작하는 경우`() {
        // 1. Around Before
        // 2. Before Order(1)
        // 3. Before Order(2)
        // 4. Run
        // 5. Around After
        // 6. After Returning
        // 7. After Order(2)
        // 8. After Order(1)
        memberServiceV2.saveMember()

        val members = memberRepository.findAll()
        val memberCreateEvents = memberCreateEventRepository.findAll()

        Assertions.assertThat(members.size).isEqualTo(1)
        Assertions.assertThat(memberCreateEvents.size).isEqualTo(1)
    }

    @Test
    fun `예외를 발생시키는 경우`() {
        // 1. Around Before
        // 2. Before Order(1)
        // 3. Before Order(2)
        // 4. Run
        // 5. After Throwing
        // 6. After Order(2)
        // 7. After Order(1)
        Assertions.assertThatThrownBy { memberServiceV2.saveMemberWithThrowException() }
            .isInstanceOf(IllegalArgumentException::class.java)
    }

    @Test
    fun `Logging 애노테이션 동작 확인`() {
        memberServiceV2.saveMemberWithRunningTimeLogging()

        val members = memberRepository.findAll()
        val memberCreateEvents = memberCreateEventRepository.findAll()

        Assertions.assertThat(members.size).isEqualTo(1)
        Assertions.assertThat(memberCreateEvents.size).isEqualTo(1)
    }
}
