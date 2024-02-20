package com.eunji.look_at_this.api.service

import com.eunji.look_at_this.api.dto.AlarmDto
import com.eunji.look_at_this.api.dto.MemberDto

interface MemberService {
    fun signUp(memberReqDto: MemberDto.MemberReqDto): MemberDto.MemberBasicTokenResDto?
    fun signIn(memberReqDto: MemberDto.MemberReqDto): MemberDto.MemberBasicTokenResDto?
    fun getMembers(): List<MemberDto.MemberResDto?>
    fun postFcmToken(fcmReqDto: MemberDto.MemberFcmReqDto, token: String): Boolean?
    fun postAlarm(alarmReqDto: AlarmDto, token: String): AlarmDto?
    fun getAlarm(token: String): AlarmDto?
}