package com.eunji.look_at_this.api.dto

import lombok.Builder
import lombok.Getter
import lombok.NoArgsConstructor

class LinkDto {

    //링크 생성 요청
    @Getter
    @NoArgsConstructor
    data class LinkReqDto @Builder constructor(var linkUrl: String, var linkMemo: String?)

    //개발용)링크 조회 응답
    @Getter
    @NoArgsConstructor
    data class LinkResDtoDev @Builder constructor(
        var linkId: Long,
        var linkUrl: String,
        var linkMemo: String?,
        var linkThumbnail: String?,
        var linkCreatedAt: String,
    )

    //링크 조회 응답
    @Getter
    @NoArgsConstructor
    data class LinkResDto @Builder constructor(
        var linkId: Long,
        var linkUrl: String,
        var linkMemo: String?,
        var linkThumbnail: String?,
        var linkCreatedAt: String,
        var linkIsRead: Boolean = false,
        var linkIsBookmark: Boolean = false,
    )

    //링크 read, bookmark 요청하는 DTO
    @Getter
    @NoArgsConstructor
    data class LinkReadOrBookmarkReqDto @Builder constructor(var linkId: Long)


}