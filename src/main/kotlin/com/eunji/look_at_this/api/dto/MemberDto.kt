package com.eunji.look_at_this.api.dto

import lombok.Builder
import lombok.Getter
import lombok.NoArgsConstructor

class MemberDto {

    @Getter
    @NoArgsConstructor
    class MemberReqDto @Builder constructor(var memberEmail: String, var memberPassword: String) {

    }

    @Getter
    @NoArgsConstructor
    class MemberResDto @Builder constructor(var memberId: Long, var memberEmail: String, var memberPassword: String) {

    }

}