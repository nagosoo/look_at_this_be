package com.eunji.look_at_this.api.service.serviceImpl

import com.eunji.look_at_this.api.dto.MemberDto
import com.eunji.look_at_this.api.entity.Member
import com.eunji.look_at_this.api.repository.MemberRepository
import com.eunji.look_at_this.api.service.MemberService
import lombok.RequiredArgsConstructor
import lombok.extern.slf4j.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Slf4j
@Service
@RequiredArgsConstructor
class MemberServiceImpl(
        private val memberRepository: MemberRepository,
        private val passwordEncoder: BCryptPasswordEncoder
) : MemberService {

    override fun createMember(memberReqDto: MemberDto.MemberReqDto): Long? {
        if (memberRepository.getMembersByMemberEmail(memberReqDto.memberEmail).isEmpty.not()) {
            print("이미 존재하는 회원입니다.")
            return null
        }
        val hashedPassword = passwordEncoder.encode(memberReqDto.memberPassword)

        Member(
                memberPassword = hashedPassword,
                memberEmail = memberReqDto.memberEmail,
                ).apply {
            return memberRepository.save(this).memberId
        }
    }

    override fun getMemberList(): List<MemberDto.MemberResDto?> {
        return memberRepository.findAll().map {
            MemberDto.MemberResDto(
                    memberId = it.memberId,
                    memberEmail = it.memberEmail,
                    memberPassword = it.memberPassword,
            )
        }
    }
}