package com.eunji.look_at_this.common.utils

import com.eunji.look_at_this.api.dto.AlarmTime
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

object DateUtil {

    fun parseStringToTime(time: String): LocalDateTime {
        return when (time) {
            AlarmTime.AM_11.time -> getLocalDateTime(time)
            AlarmTime.PM_15.time -> getLocalDateTime(time)
            AlarmTime.PM_20.time -> getLocalDateTime(time)
            else -> throw IllegalArgumentException("잘못된 시간 형식입니다.")
            //  else -> getLocalDateTime(time)
        }
    }

    private fun getLocalDateTime(time: String): LocalDateTime {
        val currentDate = LocalDateTime.now().toLocalDate()
        val localTime = LocalTime.parse(time, DateTimeFormatter.ofPattern("HH:mm"))
        return LocalDateTime.of(currentDate, localTime)
    }

    fun parseTimeToString(time: LocalDateTime): String {
        return time.format(DateTimeFormatter.ofPattern("HH:mm"))
    }

}