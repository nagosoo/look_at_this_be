package com.eunji.look_at_this.api.entity

import jakarta.persistence.*
import lombok.Getter
import lombok.NoArgsConstructor

@Getter
@NoArgsConstructor
@Entity
class BookMarkHistory(
        bookMarkHistoryId: Long,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private var bookMarkHistoryMemberId: Long = bookMarkHistoryId

    @OneToOne
    @JoinColumn(name = "member_id")
    private var bookMarkHistoryMember: Member? = null

    @OneToOne
    @JoinColumn(name = "link_id")
    private var bookMarkHistoryLink: Link? = null

}