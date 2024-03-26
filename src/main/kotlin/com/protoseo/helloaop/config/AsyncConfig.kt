package com.protoseo.helloaop.config

import java.util.concurrent.Executor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor


@EnableAsync
@Configuration
class AsyncConfig {

    @Bean
    fun taskExecutor(): Executor {
        val executor = ThreadPoolTaskExecutor()
        executor.corePoolSize = 2
        executor.maxPoolSize = 2
        executor.queueCapacity = 500
        executor.setThreadNamePrefix("Async-")
        executor.initialize()
        return executor
    }
}
