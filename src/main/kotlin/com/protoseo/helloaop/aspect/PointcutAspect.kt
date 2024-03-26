package com.protoseo.helloaop.aspect

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Aspect
@Component
class PointcutAspect {

    private val log = LoggerFactory.getLogger(this.javaClass)

    @Pointcut("@annotation(com.protoseo.helloaop.annotation.Logging)")
    fun loggingAnnotation() {}

    @Around("loggingAnnotation()")
    fun methodRunningTimeLog(joinPoint: ProceedingJoinPoint): Any? {
        val start = System.currentTimeMillis()
        val result = joinPoint.proceed()
        val end = System.currentTimeMillis()
        log.info("Running Time: {}ms", end - start)
        return result
    }
}
