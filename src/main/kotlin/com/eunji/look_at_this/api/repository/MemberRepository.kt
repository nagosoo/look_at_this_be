package com.eunji.look_at_this.api.repository

import com.eunji.look_at_this.api.entity.Member
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface MemberRepository : JpaRepository<Member, Long> {
     fun getMembersByMemberEmail(memberEmail: String): Optional<Member>
     fun findByMemberEmail(memberEmail: String): Optional<Member>
     fun findByMemberBasicToken(memberBasicToken: String): Optional<Member>
}