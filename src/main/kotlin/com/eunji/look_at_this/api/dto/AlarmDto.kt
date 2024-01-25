package com.eunji.look_at_this.api.dto

import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

data class AlarmDto(
    val keepReceiveAlarms: Boolean = true,
    val alarmTime: String = LocalDateTime.now(ZoneId.of("Asia/Seoul")).format(DateTimeFormatter.ofPattern("HH:mm"))
)
