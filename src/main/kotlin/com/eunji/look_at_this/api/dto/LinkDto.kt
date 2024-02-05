package com.eunji.look_at_this.api.dto

import lombok.Builder
import lombok.Getter
import lombok.NoArgsConstructor

class LinkDto {

    //링크 생성 요청
    @Getter
    @NoArgsConstructor
    class LinkReqDto @Builder constructor(var linkUrl: String, var linkMemo: String) {
    }

    //링크 조회 응답 - dev
    @Getter
    @NoArgsConstructor
    class LinkResDtoDev @Builder constructor(
        var linkId: Long,
        var linkUrl: String,
        var linkMemo: String,
        var linkThumbnail: String,
        var linkCreatedAt: String,
    ) {

    }

    //링크 조회 응답
    @Getter
    @NoArgsConstructor
    class LinkListResDto @Builder constructor(
        var linkId: Long,
        var linkUrl: String,
        var linkMemo: String,
        var linkThumbnail: String,
        var linkCreatedAt: String,
        var linkIsRead : Boolean  = false,
        var linkIsBookmark : Boolean = false,
    ) {

    }


    //링크 읽었다, 북마크 했다는 표시 요청
    @Getter
    @NoArgsConstructor
    class LinkReadOrBookmarkReqDto @Builder constructor(var linkId: Long) {

    }


}