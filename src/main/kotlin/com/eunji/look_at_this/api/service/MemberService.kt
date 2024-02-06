package com.eunji.look_at_this.api.service

import com.eunji.look_at_this.api.dto.AlarmDto
import com.eunji.look_at_this.api.dto.MemberDto

interface MemberService {
    fun createMember(memberReqDto: MemberDto.MemberReqDto): MemberDto.MemberBasicTokenResDto?
    fun logIn(memberReqDto: MemberDto.MemberReqDto): MemberDto.MemberBasicTokenResDto?
    fun getMemberList(): List<MemberDto.MemberResDto?>
    fun postFcmToken(memberFcmReqDto: MemberDto.MemberFcmReqDto, token: String): Long?
    fun postAlarm(memberAlarmSettingPostReqDto: AlarmDto, token: String): AlarmDto?
    fun getAlarm(token:String): AlarmDto?
}