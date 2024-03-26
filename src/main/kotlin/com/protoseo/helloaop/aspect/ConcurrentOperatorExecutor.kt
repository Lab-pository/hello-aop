package com.protoseo.helloaop.aspect

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.slf4j.LoggerFactory
import org.springframework.core.Ordered
import org.springframework.stereotype.Component
import com.protoseo.helloaop.exception.DuplicateRegisterException

@Aspect
@Component
class ConcurrentOperatorExecutor : Ordered {

    companion object {
        private const val DEFAULT_MAX_RETRIES = 5
    }

    private val log = LoggerFactory.getLogger(this.javaClass)
    private var order = 1
    private var maxRetries = DEFAULT_MAX_RETRIES

    fun setMaxRetries(maxRetries: Int) {
        this.maxRetries = maxRetries
    }

    override fun getOrder(): Int {
        return this.order
    }

    fun setOrder(order: Int) {
        this.order = order
    }

    @Around("execution(* com.protoseo.helloaop.service.RegisterEventService.*(..))")
    fun doConcurrentOperation(joinPoint: ProceedingJoinPoint): Any? {
        var numAttempts = 0
        var exception: DuplicateRegisterException
        do {
            numAttempts++
            log.info(Thread.currentThread().name + " " + numAttempts)
            try {
                return joinPoint.proceed()
            } catch (ex: DuplicateRegisterException) {
                exception = ex
                Thread.sleep(100L)
            }
        } while (numAttempts <= this.maxRetries)
        throw exception
    }
}
