package com.eunji.look_at_this.api.service

    import com.eunji.look_at_this.api.dto.CursorResult
    import com.eunji.look_at_this.api.dto.LinkDto
    import org.springframework.data.domain.Pageable

interface LinkService {
        fun getLinksForDev(): List<LinkDto.LinkResDtoDev>
        fun postLink(linkReqDto: LinkDto.LinkReqDto, token:String): LinkDto.LinkResDto?
        fun read(token: String, readReqDto: LinkDto.LinkReadOrBookmarkReqDto): LinkDto.LinkResDto?
        fun bookmark(token: String, bookmarkReqDto: LinkDto.LinkReadOrBookmarkReqDto): LinkDto.LinkResDto?
        fun getLinkPaging(cursorId: Long?, pageSize: Pageable, token: String):CursorResult<LinkDto.LinkResDto>
        fun getBookmarkLinkPaging(cursorId: Long?, pageSize: Pageable, token: String):CursorResult<LinkDto.LinkResDto>
}