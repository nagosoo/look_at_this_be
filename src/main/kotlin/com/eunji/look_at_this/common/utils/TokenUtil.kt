package com.eunji.look_at_this.common.utils

import com.eunji.look_at_this.api.entity.Member
import com.eunji.look_at_this.api.repository.MemberRepository

object TokenUtil {

    fun getMemberIdByToken(token: String, memberRepository: MemberRepository): Long? {
        val parsedToken = token.split(" ")[1]
        val memberId: Long? = memberRepository.findByMemberBasicToken(parsedToken)?.memberId
        return memberId
    }

    fun getMemberByToken(token: String, memberRepository: MemberRepository): Member? {
        val memberId = getMemberIdByToken(token, memberRepository) ?: return null
        return memberRepository.findById(memberId).orElse(null) ?: return null
    }

}