package com.eunji.look_at_this.api.service

import com.eunji.look_at_this.api.dto.AlarmDto
import com.eunji.look_at_this.api.dto.MemberDto

interface MemberService {
    fun createMember(memberReqDto: MemberDto.MemberReqDto): String?
    fun logIn(memberReqDto: MemberDto.MemberReqDto): String?
    fun getMemberList(): List<MemberDto.MemberResDto?>
    fun postFcmToken(memberFcmReqDto: MemberDto.MemberFcmReqDto): Long?
    fun postAlarm(memberAlarmSettingPostReqDto: MemberDto.MemberAlarmSettingPostReqDto): Long?
    fun getAlarm(memberId:Long): AlarmDto?
}