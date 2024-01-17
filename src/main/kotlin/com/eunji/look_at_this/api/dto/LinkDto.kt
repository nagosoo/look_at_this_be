package com.eunji.look_at_this.api.dto

import lombok.Builder
import lombok.Getter
import lombok.NoArgsConstructor

class LinkDto {

    @Getter
    @NoArgsConstructor
    class LinkReqDto @Builder constructor(var linkUrl: String, var linkMemo: String) {

    }

    @Getter
    @NoArgsConstructor
    class LinkResDto @Builder constructor(var linkId: Long, var linkUrl: String, var linkMemo: String, var linkThumbnail: String, var linkCreatedAt: String) {

    }

}