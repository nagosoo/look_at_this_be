package com.eunji.look_at_this.api.service.serviceImpl

import com.eunji.look_at_this.api.dto.CursorResult
import com.eunji.look_at_this.api.dto.FcmDto
import com.eunji.look_at_this.api.dto.LinkDto
import com.eunji.look_at_this.api.entity.BookMarkHistory
import com.eunji.look_at_this.api.entity.Link
import com.eunji.look_at_this.api.entity.LinkClickHistory
import com.eunji.look_at_this.api.entity.Member
import com.eunji.look_at_this.api.repository.LinkBookmarkHistoryRepository
import com.eunji.look_at_this.api.repository.LinkClickHistoryRepository
import com.eunji.look_at_this.api.repository.LinkRepository
import com.eunji.look_at_this.api.repository.MemberRepository
import com.eunji.look_at_this.api.service.FCMNotificationService
import com.eunji.look_at_this.api.service.LinkService
import com.eunji.look_at_this.common.utils.TokenUtils
import lombok.RequiredArgsConstructor
import lombok.extern.slf4j.Slf4j
import org.jsoup.Jsoup
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

    private fun getThumbnail(linkUrl: String): String {
        Jsoup.connect(linkUrl).get().run {
            return this.select("meta[property=og:image]").attr("content")
        }
    }

    override fun createLink(linkReqDto: LinkDto.LinkReqDto, token: String): Long? {
        Link(
            linkUrl = linkReqDto.linkUrl,
            linkMemo = linkReqDto.linkMemo,
            linkThumbnail = getThumbnail(linkReqDto.linkUrl),
            linkCreatedAt = LocalDateTime.now()
        ).apply {
            postFcm()
            readLink(token, LinkDto.LinkReadOrBookmarkReqDto(linkRepository.save(this).linkId))
            return linkRepository.save(this).linkId
        }
    }

    private fun postFcm() {
        memberRepository.findAll().filter {
            it.keepReceiveAlarms
        }.map {
            it.memberFcmToken
        }.forEach { fcmToken ->
            fcmToken?.let {
                fcmNotificationService.sendNotificationByUserToken(
                    FcmDto.FCMNotificationRequestDto(
                        fcmToken = fcmToken,
                        title = "ㅇㅣㄱㅓ보ㅏ보ㅏ",
                        body = "새로운 링크가 도착했오!",
                    )
                )
            }
        }
    }

    override fun readLink(token: String, linkReadOrBookmarkReqDto: LinkDto.LinkReadOrBookmarkReqDto): Long? {
        val link: Link = linkRepository.findById(linkReadOrBookmarkReqDto.linkId).get()
        val memberId = TokenUtils.getMemberIdByToken(token, memberRepository)
        val member: Member = memberRepository.findById(memberId).get()
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

    private fun getLinks(id: Long?, page: Pageable): List<Link> {
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

    override fun getLinkList(
        cursorId: Long?,
        pageSize: Pageable,
        token: String
    ): CursorResult<LinkDto.LinkListResDto> {

        val allLinks: List<Link> = getLinks(cursorId, pageSize)
        val lastIdOfList: Long? = if (allLinks.isEmpty()) null else allLinks.last().linkId

        val memberId =TokenUtils.getMemberIdByToken(token, memberRepository)

        val readLinks = linkClickHistoryRepository.findAll().filter {
            it.member?.memberId == memberId
        }.map {
            it.link?.linkId
        }

        val bookmarkedLinks = bookmarkHistoryRepository.findAll().filter {
            it.member?.memberId == memberId
        }.map {
            it.link?.linkId
        }

        val linksRes = allLinks.map {
            LinkDto.LinkListResDto(
                linkId = it.linkId,
                linkUrl = it.linkUrl,
                linkMemo = it.linkMemo,
                linkThumbnail = it.linkThumbnail,
                linkCreatedAt = it.linkCreatedAt.toString(),
                linkIsRead = readLinks.contains(it.linkId),
                linkIsBookmark = bookmarkedLinks.contains(it.linkId),
            )
        }

        return CursorResult<LinkDto.LinkListResDto>(linksRes, hasNext(lastIdOfList), getNextCursorId(lastIdOfList))
    }

    override fun bookmarkLink(token: String, linkReadOrBookmarkReqDto: LinkDto.LinkReadOrBookmarkReqDto): Long? {
        val link: Link = linkRepository.findById(linkReadOrBookmarkReqDto.linkId).get()
        val memberId =TokenUtils.getMemberIdByToken(token, memberRepository)
        val member: Member = memberRepository.findById(memberId).get()
        val linkBookmarkHistory = bookmarkHistoryRepository.findByMemberAndLink(member, link)
        if (linkBookmarkHistory == null) {
            //북마크 추가
            BookMarkHistory(
                member = member,
                link = link,
            ).apply {
                return bookmarkHistoryRepository.save(this).bookMarkHistoryId
            }
        } else {
            //북마크 해지
            bookmarkHistoryRepository.delete(linkBookmarkHistory)
            return linkBookmarkHistory.bookMarkHistoryId
        }
    }
}