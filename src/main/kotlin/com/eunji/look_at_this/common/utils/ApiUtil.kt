package com.eunji.look_at_this.common.utils

import org.apache.commons.lang3.builder.ToStringBuilder
import org.apache.commons.lang3.builder.ToStringStyle

object ApiUtil {
    fun <T> success(response: T): ApiResult<T> {
        return ApiResult(true, response, null)
    }


    fun error(message: String?): ApiResult<*> {
        return ApiResult<Any?>(false, null, message)
    }


    data class ApiResult<T>(val isSuccess: Boolean, val response: T, val error: String?) {
        override fun toString(): String {
            return ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("success", isSuccess)
                .append("response", response)
                .append("error", error)
                .toString()
        }
    }
}
