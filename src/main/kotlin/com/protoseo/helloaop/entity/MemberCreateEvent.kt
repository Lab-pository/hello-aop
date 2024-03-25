package com.protoseo.helloaop.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType.IDENTITY
import jakarta.persistence.Id

@Entity
class MemberCreateEvent(
    @Id
    @GeneratedValue(strategy = IDENTITY)
    var id: Long? = null,
    var memberId: Long
) {
}
