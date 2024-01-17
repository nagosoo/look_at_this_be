package com.eunji.look_at_this.api.service.serviceImpl

import com.eunji.look_at_this.api.dto.LinkDto
import com.eunji.look_at_this.api.entity.Link
import com.eunji.look_at_this.api.repository.LinkRepository
import lombok.RequiredArgsConstructor
import lombok.extern.slf4j.Slf4j
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Slf4j
@Service
@RequiredArgsConstructor
class LinkServiceImpl(
        private val linkRepository: LinkRepository
) : LinkService {


    override fun getLinkList(): List<LinkDto.LinkResDto> {
        return linkRepository.findAll().map {
            LinkDto.LinkResDto(
                    linkId = it.linkId,
                    linkUrl = it.linkUrl,
                    linkMemo = it.linkMemo,
                    linkThumbnail = it.linkThumbnail,
                    linkCreatedAt = it.linkCreatedAt.toString()
            )
        }
    }

    override fun createLink(linkReqDto: LinkDto.LinkReqDto): Long? {
        linkReqDto.linkUrl
        linkReqDto.linkMemo
        Link(
                linkUrl = linkReqDto.linkUrl,
                linkMemo = linkReqDto.linkMemo,
                linkThumbnail = "https://i.namu.wiki/i/H3V9ZKvZivijkPM2wPfXOyRZWjc77sJPzRJGGD1YiAYshpQQUSXxNgR4b3VjVQWniomnar2CrcF_vBoRfo0-YwjWnV4qYA7g57wrbw1N4sYuBaY2PlKrqahMYIpptVhFvWFAHSelK_Kz-HOoQgtFVQ.webp",
                linkCreatedAt = LocalDateTime.now()
        ).apply {
            return linkRepository.save(this).linkId
        }
    }
}