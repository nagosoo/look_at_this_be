package com.eunji.look_at_this.common.utils

import com.eunji.look_at_this.Constance
import com.eunji.look_at_this.common.exception.NotUrlFormatException
import org.jsoup.Jsoup

object LinkUtil{

     fun getThumbnail(linkUrl: String): String {
        try {
            Jsoup.connect(linkUrl).get().run {
                return this.select("meta[property=og:image]").attr("content")
            }
        } catch (e: Exception) {
            throw NotUrlFormatException(Constance.NOT_URL_FORMAT_ERROR)
        }
    }

}