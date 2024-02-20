package com.eunji.look_at_this.api.repository

import com.eunji.look_at_this.api.entity.Member
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface MemberRepository : JpaRepository<Member, Long> {
     fun findByMemberEmail(memberEmail: String): Member?
     fun findByMemberBasicToken(memberBasicToken: String): Member?
}