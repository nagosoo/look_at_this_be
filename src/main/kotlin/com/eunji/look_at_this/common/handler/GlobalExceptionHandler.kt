package com.eunji.look_at_this.common.handler

import com.eunji.look_at_this.Constance
import com.eunji.look_at_this.common.exception.FoundException
import com.eunji.look_at_this.common.exception.NotFoundException
import com.eunji.look_at_this.common.exception.NotUrlFormatException
import com.eunji.look_at_this.common.utils.ApiUtil
import com.eunji.look_at_this.common.utils.ApiUtil.ApiResult
import lombok.extern.slf4j.Slf4j
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler


@Slf4j
@ControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(
        value = [FoundException::class]
    )
    fun handleFoundException(e: FoundException): ResponseEntity<ApiResult<*>> {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ApiUtil.error(Constance.ALREADY_EXIST_MEMBER_ERROR))
    }

    @ExceptionHandler(
        value = [NotFoundException::class]
    )
    fun handleNotFoundException(e: NotFoundException): ResponseEntity<ApiResult<*>> {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ApiUtil.error(Constance.NOT_FOUND_MEMBER_ERROR))
    }

    @ExceptionHandler(
        value = [NotUrlFormatException::class]
    )
    fun handleNotUrlFormatException(e: NotUrlFormatException): ResponseEntity<ApiResult<*>> {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiUtil.error(Constance.NOT_URL_FORMAT_ERROR))
    }

}