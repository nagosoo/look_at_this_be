package com.eunji.look_at_this.api.dto

import lombok.Getter
import lombok.NoArgsConstructor


@Getter
@NoArgsConstructor
data class FCMNotificationRequestDto(
    val targetUserId: Long,
    val title: String,
    val body: String
)