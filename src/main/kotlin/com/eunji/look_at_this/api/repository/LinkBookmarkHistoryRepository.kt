package com.eunji.look_at_this.api.repository

import com.eunji.look_at_this.api.entity.BookMarkHistory
import com.eunji.look_at_this.api.entity.Link
import com.eunji.look_at_this.api.entity.Member
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface LinkBookmarkHistoryRepository : JpaRepository<BookMarkHistory, Long> {
    fun findByMemberAndLink(member: Member, link: Link): BookMarkHistory?
}