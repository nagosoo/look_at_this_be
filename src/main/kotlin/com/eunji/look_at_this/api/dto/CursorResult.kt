package com.eunji.look_at_this.api.dto


data class CursorResult<T>(var values: List<T>, var hasNext: Boolean, var nextCursor: Long?)