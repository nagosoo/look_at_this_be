package com.eunji.look_at_this.api.dto

import lombok.Getter
import lombok.NoArgsConstructor



class FcmDto {
    @Getter
    @NoArgsConstructor
    data class FCMNotificationRequestDtoDev(
        val targetUserId: Long,
        val title: String,
        val body: String
    )

    @Getter
    @NoArgsConstructor
    data class FCMNotificationRequestDto(
        val fcmToken: String,
        val title: String,
        val body: String
    )
}
