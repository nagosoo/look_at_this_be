package com.eunji.look_at_this.api.service.serviceImpl

import com.eunji.look_at_this.Constance
import com.eunji.look_at_this.api.dto.CursorResult
import com.eunji.look_at_this.api.dto.FcmDto
import com.eunji.look_at_this.api.dto.LinkDto
import com.eunji.look_at_this.api.entity.*
import com.eunji.look_at_this.api.repository.LinkBookmarkHistoryRepository
import com.eunji.look_at_this.api.repository.LinkClickHistoryRepository
import com.eunji.look_at_this.api.repository.LinkRepository
import com.eunji.look_at_this.api.repository.MemberRepository
import com.eunji.look_at_this.api.service.FCMNotificationService
import com.eunji.look_at_this.api.service.LinkService
import com.eunji.look_at_this.common.utils.LinkUtil
import com.eunji.look_at_this.common.utils.TokenUtil
import lombok.RequiredArgsConstructor
import lombok.extern.slf4j.Slf4j
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.time.LocalDateTime


@Slf4j
@Service
@RequiredArgsConstructor
class LinkServiceImpl(
    private val linkRepository: LinkRepository,
    private val linkHistoryRepository: LinkClickHistoryRepository,
    private val memberRepository: MemberRepository,
    private val linkClickHistoryRepository: LinkClickHistoryRepository,
    private val bookmarkHistoryRepository: LinkBookmarkHistoryRepository,
    private val fcmNotificationService: FCMNotificationService,
) : LinkService {

    override fun getLinksForDev(): List<LinkDto.LinkResDtoDev> {
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

    override fun postLink(linkReqDto: LinkDto.LinkReqDto, token: String): LinkDto.LinkResDto? {
        var absoluteUrl = linkReqDto.linkUrl
        if (!linkReqDto.linkUrl.startsWith("http") && !linkReqDto.linkUrl.startsWith("https")) {
            absoluteUrl = "https://${linkReqDto.linkUrl}"
        }
        Link(
            linkUrl = absoluteUrl,
            linkMemo = linkReqDto.linkMemo,
            linkThumbnail = LinkUtil.getThumbnail(absoluteUrl),
            linkCreatedAt = LocalDateTime.now()
        ).apply {
            postFcm()
            val savedLink = linkRepository.save(this).apply {
                read(token, LinkDto.LinkReadOrBookmarkReqDto(linkId))
            }
            return LinkDto.LinkResDto(
                linkId = savedLink.linkId,
                linkUrl = savedLink.linkUrl,
                linkMemo = savedLink.linkMemo,
                linkThumbnail = savedLink.linkThumbnail,
                linkCreatedAt = savedLink.linkCreatedAt.toString(),
                linkIsRead = true,
                linkIsBookmark = false,
            )
        }
    }

    private fun postFcm() {
        memberRepository.findAll().filter {
            it.keepReceiveAlarms
        }.map {
            it.memberFcmToken
        }.forEach { fcmToken ->
            fcmToken?.let {
                fcmNotificationService.sendNotification(
                    FcmDto.FCMNotificationRequestDto(
                        fcmToken = fcmToken,
                        title = Constance.FCM_TITLE,
                        body = Constance.FCM_BODY,
                    )
                )
            }
        }
    }

    override fun getLinkPaging(
        cursorId: Long?,
        pageSize: Pageable,
        token: String
    ): CursorResult<LinkDto.LinkResDto> {

        val allLinks: List<Link> = getLinksByCursor(cursorId, pageSize)
        val lastIdOfList: Long? = if (allLinks.isEmpty()) null else allLinks.last().linkId
        val memberId = TokenUtil.getMemberIdByToken(token, memberRepository)!!
        val readLinks = getReadLinks(memberId)
        val bookmarkedLinks = getBookmarkLinks(memberId)

        val linksRes = allLinks.map {
            LinkDto.LinkResDto(
                linkId = it.linkId,
                linkUrl = it.linkUrl,
                linkMemo = it.linkMemo,
                linkThumbnail = it.linkThumbnail,
                linkCreatedAt = it.linkCreatedAt.toString(),
                linkIsRead = readLinks.contains(it.linkId),
                linkIsBookmark = bookmarkedLinks.contains(it.linkId),
            )
        }

        return CursorResult(linksRes, hasNext(lastIdOfList), getNextCursorId(lastIdOfList))
    }

    override fun getBookmarkLinkPaging(
        cursorId: Long?,
        pageSize: Pageable,
        token: String
    ): CursorResult<LinkDto.LinkResDto> {

        val memberId = TokenUtil.getMemberIdByToken(token, memberRepository)!!
        val allLinks: List<Link> = getLinksByCursor(cursorId, pageSize)
        val bookmarkLinks: List<Link> = allLinks.filter {
            it.bookMarkHistories.any { it.member?.memberId == memberId }
        }
        val lastIdOfList: Long? = if (allLinks.isEmpty()) null else allLinks.last().linkId
        val readLinks = getReadLinks(memberId)

        val linksRes = bookmarkLinks.map {
            LinkDto.LinkResDto(
                linkId = it.linkId,
                linkUrl = it.linkUrl,
                linkMemo = it.linkMemo,
                linkThumbnail = it.linkThumbnail,
                linkCreatedAt = it.linkCreatedAt.toString(),
                linkIsRead = readLinks.contains(it.linkId),
                linkIsBookmark = true,
            )
        }
        return CursorResult(linksRes, hasNext(lastIdOfList), getNextCursorId(lastIdOfList))
    }

    override fun read(
        token: String,
        readReqDto: LinkDto.LinkReadOrBookmarkReqDto
    ): LinkDto.LinkResDto? {
        val link: Link = linkRepository.findById(readReqDto.linkId).get()
        val member = TokenUtil.getMemberByToken(token, memberRepository) ?: return null
        var linkClickHistory = linkHistoryRepository.findByMemberAndLink(member, link)
        if (linkClickHistory == null) {
            //새로 read
            linkClickHistory = LinkClickHistory(
                member = member,
                link = link,
            ).apply {
                linkClickHistoryRepository.save(this)
            }
        }
        return getLinkResDto(link, linkClickHistory, member)
    }

    override fun bookmark(
        token: String,
        bookmarkReqDto: LinkDto.LinkReadOrBookmarkReqDto
    ): LinkDto.LinkResDto? {
        val link: Link = linkRepository.findById(bookmarkReqDto.linkId).get()
        val member = TokenUtil.getMemberByToken(token, memberRepository) ?: return null
        var linkBookmarkHistory = bookmarkHistoryRepository.findByMemberAndLink(member, link)
        if (linkBookmarkHistory == null) {
            //북마크 추가
            linkBookmarkHistory = BookMarkHistory(
                member = member,
                link = link,
            ).apply {
                bookmarkHistoryRepository.save(this)
            }
        } else {
            //북마크 해지
            bookmarkHistoryRepository.delete(linkBookmarkHistory)
        }
        return getLinkResDto(link, linkBookmarkHistory, member)
    }

    private fun getLinksByCursor(id: Long?, page: Pageable): List<Link> {
        if (id == null) return this.linkRepository.findAllByOrderByLinkIdDesc(page)
        return this.linkRepository.findByLinkIdLessThanOrderByLinkIdDesc(
            id,
            page
        )
    }

    private fun hasNext(id: Long?): Boolean {
        if (id == null) return false
        return this.linkRepository.existsByLinkIdLessThan(id)
    }

    private fun getNextCursorId(lastCursorId: Long?): Long? {
        if (lastCursorId == null) return null
        return if (lastCursorId > 1) lastCursorId else null
    }

    private fun getReadLinks(memberId: Long): List<Long?> {
        return linkClickHistoryRepository.findAll().filter {
            it.member?.memberId == memberId
        }.map {
            it.link?.linkId
        }
    }

    private fun getBookmarkLinks(memberId: Long): List<Long?> {
        return bookmarkHistoryRepository.findAll().filter {
            it.member?.memberId == memberId
        }.map {
            it.link?.linkId
        }
    }

    private fun getLinkResDto(link: Link, linkHistory: LinkHistory, member: Member): LinkDto.LinkResDto {
        return LinkDto.LinkResDto(
            linkId = link.linkId,
            linkUrl = link.linkUrl,
            linkMemo = link.linkMemo,
            linkThumbnail = link.linkThumbnail,
            linkCreatedAt = link.linkCreatedAt.toString(),
            linkIsRead = link.linkClickHistories.any { linkHistory.link?.linkId == link.linkId && linkHistory.member?.memberId == member.memberId },
            linkIsBookmark = link.bookMarkHistories.any { linkHistory.link?.linkId == link.linkId && linkHistory.member?.memberId == member.memberId },
        )
    }
}