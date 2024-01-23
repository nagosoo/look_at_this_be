package com.eunji.look_at_this.api.entity

import jakarta.persistence.*
import lombok.Builder
import lombok.Getter
import lombok.NoArgsConstructor

@Getter
@NoArgsConstructor
@Entity
data class LinkClickHistory(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val linkClickHistoryId: Long,

    @ManyToOne
    @JoinColumn(name = "member_id")
    val linkClickHistoryMember: Member? = null,

    @ManyToOne
    @JoinColumn(name = "link_id")
    val linkClickHistoryLink: Link? = null,
) {
    @Builder
    constructor(
        linkClickHistoryMember: Member,
        linkClickHistoryLink: Link
    ) : this(0, linkClickHistoryMember, linkClickHistoryLink)
}