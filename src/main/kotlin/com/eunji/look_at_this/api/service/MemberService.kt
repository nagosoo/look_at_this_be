package com.eunji.look_at_this.api.service

import com.eunji.look_at_this.api.dto.MemberDto
import com.eunji.look_at_this.api.entity.Member

interface MemberService {
    fun createMember(memberReqDto: MemberDto.MemberReqDto): Long?
    fun getMemberList(): List<MemberDto.MemberResDto?>
    fun postFcmToken(memberFcmReqDto: MemberDto.MemberFcmReqDto): Long?
    fun postAlarm(memberAlarmSettingReqDto: MemberDto.MemberAlarmSettingReqDto): Long?
}