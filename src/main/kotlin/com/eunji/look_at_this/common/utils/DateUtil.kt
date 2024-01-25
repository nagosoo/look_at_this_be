package com.eunji.look_at_this.common.utils

import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

object DateUtil {

    fun parseStringToTime(time: String): LocalDateTime {
        val currentDate = LocalDateTime.now().toLocalDate()
        try {
            val localTime = LocalTime.parse(time, DateTimeFormatter.ofPattern("HH:mm"))
            return LocalDateTime.of(currentDate, localTime)
        } catch (e: Exception) {
            return LocalDateTime.of(currentDate, LocalTime.of(21, 0))
        }
    }

    fun parseTimeToString(time: LocalDateTime): String {
        return time.format(DateTimeFormatter.ofPattern("HH:mm"))
    }

}