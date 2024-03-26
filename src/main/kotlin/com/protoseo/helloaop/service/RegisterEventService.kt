package com.protoseo.helloaop.service

import java.time.LocalDateTime
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.locks.ReentrantLock
import org.springframework.stereotype.Service
import com.protoseo.helloaop.entity.RegisterEvent
import com.protoseo.helloaop.exception.DuplicateRegisterException
import com.protoseo.helloaop.repository.RegisterEventRepository

@Service
class RegisterEventService(private val registerEventRepository: RegisterEventRepository) {

    private val locks = ConcurrentHashMap<Long, ReentrantLock>()

    fun saveRegisterEvent(id: Long, name: String): Long {
        val lock = locks.computeIfAbsent(id) { ReentrantLock() }
        if (lock.tryLock()) {
            try {
                val save = registerEventRepository.save(RegisterEvent(id, name, LocalDateTime.now()))
                return save.id
            } finally {
                lock.unlock()
            }
        }
        throw DuplicateRegisterException()
    }
}
