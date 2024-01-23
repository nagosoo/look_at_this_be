package com.eunji.look_at_this.api.entity

import jakarta.persistence.*
import lombok.Builder
import lombok.NoArgsConstructor

@NoArgsConstructor
@Entity
data class Member(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val memberId: Long,
        @Column(nullable = false)
        val memberEmail: String,
        @Column(nullable = false)
        val memberPassword: String,
        @OneToMany(mappedBy = "linkClickHistoryMember")
        val linkClickHistories: MutableList<LinkClickHistory> = mutableListOf(),
        @OneToMany(mappedBy = "bookMarkHistoryMember")
        val bookMarkHistories: MutableList<BookMarkHistory> = mutableListOf()
) {
    @Builder
    constructor(
            memberEmail: String,
            memberPassword: String
    ) : this(0, memberEmail, memberPassword)
}