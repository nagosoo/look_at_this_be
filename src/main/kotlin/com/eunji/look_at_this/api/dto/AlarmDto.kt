package com.eunji.look_at_this.api.dto

data class AlarmDto(
    val keepReceiveAlarms: Boolean = true,
    val alarmTime: String? = null
)

enum class AlarmTime(val time: String) {
    AM_11("11:00"),
    PM_15("15:00"),
    PM_20("20:00")
}