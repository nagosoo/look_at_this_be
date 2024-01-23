package com.eunji.look_at_this.api.service.serviceImpl

import com.eunji.look_at_this.api.dto.LinkDto
import com.eunji.look_at_this.api.entity.Link
import com.eunji.look_at_this.api.entity.LinkClickHistory
import com.eunji.look_at_this.api.entity.Member
import com.eunji.look_at_this.api.repository.LinkHistoryRepository
import com.eunji.look_at_this.api.repository.LinkRepository
import com.eunji.look_at_this.api.repository.MemberRepository
import com.eunji.look_at_this.api.service.LinkService
import lombok.RequiredArgsConstructor
import lombok.extern.slf4j.Slf4j
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Slf4j
@Service
@RequiredArgsConstructor
class LinkServiceImpl(
    private val linkRepository: LinkRepository,
    private val linkHistoryRepository: LinkHistoryRepository,
    private val memberRepository: MemberRepository,
    private val linkClickHistoryRepository: LinkHistoryRepository
) : LinkService {

    override fun getLinkListDev(): List<LinkDto.LinkResDtoDev> {
        return linkRepository.findAll().map {
            LinkDto.LinkResDtoDev(
                linkId = it.linkId,
                linkUrl = it.linkUrl,
                linkMemo = it.linkMemo,
                linkThumbnail = it.linkThumbnail,
                linkCreatedAt = it.linkCreatedAt.toString()
            )
        }
    }

    override fun createLink(linkReqDto: LinkDto.LinkReqDto): Long? {
        Link(
            linkUrl = linkReqDto.linkUrl,
            linkMemo = linkReqDto.linkMemo,
            linkThumbnail = "https://i.namu.wiki/i/H3V9ZKvZivijkPM2wPfXOyRZWjc77sJPzRJGGD1YiAYshpQQUSXxNgR4b3VjVQWniomnar2CrcF_vBoRfo0-YwjWnV4qYA7g57wrbw1N4sYuBaY2PlKrqahMYIpptVhFvWFAHSelK_Kz-HOoQgtFVQ.webp",
            linkCreatedAt = LocalDateTime.now()
        ).apply {
            return linkRepository.save(this).linkId
        }
    }

    override fun readLink(linkReadReqDto: LinkDto.LinkReadReqDto): Long? {
        val link: Link = linkRepository.findById(linkReadReqDto.linkId).get()
        val member: Member = memberRepository.findById(linkReadReqDto.memberId).get()
        val linkClickHistory = linkHistoryRepository.findByMemberAndLink(member, link)
        if (linkClickHistory == null) {
            //새로 read
            LinkClickHistory(
                member = member,
                link = link,
            ).apply {
                return linkClickHistoryRepository.save(this).linkClickHistoryId
            }
        } else {
            //이미 Read
            return linkClickHistory.linkClickHistoryId
        }
    }

    override fun getLinkList(linkListReqDto: LinkDto.LinkListReqDto): List<LinkDto.LinkListResDto> {
        val allLinks = linkRepository.findAll()

        val readLinks = linkClickHistoryRepository.findAll().filter {
            it.member?.memberId == linkListReqDto.memberId
        }.map {
            it.link?.linkId
        }

        return allLinks.map {
            LinkDto.LinkListResDto(
                linkId = it.linkId,
                linkUrl = it.linkUrl,
                linkMemo = it.linkMemo,
                linkThumbnail = it.linkThumbnail,
                linkCreatedAt = it.linkCreatedAt.toString(),
                linkIsRead = readLinks.contains(it.linkId)
            )
        }
    }
}