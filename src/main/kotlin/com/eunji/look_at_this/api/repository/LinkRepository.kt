package com.eunji.look_at_this.api.repository

import com.eunji.look_at_this.api.entity.Link
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository


@Repository
interface LinkRepository : JpaRepository<Link, Long> {
   fun findAllByOrderByLinkIdDesc(pageable: Pageable): List<Link>
   fun findByLinkIdLessThanOrderByLinkIdDesc(cursor: Long, pageable: Pageable): List<Link>
   fun existsByLinkIdLessThan(id: Long): Boolean
}
