package com.eunji.look_at_this.api.repository

import com.eunji.look_at_this.api.entity.Link
import com.eunji.look_at_this.api.entity.LinkClickHistory
import com.eunji.look_at_this.api.entity.Member
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface LinkHistoryRepository : JpaRepository<LinkClickHistory, Long> {
    fun findByMemberAndLink(member: Member, link: Link): LinkClickHistory?
}