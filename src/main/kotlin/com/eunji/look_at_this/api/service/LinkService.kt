package com.eunji.look_at_this.api.service

    import com.eunji.look_at_this.api.dto.CursorResult
    import com.eunji.look_at_this.api.dto.LinkDto
    import org.springframework.data.domain.Page
    import org.springframework.data.domain.Pageable

interface LinkService {
        fun getLinkListDev(): List<LinkDto.LinkResDtoDev>
        fun createLink(linkReqDto: LinkDto.LinkReqDto): Long?
        fun readLink(linkReadOrBookmarkReqDto: LinkDto.LinkReadOrBookmarkReqDto): Long?
        fun bookmarkLink(linkReadOrBookmarkReqDto: LinkDto.LinkReadOrBookmarkReqDto): Long?
        fun getLinkList(linkListReqDto: LinkDto.LinkListReqDto,cursorId: Long?, pageSize: Pageable):CursorResult<LinkDto.LinkListResDto>
}