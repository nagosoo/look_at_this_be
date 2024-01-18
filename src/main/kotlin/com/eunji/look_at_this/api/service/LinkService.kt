package com.eunji.look_at_this.api.service

import com.eunji.look_at_this.api.dto.LinkDto

interface LinkService {
    fun getLinkList(): List<LinkDto.LinkResDto>
    fun createLink(linkReqDto: LinkDto.LinkReqDto): Long?
}