package com.eunji.look_at_this.api.entity

import jakarta.persistence.*
import lombok.Builder
import lombok.Getter
import lombok.NoArgsConstructor

@Getter
@NoArgsConstructor
@Entity
data class BookMarkHistory(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val bookMarkHistoryId: Long,

    @ManyToOne
    @JoinColumn(name = "member_id")
    val member: Member? = null,

    @ManyToOne
    @JoinColumn(name = "link_id")
    val link: Link? = null,
) {
    @Builder
    constructor(
        member: Member,
        link: Link
    ) : this(0, member, link)
}