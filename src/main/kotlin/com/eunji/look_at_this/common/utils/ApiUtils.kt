package com.eunji.look_at_this.common.utils

import org.apache.commons.lang3.builder.ToStringBuilder
import org.apache.commons.lang3.builder.ToStringStyle
import org.springframework.http.HttpStatus

object ApiUtils {
    fun <T> success(response: T): ApiResult<T> {
        return ApiResult(true, response, null)
    }

    fun <T> fail(response: T): ApiResult<T> {
        return ApiResult(false, response, null)
    }

    fun error(throwable: Throwable, status: HttpStatus): ApiResult<*> {
        return ApiResult<Any?>(false, null, ApiError(throwable, status))
    }

    fun error(message: String?, status: HttpStatus): ApiResult<*> {
        return ApiResult<Any?>(false, null, ApiError(message, status))
    }

    class ApiError internal constructor(val message: String?, status: HttpStatus) {
        val status: Int = status.value()

        internal constructor(throwable: Throwable, status: HttpStatus) : this(throwable.message, status)

        override fun toString(): String {
            return ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                    .append("message", message)
                    .append("status", status)
                    .toString()
        }
    }

    class ApiResult<T>(val isSuccess: Boolean, val response: T, val error: ApiError?) {
        override fun toString(): String {
            return ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                    .append("success", isSuccess)
                    .append("response", response)
                    .append("error", error)
                    .toString()
        }
    }
}