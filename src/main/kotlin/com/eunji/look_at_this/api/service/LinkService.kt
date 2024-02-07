package com.eunji.look_at_this.api.service

    import com.eunji.look_at_this.api.dto.CursorResult
    import com.eunji.look_at_this.api.dto.LinkDto
    import com.eunji.look_at_this.api.entity.Link
    import org.springframework.data.domain.Page
    import org.springframework.data.domain.Pageable

interface LinkService {
        fun getLinkListDev(): List<LinkDto.LinkResDtoDev>
        fun createLink(linkReqDto: LinkDto.LinkReqDto,token:String): LinkDto.LinkListResDto?
        fun readLink(token: String, linkReadOrBookmarkReqDto: LinkDto.LinkReadOrBookmarkReqDto): Long?
        fun bookmarkLink(token: String, linkReadOrBookmarkReqDto: LinkDto.LinkReadOrBookmarkReqDto): Long?
        fun getLinkList(cursorId: Long?, pageSize: Pageable, token: String):CursorResult<LinkDto.LinkListResDto>
}