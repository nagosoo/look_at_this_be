package com.eunji.look_at_this.common.exception

class NotFoundException : RuntimeException {
    constructor(message: String?) : super(message)

    constructor(message: String?, cause: Throwable?) : super(message, cause)
}