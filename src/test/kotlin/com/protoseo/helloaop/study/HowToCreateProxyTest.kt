package com.protoseo.helloaop.study

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.aop.support.AopUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import com.protoseo.helloaop.service.MemberService
import com.protoseo.helloaop.service.MemberServiceInterface

@SpringBootTest(properties = ["spring.aop.proxy-target-class=false"])
class HowToCreateProxyTest {

    /***
     * 주석을 해제하고 테스트를 실행하면, 다음과 같은 오류가 발생한다.
     * Caused by: org.springframework.beans.factory.BeanNotOfRequiredTypeException:
     * Bean named 'memberServiceImpl' is expected to be of type 'com.protoseo.helloaop.service.MemberServiceImpl' but was actually of type 'jdk.proxy3.$Proxy113'
     * proxy-target-class=false로 설정된 경우, JDK 동적 프록시를 사용하게 된다.
     * 이 경우, MemberServiceImpl을 주입받기를 원하지만, 실제 들어오는 타입은 JDK Proxy 타입인데, JDK Proxy는 인터페이스를 기반해서 생성되므로 MemberServiceImpl에 대해서 모른다.
     */
//    @Autowired
//    lateinit var memberServiceImpl: MemberServiceImpl

    @Autowired
    lateinit var memberServiceInterface: MemberServiceInterface

    @Autowired
    lateinit var memberService: MemberService

//    @Test
//    fun `Class 로 프록시 객체를 생성하는 경우`() {
//        println(AopUtils.isAopProxy(memberServiceImpl))
//        println(AopUtils.isCglibProxy(memberServiceImpl))
//        println(AopUtils.isJdkDynamicProxy(memberServiceImpl))
//    }

    @Test
    fun `Interface 로 프록시 객체를 생성하는 경우`() {
        // proxy-target-class=false로 설정되었으므로, JDK 동적 프록시로 생성된다.
        println(memberServiceInterface.javaClass)
        assertThat(AopUtils.isAopProxy(memberServiceInterface)).isTrue
        assertThat(AopUtils.isCglibProxy(memberServiceInterface)).isFalse
        assertThat(AopUtils.isJdkDynamicProxy(memberServiceInterface)).isTrue
    }

    @Test
    fun `Interface가 없고 Class만 존재하는 경우 프록시 객체를 생성하는 경우`() {
        // proxy-target-class=false로 설정되어있지만, 인터페이스가 없으므로 JDK 동적 프록시로 생성된다.
        println(memberService.javaClass)
        assertThat(AopUtils.isAopProxy(memberService)).isTrue
        assertThat(AopUtils.isCglibProxy(memberService)).isTrue
        assertThat(AopUtils.isJdkDynamicProxy(memberService)).isFalse
    }
}
