package com.eunji.look_at_this.common.utils

import com.eunji.look_at_this.api.repository.MemberRepository

object TokenUtils {

   fun getMemberIdByToken(token: String, memberRepository: MemberRepository): Long {
        val parsedToken = token.split(" ")[1]
        val memberId: Long = memberRepository.findByMemberBasicToken(parsedToken).get().memberId
       return memberId
    }

}