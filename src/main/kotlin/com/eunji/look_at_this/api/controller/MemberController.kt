package com.eunji.look_at_this.api.controller

import com.eunji.look_at_this.api.dto.AlarmDto
import com.eunji.look_at_this.api.dto.MemberDto
import com.eunji.look_at_this.api.service.MemberService
import com.eunji.look_at_this.common.utils.ApiUtil
import lombok.RequiredArgsConstructor
import lombok.extern.slf4j.Slf4j
import org.springframework.web.bind.annotation.*

@Slf4j
@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
class MemberController(
    private val memberService: MemberService
) {
    @PostMapping
    fun signUp(@RequestBody memberDto: MemberDto.MemberReqDto): ApiUtil.ApiResult<MemberDto.MemberBasicTokenResDto?> {
        return ApiUtil.success(memberService.signUp(memberDto))
    }

    @PostMapping("/login")
    fun signIn(@RequestBody memberDto: MemberDto.MemberReqDto): ApiUtil.ApiResult<MemberDto.MemberBasicTokenResDto?> {
        return ApiUtil.success(memberService.signIn(memberDto))
    }

    @PostMapping("/fcm")
    fun postFcmToken(
        @RequestHeader("Authorization") token: String,
        @RequestBody fcmDto: MemberDto.MemberFcmReqDto
    ): ApiUtil.ApiResult<Boolean?> {
        return ApiUtil.success(memberService.postFcmToken(fcmDto, token))
    }

    @PostMapping("/alarm")
    fun postAlarm(
        @RequestHeader("Authorization") token: String,
        @RequestBody alarmReqDto: AlarmDto
    ): ApiUtil.ApiResult<AlarmDto?> {
        return ApiUtil.success(memberService.postAlarm(alarmReqDto, token))
    }

    //개발용
    @GetMapping
    fun getMembers(): ApiUtil.ApiResult<List<MemberDto.MemberResDto?>> {
        return ApiUtil.success(memberService.getMembers())
    }

    @GetMapping("/alarm")
    fun getAlarm(@RequestHeader("Authorization") token: String): ApiUtil.ApiResult<AlarmDto?> {
        return ApiUtil.success(memberService.getAlarm(token))
    }
}
