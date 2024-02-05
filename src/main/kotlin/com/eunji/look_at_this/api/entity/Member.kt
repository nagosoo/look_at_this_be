package com.eunji.look_at_this.api.entity

import jakarta.persistence.*
import lombok.Builder
import lombok.NoArgsConstructor
import java.time.LocalDateTime
import java.time.LocalTime

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
    @Column(nullable = false)
    val memberBasicToken: String,
    @Column(nullable = true)
    val memberFcmToken: String? = null,
    @Column(nullable = false)
    val keepReceiveAlarms: Boolean = true,
    @Column(nullable = false)
    val alarmTime: LocalDateTime = LocalDateTime.of(LocalDateTime.now().toLocalDate(), LocalTime.of(21, 0)),
    @OneToMany(mappedBy = "member")
    val linkClickHistories: MutableList<LinkClickHistory> = mutableListOf(),
    @OneToMany(mappedBy = "member")
    val bookMarkHistories: MutableList<BookMarkHistory> = mutableListOf()
) {
    @Builder
    constructor(
        memberEmail: String,
        memberPassword: String,
        memberBasicToken: String
    ) : this(0, memberEmail, memberPassword, memberBasicToken)
}