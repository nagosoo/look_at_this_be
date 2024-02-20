package com.eunji.look_at_this

import com.eunji.look_at_this.api.dto.AlarmTime
import com.eunji.look_at_this.api.dto.FcmDto
import com.eunji.look_at_this.api.repository.MemberRepository
import com.eunji.look_at_this.api.service.FCMNotificationService
import com.eunji.look_at_this.common.utils.DateUtil
import lombok.RequiredArgsConstructor
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component


@Component
@RequiredArgsConstructor
class FcmScheduler(
    private val memberRepository: MemberRepository,
    private val fcmNotificationService: FCMNotificationService,
) {

    @Scheduled(cron = "0 0 11 * * *")
    fun sendFcmAm11() {
        getMemberByTime(AlarmTime.AM_11.time)
    }

    @Scheduled(cron = "0 0 15 * * *")
    fun sendFcmPm15() {
        getMemberByTime(AlarmTime.PM_15.time)
    }

    @Scheduled(cron = "0 0 20 * * *")
    fun sendFcmPm20() {
        getMemberByTime(AlarmTime.PM_20.time)
    }

//    @Scheduled(cron = "0 59 16 * * *")
//    fun sendFcmtest() {
//        getMemberByTime("16:59")
//    }

    private fun getMemberByTime(time: String) {
        memberRepository.findAll().filter {
            !it.keepReceiveAlarms && DateUtil.parseTimeToString(it.alarmTime) == time
        }.map {
            it.memberFcmToken
        }.forEach { fcmToken ->
            fcmToken?.let {
                fcmNotificationService.sendNotification(
                    FcmDto.FCMNotificationRequestDto(
                        fcmToken = fcmToken,
                        title = Constance.FCM_TITLE,
                        body = Constance.FCM_BODY,
                    )
                )
            }
        }
    }
}