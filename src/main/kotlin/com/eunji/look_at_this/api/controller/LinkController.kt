package com.eunji.look_at_this.api.controller

import com.eunji.look_at_this.Constance
import com.eunji.look_at_this.api.dto.CursorResult
import com.eunji.look_at_this.api.dto.LinkDto
import com.eunji.look_at_this.api.dto.LinkDto.LinkReqDto
import com.eunji.look_at_this.api.service.LinkService
import com.eunji.look_at_this.common.utils.ApiUtil
import com.eunji.look_at_this.common.utils.ApiUtil.ApiResult
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
    fun postLink(
        @RequestHeader("Authorization") token: String,
        @RequestBody linkReqDto: LinkReqDto?
    ): ApiResult<LinkDto.LinkResDto?> {
        return ApiUtil.success(linkService.postLink(linkReqDto!!, token))
    }

    @PostMapping("/read")
    fun read(
        @RequestHeader("Authorization") token: String,
        @RequestBody readReqDto: LinkDto.LinkReadOrBookmarkReqDto?
    ): ApiResult<LinkDto.LinkResDto?> {
        return ApiUtil.success(linkService.read(token, readReqDto!!))
    }

    @PostMapping("/bookmark")
    fun bookmark(
        @RequestHeader("Authorization") token: String,
        @RequestBody bookmarkReqDto: LinkDto.LinkReadOrBookmarkReqDto?
    ): ApiResult<LinkDto.LinkResDto?> {
        return ApiUtil.success(linkService.bookmark(token, bookmarkReqDto!!))
    }

    //개발용
    @GetMapping("/dev")
    fun getLinksForDev(): ApiResult<List<LinkDto.LinkResDtoDev>> {
        return ApiUtil.success(linkService.getLinksForDev())
    }

    @GetMapping
    fun getLinkPaging(
        @RequestHeader("Authorization") token: String,
        @RequestParam cursorId: Long?,
        @RequestParam pageSize: Int?,
    ): CursorResult<LinkDto.LinkResDto> {
        val pageable: Pageable =
            PageRequest.of(0, pageSize ?: Constance.DEFAULT_PAGING_SIZE, Sort.by("linkId").descending())
        val linkListPage: CursorResult<LinkDto.LinkResDto> =
            linkService.getLinkPaging(cursorId, pageable, token)
        return linkListPage
    }

    @GetMapping("/bookmark")
    fun getBookmarkLinkPaging(
        @RequestHeader("Authorization") token: String,
        @RequestParam cursorId: Long?,
        @RequestParam pageSize: Int?,
    ): CursorResult<LinkDto.LinkResDto> {
        val pageable: Pageable =
            PageRequest.of(0, pageSize ?: Constance.DEFAULT_PAGING_SIZE, Sort.by("linkId").descending())
        val linkListPage: CursorResult<LinkDto.LinkResDto> =
            linkService.getBookmarkLinkPaging(cursorId, pageable, token)
        return linkListPage
    }
}
