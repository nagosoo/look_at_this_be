package com.eunji.look_at_this.api.controller

import com.eunji.look_at_this.api.dto.LinkDto
import com.eunji.look_at_this.api.dto.LinkDto.LinkReqDto
import com.eunji.look_at_this.api.service.LinkService
import com.eunji.look_at_this.common.utils.ApiUtils
import com.eunji.look_at_this.common.utils.ApiUtils.ApiResult
import lombok.RequiredArgsConstructor
import lombok.extern.slf4j.Slf4j
import org.springframework.web.bind.annotation.*

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

    @PostMapping("/read")
    fun postReadLink(@RequestBody createReq: LinkDto.LinkReadOrBookmarkReqDto?): ApiResult<Long?> {
        return ApiUtils.success(linkService.readLink(createReq!!))
    }

    @PostMapping("/bookmark")
    fun postBookmarkLink(@RequestBody createReq: LinkDto.LinkReadOrBookmarkReqDto?): ApiResult<Long?> {
        return ApiUtils.success(linkService.bookmarkLink(createReq!!))
    }

    //개발용
    @GetMapping("/dev")
    fun getLinkListDev(): ApiResult<List<LinkDto.LinkResDtoDev>> {
        return ApiUtils.success(linkService.getLinkListDev())
    }

    @GetMapping
    fun getLinkList(@RequestBody createReq: LinkDto.LinkListReqDto?): ApiResult<List<LinkDto.LinkListResDto>> {
        return ApiUtils.success(linkService.getLinkList(createReq!!))
    }
}
