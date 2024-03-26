package com.protoseo.helloaop.repository

import org.springframework.stereotype.Repository
import com.protoseo.helloaop.entity.RegisterEvent

@Repository
class RegisterEventRepository {
    private val database: HashMap<Long, RegisterEvent> = HashMap()

    fun save(registerEvent: RegisterEvent): RegisterEvent {
        Thread.sleep(100L)
        database[registerEvent.id] = registerEvent
        return registerEvent
    }
}
