package com.eunji.look_at_this.api.entity

import jakarta.persistence.*
import lombok.Builder
import lombok.NoArgsConstructor
import java.time.LocalDateTime


@NoArgsConstructor
@Entity
data class Link(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val linkId: Long,
    @Column(nullable = false)
    val linkUrl: String,
    @Column(nullable = true)
    val linkMemo: String,
    @Column(nullable = false)
    val linkThumbnail: String,
    @Column(nullable = false)
    val linkCreatedAt: LocalDateTime,
    @OneToOne(mappedBy = "linkClickHistoryLink")
    val linkClickHistory: LinkClickHistory? = null,
    @OneToOne(mappedBy = "bookMarkHistoryLink")
    val bookMarkHistory: BookMarkHistory? = null,
) {
    @Builder
    constructor(
        linkUrl: String,
        linkMemo: String,
        linkThumbnail: String,
        linkCreatedAt: LocalDateTime,
    ) : this(0, linkUrl, linkMemo, linkThumbnail, linkCreatedAt)
}