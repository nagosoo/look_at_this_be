package com.eunji.look_at_this.common.handler

import com.eunji.look_at_this.common.exception.FoundException
import com.eunji.look_at_this.common.exception.NotFoundException
import com.eunji.look_at_this.common.utils.ApiUtils
import com.eunji.look_at_this.common.utils.ApiUtils.ApiResult
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
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ApiUtils.error("이미 존재하는 아이디야ㅠ_ㅠ"))
    }

    @ExceptionHandler(
        value = [NotFoundException::class]
    )
    fun handleNotFoundException(e: NotFoundException): ResponseEntity<ApiResult<*>> {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ApiUtils.error("아이디 혹은 비밀번호가 틀렸어ㅠ_ㅠ"))
    }

}