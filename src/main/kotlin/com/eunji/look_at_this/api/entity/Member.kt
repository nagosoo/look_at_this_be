package com.eunji.look_at_this.api.entity

import jakarta.persistence.*
import lombok.Builder
import lombok.Getter
import lombok.NoArgsConstructor

@Getter
@NoArgsConstructor
@Entity
class Member(
        memberId: Long,
        memberEmail: String,
        memberPassword: String,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private var memberId: Long = memberId

    @Column(nullable = false)
    private var memberEmail: String = memberEmail

    @Column(nullable = false)
    private var memberPassword: String = memberPassword

    @Builder
    constructor(
            memberEmail: String,
            memberPassword: String,
    ) : this(0, memberEmail, memberPassword)
}