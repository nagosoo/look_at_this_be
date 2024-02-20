package com.eunji.look_at_this.api.dto

import lombok.Getter
import lombok.NoArgsConstructor

class FcmDto {
    /**
     * 개발용) 특정 사용자에게 알림을 보내기 위한 요청 DTO
     */
    @Getter
    @NoArgsConstructor
    data class FCMNotificationRequestDtoDev(
        val targetUserId: Long,
        val title: String,
        val body: String
    )

    /**
     * 특정 사용자에게 알림을 보내기 위한 요청 DTO
     */
    @Getter
    @NoArgsConstructor
    data class FCMNotificationRequestDto(
        val fcmToken: String,
        val title: String,
        val body: String
    )

}
