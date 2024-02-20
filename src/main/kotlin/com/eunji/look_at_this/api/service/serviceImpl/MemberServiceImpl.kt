package com.eunji.look_at_this.api.service.serviceImpl

import com.eunji.look_at_this.Constance
import com.eunji.look_at_this.api.dto.AlarmDto
import com.eunji.look_at_this.api.dto.MemberDto
import com.eunji.look_at_this.api.entity.Member
import com.eunji.look_at_this.api.repository.MemberRepository
import com.eunji.look_at_this.api.service.MemberService
import com.eunji.look_at_this.common.exception.FoundException
import com.eunji.look_at_this.common.exception.NotFoundException
import com.eunji.look_at_this.common.utils.DateUtil
import com.eunji.look_at_this.common.utils.DateUtil.parseTimeToString
import com.eunji.look_at_this.common.utils.TokenUtil
import lombok.RequiredArgsConstructor
import lombok.extern.slf4j.Slf4j
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Slf4j
@Service
@RequiredArgsConstructor
class MemberServiceImpl(
    private val memberRepository: MemberRepository,
    private val passwordEncoder: BCryptPasswordEncoder
) : MemberService {
    override fun signUp(memberReqDto: MemberDto.MemberReqDto): MemberDto.MemberBasicTokenResDto? {
        if (memberRepository.findByMemberEmail(memberReqDto.memberEmail) != null) {
            throw FoundException(Constance.ALREADY_EXIST_MEMBER_ERROR)
        }
        val hashedPassword = passwordEncoder.encode(memberReqDto.memberPassword)
        val token = getBasicToken(memberReqDto.memberEmail, hashedPassword)

        Member(
            memberBasicToken = token,
            memberPassword = hashedPassword,
            memberEmail = memberReqDto.memberEmail,
        ).apply {
            memberRepository.save(this)
        }
        return MemberDto.MemberBasicTokenResDto(token)
    }

    override fun signIn(memberReqDto: MemberDto.MemberReqDto): MemberDto.MemberBasicTokenResDto? {
        val member = memberRepository.findByMemberEmail(memberReqDto.memberEmail)
            ?: throw NotFoundException(Constance.NOT_FOUND_MEMBER_ERROR) //없는 회원 일 경우

        //아이디 비번 불일치
        if (passwordEncoder.matches(memberReqDto.memberPassword, member.memberPassword).not()) {
            throw NotFoundException(Constance.NOT_FOUND_MEMBER_ERROR)
        }

        val token = getBasicToken(memberReqDto.memberEmail, member.memberPassword)
        return MemberDto.MemberBasicTokenResDto(token)
    }

    //개발용
    override fun getMembers(): List<MemberDto.MemberResDto?> {
        return memberRepository.findAll().map {
            MemberDto.MemberResDto(
                memberId = it.memberId,
                memberEmail = it.memberEmail,
                memberPassword = it.memberPassword,
                memberFcmToken = it.memberFcmToken,
                memberAlarmSetting = AlarmDto(
                    keepReceiveAlarms = it.keepReceiveAlarms,
                    alarmTime = parseTimeToString(it.alarmTime)
                ),
                memberBasicToken = it.memberBasicToken
            )
        }
    }

    override fun postFcmToken(fcmReqDto: MemberDto.MemberFcmReqDto, token: String): Boolean? {
        val member = TokenUtil.getMemberByToken(token, memberRepository) ?: return null
        member.copy(
            memberFcmToken = fcmReqDto.fcmToken
        ).apply {
            memberRepository.save(this)
            return true
        }
    }

    override fun postAlarm(alarmReqDto: AlarmDto, token: String): AlarmDto? {
        val member = TokenUtil.getMemberByToken(token, memberRepository) ?: return null
        val modifiedMember = if (alarmReqDto.keepReceiveAlarms) {
            member.copy(
                keepReceiveAlarms = true,
            )
        } else {
            member.copy(
                keepReceiveAlarms = false,
                alarmTime = DateUtil.parseStringToTime(alarmReqDto.alarmTime!!),
            )
        }

        memberRepository.save(modifiedMember)
        return alarmReqDto
    }

    override fun getAlarm(token: String): AlarmDto? {
        val member = TokenUtil.getMemberByToken(token, memberRepository) ?: return null
        return AlarmDto(
            keepReceiveAlarms = member.keepReceiveAlarms,
            alarmTime = parseTimeToString(member.alarmTime)
        )
    }

    private fun getBasicToken(email: String, hashedPw: String): String {
        return java.util.Base64.getEncoder().encodeToString("$email:$hashedPw".toByteArray())
    }
}