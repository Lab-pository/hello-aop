package com.protoseo.helloaop.entity

import java.time.LocalDateTime

class RegisterEvent(
    val id: Long,
    val eventName: String,
    val createdAt: LocalDateTime,
) {
}
