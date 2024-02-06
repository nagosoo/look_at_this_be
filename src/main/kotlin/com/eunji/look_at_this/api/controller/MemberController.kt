package com.eunji.look_at_this.api.controller

import com.eunji.look_at_this.api.dto.AlarmDto
import com.eunji.look_at_this.api.dto.MemberDto
import com.eunji.look_at_this.api.service.MemberService
import com.eunji.look_at_this.common.utils.ApiUtils
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
    fun create(@RequestBody createReq: MemberDto.MemberReqDto): ApiUtils.ApiResult<MemberDto.MemberBasicTokenResDto?> {
        return ApiUtils.success(memberService.createMember(createReq))
    }

    @PostMapping("/login")
    fun logIn(@RequestBody createReq: MemberDto.MemberReqDto): ApiUtils.ApiResult<MemberDto.MemberBasicTokenResDto?> {
        return ApiUtils.success(memberService.logIn(createReq))
    }

    @PostMapping("/fcm")
    fun postFcmToken(@RequestHeader("Authorization") token: String, @RequestBody createReq: MemberDto.MemberFcmReqDto): ApiUtils.ApiResult<Long?> {
        return ApiUtils.success(memberService.postFcmToken(createReq, token))
    }

    @PostMapping("/alarm")
    fun postAlarm(@RequestHeader("Authorization") token: String, @RequestBody postAlarmReqDto:AlarmDto): ApiUtils.ApiResult<AlarmDto?> {
        return ApiUtils.success(memberService.postAlarm(postAlarmReqDto,token))
    }

    @GetMapping
    fun getMemberList(): ApiUtils.ApiResult<List<MemberDto.MemberResDto?>> {
        return ApiUtils.success(memberService.getMemberList())
    }

    @GetMapping("/alarm")
    fun getAlarmSetting(@RequestHeader("Authorization") token: String): ApiUtils.ApiResult<AlarmDto?> {
        return ApiUtils.success(memberService.getAlarm(token))
    }
}
