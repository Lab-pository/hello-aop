package com.protoseo.helloaop.service

import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors
import java.util.concurrent.atomic.AtomicInteger
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class RegisterEventServiceTest {

    @Autowired
    lateinit var registerEventService: RegisterEventService

    @Test
    fun `Lock으로 동시 요청이 막혀져 있는 경우, 5번까지 재시도를 진행한다`() {
        val count = 5
        val executorService = Executors.newFixedThreadPool(16)
        val latch = CountDownLatch(count)

        val successCount = AtomicInteger(0)
        val failCount = AtomicInteger(0)

        for (i in 0 until count) {
            executorService.submit {
                try {
                    registerEventService.saveRegisterEvent(1L, "CreateMember-$i")
                    successCount.getAndIncrement()
                } catch (e: RuntimeException) {
                    failCount.getAndIncrement()
                } finally {
                    latch.countDown()
                }
            }
        }
        latch.await()

        Assertions.assertEquals(count, successCount.get())
        Assertions.assertEquals(0, failCount.get())
    }

}
