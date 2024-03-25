package com.protoseo.helloaop.repository

import org.springframework.data.jpa.repository.JpaRepository
import com.protoseo.helloaop.entity.MemberCreateEvent

interface MemberCreateEventRepository : JpaRepository<MemberCreateEvent, Long> {
}
