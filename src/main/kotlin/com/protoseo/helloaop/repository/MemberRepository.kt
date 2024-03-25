package com.protoseo.helloaop.repository

import org.springframework.data.jpa.repository.JpaRepository
import com.protoseo.helloaop.entity.Member

interface MemberRepository : JpaRepository<Member, Long> {
}
