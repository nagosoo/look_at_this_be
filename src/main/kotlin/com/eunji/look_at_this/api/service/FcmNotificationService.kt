package com.eunji.look_at_this.api.service

import com.eunji.look_at_this.Constance.CHANNEL_ID
import com.eunji.look_at_this.api.dto.FcmDto
import com.eunji.look_at_this.api.repository.MemberRepository
import com.google.firebase.messaging.*
import lombok.RequiredArgsConstructor
import lombok.extern.slf4j.Slf4j
import org.springframework.stereotype.Service

@RequiredArgsConstructor
@Slf4j
@Service
class FCMNotificationService(
    private val firebaseMessaging: FirebaseMessaging,
    private val memberRepository: MemberRepository,
) {

    /*
       * FcmToken을 통해 특정 사용자에게 알림을 보냄
       * */
    fun sendNotification(requestDto: FcmDto.FCMNotificationRequestDto): String {

        val notification: Notification = Notification.builder()
            .setTitle(requestDto.title)
            .setBody(requestDto.body)
            .build()

        val message: Message = Message.builder()
            .setToken(requestDto.fcmToken)
            .setNotification(notification)
            .setAndroidConfig(
                AndroidConfig.builder()
                    .setPriority(AndroidConfig.Priority.HIGH)
                    .setNotification(
                        AndroidNotification.builder()
                            .setChannelId(CHANNEL_ID)
                            .build()
                    )
                    .build()
            )
            .build()

        try {
            firebaseMessaging.send(message)
            return "알림을 성공적으로 전송했습니다. targetUserId=" + requestDto.fcmToken
        } catch (e: FirebaseMessagingException) {
            e.printStackTrace()
            return "알림 보내기를 실패하였습니다. targetUserId=" + requestDto.fcmToken
        }

    }

    /*
    * 개발용) MemberId 를 통해 특정 사용자에게 알림을 보냄
    * */
    fun sendNotificationForDev(requestDto: FcmDto.FCMNotificationRequestDtoDev): String {

        val member = memberRepository.findById(requestDto.targetUserId)

        if (!member.isEmpty) {
            val notification: Notification = Notification.builder()
                .setTitle(requestDto.title)
                .setBody(requestDto.body)
                .build()

            val fcmToken = member.get().memberFcmToken

            val message: Message = Message.builder()
                .setToken(fcmToken)
                .setNotification(notification)
                .setAndroidConfig(
                    AndroidConfig.builder()
                        .setPriority(AndroidConfig.Priority.HIGH)
                        .setNotification(
                            AndroidNotification.builder()
                                .setChannelId(CHANNEL_ID)
                                .build()
                        )
                        .build()
                )
                .build()

            try {
                firebaseMessaging.send(message)
                return "알림을 성공적으로 전송했습니다. targetUserId=" + requestDto.targetUserId
            } catch (e: FirebaseMessagingException) {
                e.printStackTrace()
                return "알림 보내기를 실패하였습니다. targetUserId=" + requestDto.targetUserId
            }
        } else {
            return "해당 아이디 유저가 존재 하지 않습니다. targetUserId= " + requestDto.targetUserId
        }
    }
}