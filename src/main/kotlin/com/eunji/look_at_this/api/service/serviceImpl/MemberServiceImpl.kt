package com.eunji.look_at_this.api.service.serviceImpl

import com.eunji.look_at_this.api.dto.AlarmDto
import com.eunji.look_at_this.api.dto.MemberDto
import com.eunji.look_at_this.api.entity.Member
import com.eunji.look_at_this.api.repository.MemberRepository
import com.eunji.look_at_this.api.service.MemberService
import com.eunji.look_at_this.common.exception.FoundException
import com.eunji.look_at_this.common.exception.NotFoundException
import com.eunji.look_at_this.common.utils.DateUtil
import com.eunji.look_at_this.common.utils.DateUtil.parseTimeToString
import com.eunji.look_at_this.common.utils.TokenUtils
import lombok.RequiredArgsConstructor
import lombok.extern.slf4j.Slf4j
import org.slf4j.LoggerFactory
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Slf4j
@Service
@RequiredArgsConstructor
class MemberServiceImpl(
    private val memberRepository: MemberRepository,
    private val passwordEncoder: BCryptPasswordEncoder
) : MemberService {
    private val log = LoggerFactory.getLogger(this.javaClass)!!
    override fun createMember(memberReqDto: MemberDto.MemberReqDto): MemberDto.MemberBasicTokenResDto? {
        if (memberRepository.getMembersByMemberEmail(memberReqDto.memberEmail).isEmpty.not()) {
            log.debug("이미 존재하는 회원입니다.")
            throw FoundException("이미 존재하는 아이디야ㅠ_ㅠ")
        }
        val hashedPassword = passwordEncoder.encode(memberReqDto.memberPassword)
        val token = getMemberToken(memberReqDto.memberEmail, hashedPassword)

        Member(
            memberBasicToken = token,
            memberPassword = hashedPassword,
            memberEmail = memberReqDto.memberEmail,
        ).apply {
            memberRepository.save(this)
        }
        return MemberDto.MemberBasicTokenResDto(token)
    }

    override fun logIn(memberReqDto: MemberDto.MemberReqDto): MemberDto.MemberBasicTokenResDto? {
        //없는 회원 일 경우
        if (memberRepository.getMembersByMemberEmail(memberReqDto.memberEmail).isEmpty) {
            throw NotFoundException("아이디 혹은 비밀번호가 틀렸어ㅠ_ㅠ")
        }

        //아이디 비번 불일치
        val hashedPassword = memberRepository.getMembersByMemberEmail(memberReqDto.memberEmail).apply {
            if (passwordEncoder.matches(memberReqDto.memberPassword, this.get().memberPassword).not()) {
                throw NotFoundException("아이디 혹은 비밀번호가 틀렸어ㅠ_ㅠ")
            }
        }.get().memberPassword

        val token = getMemberToken(memberReqDto.memberEmail, hashedPassword)
        return MemberDto.MemberBasicTokenResDto(token)
    }

    private fun getMemberToken(memberEmail: String, memberHashedPw: String): String {
        return java.util.Base64.getEncoder().encodeToString("$memberEmail:$memberHashedPw".toByteArray())
    }

    override fun getMemberList(): List<MemberDto.MemberResDto?> {
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

    override fun postFcmToken(memberFcmReqDto: MemberDto.MemberFcmReqDto, token: String): Boolean? {
        val memberId =TokenUtils.getMemberIdByToken(token, memberRepository)
        val member = memberRepository.findById(memberId).orElse(null) ?: return null
        member.copy(
            memberFcmToken = memberFcmReqDto.fcmToken
        ).apply {
            memberRepository.save(this)
            return true
        }
    }

    override fun postAlarm(memberAlarmSettingPostReqDto: AlarmDto, token: String): AlarmDto? {
        val memberId =TokenUtils.getMemberIdByToken(token, memberRepository)
        val member = memberRepository.findById(memberId).orElse(null) ?: return null
        val modifiedMember = if (memberAlarmSettingPostReqDto.keepReceiveAlarms) {
            member.copy(
                keepReceiveAlarms = true,
            )
        } else {
            member.copy(
                keepReceiveAlarms = false,
                alarmTime = DateUtil.parseStringToTime(memberAlarmSettingPostReqDto.alarmTime!!),
            )
        }

        memberRepository.save(modifiedMember)
        return memberAlarmSettingPostReqDto
    }

    override fun getAlarm(token: String): AlarmDto? {
        val memberId =TokenUtils.getMemberIdByToken(token, memberRepository)
        val member = memberRepository.findById(memberId).orElse(null) ?: return null
        return AlarmDto(
            keepReceiveAlarms = member.keepReceiveAlarms,
            alarmTime = parseTimeToString(member.alarmTime)
        )
    }
}