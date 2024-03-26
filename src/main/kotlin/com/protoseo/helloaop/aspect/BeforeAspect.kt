package com.protoseo.helloaop.aspect

import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.slf4j.LoggerFactory
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component

@Aspect
@Component
class BeforeAspect {

    private val log = LoggerFactory.getLogger(this.javaClass)

    @Before("execution(* com.protoseo.helloaop.service.MemberServiceV2.*(..))")
    @Order(1)
    fun beforeOrderOne(joinPoint: JoinPoint) {
        log.info("Before Order 1: ${joinPoint.signature.name}")
    }

    @Before("execution(* com.protoseo.helloaop.service.MemberServiceV2.*(..))")
    @Order(2)
    fun beforeOrderTwo(joinPoint: JoinPoint) {
        log.info("Before Order 2: ${joinPoint.signature.name}")
    }
}
