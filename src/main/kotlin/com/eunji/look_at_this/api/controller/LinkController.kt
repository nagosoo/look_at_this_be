package com.eunji.look_at_this.api.controller

import com.eunji.look_at_this.api.dto.LinkDto
import com.eunji.look_at_this.api.dto.LinkDto.LinkReqDto
import com.eunji.look_at_this.api.service.LinkService
import com.eunji.look_at_this.common.utils.ApiUtils
import com.eunji.look_at_this.common.utils.ApiUtils.ApiResult
import lombok.RequiredArgsConstructor
import lombok.extern.slf4j.Slf4j
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/link")
class LinkController(
        private val linkService: LinkService
) {
    @PostMapping
    fun create(@RequestBody createReq: LinkReqDto?): ApiResult<Long?> {
        return ApiUtils.success(linkService.createLink(createReq!!))
    }

    @GetMapping
    fun getLinkList(): ApiResult<List<LinkDto.LinkResDto>> {
        return ApiUtils.success(linkService.getLinkList())
    }
}
