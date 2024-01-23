package com.eunji.look_at_this.api.repository

import com.eunji.look_at_this.api.entity.Link
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface LinkRepository : JpaRepository<Link, Long> {
}
