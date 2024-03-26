package com.protoseo.helloaop.aspect

import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.After
import org.aspectj.lang.annotation.AfterReturning
import org.aspectj.lang.annotation.AfterThrowing
import org.aspectj.lang.annotation.Aspect
import org.slf4j.LoggerFactory
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component

@Aspect
@Component
class AfterAspect {

    private val log = LoggerFactory.getLogger(this.javaClass)

    @After("execution(* com.protoseo.helloaop.service.MemberServiceV2.*(..))")
    @Order(1)
    fun afterOrderOne(joinPoint: JoinPoint) {
        log.info("After Order 1: ${joinPoint.signature.name}")
    }

    @After("execution(* com.protoseo.helloaop.service.MemberServiceV2.*(..))")
    @Order(2)
    fun afterOrderTwo(joinPoint: JoinPoint) {
        log.info("After Order 2: ${joinPoint.signature.name}")
    }

    @AfterReturning("execution(* com.protoseo.helloaop.service.MemberServiceV2.*(..))")
    fun afterReturning(joinPoint: JoinPoint) {
        log.info("AfterReturning: ${joinPoint.signature.name}")
    }

    @AfterThrowing("execution(* com.protoseo.helloaop.service.MemberServiceV2.*(..))")
    fun afterThrowing(joinPoint: JoinPoint) {
        log.info("AfterThrowing: ${joinPoint.signature.name}")
    }

}
