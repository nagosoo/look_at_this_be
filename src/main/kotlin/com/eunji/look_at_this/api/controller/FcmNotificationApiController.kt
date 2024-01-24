package com.eunji.look_at_this.api.controller

import com.eunji.look_at_this.api.dto.FCMNotificationRequestDto
import com.eunji.look_at_this.api.service.FCMNotificationService
import lombok.RequiredArgsConstructor
import lombok.extern.slf4j.Slf4j
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/notification")
class FCMNotificationApiController(
    private val fcmNotificationService: FCMNotificationService
) {

    @PostMapping
    fun sendNotificationByToken(@RequestBody requestDto: FCMNotificationRequestDto): String {
        return fcmNotificationService.sendNotificationByToken(requestDto)
    }
}