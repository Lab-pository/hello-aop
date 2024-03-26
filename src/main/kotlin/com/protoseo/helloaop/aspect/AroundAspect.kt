package com.protoseo.helloaop.aspect

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Aspect
@Component
class AroundAspect {

    private val log = LoggerFactory.getLogger(this.javaClass)

    @Around("execution(* com.protoseo.helloaop.service.MemberServiceV2.*(..))")
    fun around(joinPoint: ProceedingJoinPoint): Any? {
        log.info("Around Before: ${joinPoint.signature.name}")
        val id = joinPoint.proceed()
        log.info("Around After: ${joinPoint.signature.name}")
        return id
    }
}
