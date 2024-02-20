package com.eunji.look_at_this.api.dto

import lombok.Builder
import lombok.Getter
import lombok.NoArgsConstructor

class MemberDto {

    /*
    * 회원가입 요청 DTO
    * */
    @Getter
    @NoArgsConstructor
    data class MemberReqDto @Builder constructor(val memberEmail: String, val memberPassword: String)

    /*
    * FCMToken 등록 DTO
     */
    @Getter
    @NoArgsConstructor
    data class MemberFcmReqDto @Builder constructor(val fcmToken: String)


    /*
    * 개발용) 회원 조회 응답 DTO
     */
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

    /*
    * 로그인 응답 DTO
     */
    @Getter
    @NoArgsConstructor
    data class MemberBasicTokenResDto @Builder constructor(
        val memberBasicToken: String
    )
}