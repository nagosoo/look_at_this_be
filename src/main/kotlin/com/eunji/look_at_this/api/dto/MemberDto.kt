package com.eunji.look_at_this.api.dto

import lombok.Builder
import lombok.Getter
import lombok.NoArgsConstructor

class MemberDto {
    @Getter
    @NoArgsConstructor
    data class MemberReqDto @Builder constructor(val memberEmail: String, val memberPassword: String)

    @Getter
    @NoArgsConstructor
    data class MemberFcmReqDto @Builder constructor(val fcmToken: String)

    @Getter
    @NoArgsConstructor
    data class MemberResDto @Builder constructor(
        val memberId: Long,
        val memberEmail: String,
        val memberPassword: String,
        val memberFcmToken: String? = null,
        val memberAlarmSetting: AlarmDto,
        val memberBasicToken: String
    )

    @Getter
    @NoArgsConstructor
    data class MemberBasicTokenResDto @Builder constructor(
        val memberBasicToken: String
    )
}