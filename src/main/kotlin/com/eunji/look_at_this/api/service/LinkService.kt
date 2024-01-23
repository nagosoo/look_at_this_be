package com.eunji.look_at_this.api.service

    import com.eunji.look_at_this.api.dto.LinkDto

    interface LinkService {
        fun getLinkListDev(): List<LinkDto.LinkResDtoDev>
        fun createLink(linkReqDto: LinkDto.LinkReqDto): Long?
        fun readLink(linkReadOrBookmarkReqDto: LinkDto.LinkReadOrBookmarkReqDto): Long?
        fun bookmarkLink(linkReadOrBookmarkReqDto: LinkDto.LinkReadOrBookmarkReqDto): Long?
        fun getLinkList(linkListReqDto: LinkDto.LinkListReqDto): List<LinkDto.LinkListResDto>
}