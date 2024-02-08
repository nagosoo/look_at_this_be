package com.eunji.look_at_this.api.controller

import com.eunji.look_at_this.api.dto.CursorResult
import com.eunji.look_at_this.api.dto.LinkDto
import com.eunji.look_at_this.api.dto.LinkDto.LinkReqDto
import com.eunji.look_at_this.api.entity.Link
import com.eunji.look_at_this.api.service.LinkService
import com.eunji.look_at_this.common.utils.ApiUtils
import com.eunji.look_at_this.common.utils.ApiUtils.ApiResult
import lombok.RequiredArgsConstructor
import lombok.extern.slf4j.Slf4j
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.web.bind.annotation.*


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/link")
class LinkController(
    private val linkService: LinkService
) {
    @PostMapping
    fun create(@RequestHeader("Authorization") token: String, @RequestBody createReq: LinkReqDto?): ApiResult<LinkDto.LinkListResDto?> {
        return ApiUtils.success(linkService.createLink(createReq!!, token))
    }

    @PostMapping("/read")
    fun postReadLink(@RequestHeader("Authorization") token: String, @RequestBody createReq: LinkDto.LinkReadOrBookmarkReqDto?): ApiResult<LinkDto.LinkListResDto?> {
        return ApiUtils.success(linkService.readLink(token, createReq!!))
    }

    @PostMapping("/bookmark")
    fun postBookmarkLink(@RequestHeader("Authorization") token: String,@RequestBody createReq: LinkDto.LinkReadOrBookmarkReqDto?): ApiResult<LinkDto.LinkListResDto?> {
        return ApiUtils.success(linkService.bookmarkLink(token,createReq!!))
    }

    //개발용
    @GetMapping("/dev")
    fun getLinkListDev(): ApiResult<List<LinkDto.LinkResDtoDev>> {
        return ApiUtils.success(linkService.getLinkListDev())
    }

    private val DEFAULT_SIZE = 10

    @GetMapping
    fun getLinkList(
        @RequestHeader("Authorization") token: String,
        @RequestParam cursorId: Long?,
        @RequestParam pageSize: Int?,
    ): CursorResult<LinkDto.LinkListResDto> {
        val pageable: Pageable = PageRequest.of(0, pageSize ?: DEFAULT_SIZE, Sort.by("linkId").descending())
        val linkListPage: CursorResult<LinkDto.LinkListResDto> =
            linkService.getLinkList( cursorId, pageable, token)
        return linkListPage
    }
}
