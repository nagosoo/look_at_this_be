package com.eunji.look_at_this.api.service

import com.eunji.look_at_this.api.dto.MemberDto

interface MemberService {
    fun createMember(memberReqDto: MemberDto.MemberReqDto): Long?
    fun getMemberList(): List<MemberDto.MemberResDto?>
    fun postFcmToken(memberFcmReqDto: MemberDto.MemberFcmReqDto): Long?
}