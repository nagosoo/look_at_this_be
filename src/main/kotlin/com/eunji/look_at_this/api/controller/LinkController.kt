package com.eunji.look_at_this.api.controller

import com.eunji.look_at_this.api.dto.CursorResult
import com.eunji.look_at_this.api.dto.LinkDto
import com.eunji.look_at_this.api.dto.LinkDto.LinkReqDto
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

    private val DEFAULT_SIZE = 10

    @GetMapping
    fun getLinkList(
        @RequestParam cursorId: Long?,
        @RequestParam pageSize: Int?,
        @RequestBody createReq: LinkDto.LinkListReqDto
    ): CursorResult<LinkDto.LinkListResDto> {
        val pageable: Pageable = PageRequest.of(0, pageSize ?: DEFAULT_SIZE, Sort.by("linkId").descending())
        val linkListPage: CursorResult<LinkDto.LinkListResDto> =
            linkService.getLinkList(createReq, cursorId, pageable)
        return linkListPage
    }
}
