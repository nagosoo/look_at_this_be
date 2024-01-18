package com.eunji.look_at_this.api.controller

import com.eunji.look_at_this.api.dto.MemberDto
import com.eunji.look_at_this.api.service.MemberService
import com.eunji.look_at_this.common.utils.ApiUtils
import lombok.RequiredArgsConstructor
import lombok.extern.slf4j.Slf4j
import org.springframework.web.bind.annotation.*

@Slf4j
@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
class MemberController(
        private val memberService: MemberService
) {
    @PostMapping
    fun create(@RequestBody createReq: MemberDto.MemberReqDto?): ApiUtils.ApiResult<Long?> {
        return ApiUtils.success(memberService.createMember(createReq!!))
    }

    @GetMapping
    fun getMemberList(): ApiUtils.ApiResult<List<MemberDto.MemberResDto?>> {
        return ApiUtils.success(memberService.getMemberList())
    }
}
