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
    override val member: Member? = null,

    @ManyToOne
    @JoinColumn(name = "link_id")
    override val link: Link? = null,
) : LinkHistory {
    @Builder
    constructor(
        member: Member,
        link: Link
    ) : this(0, member, link)

}

interface LinkHistory {
    val member: Member?
    val link: Link?
}