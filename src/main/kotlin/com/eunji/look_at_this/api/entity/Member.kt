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
) {
    @Builder
    constructor(
            memberEmail: String,
            memberPassword: String
    ) : this(0, memberEmail, memberPassword)
}