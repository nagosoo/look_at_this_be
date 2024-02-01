package com.eunji.look_at_this.common.handler

import com.eunji.look_at_this.common.exception.FoundException
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
    fun handleFoundException(e: FoundException): ResponseEntity<ApiResult<Any>> {
       return ResponseEntity.status(HttpStatus.CONFLICT).body(error("이미 존재하는 회원입니다."))
    }

}