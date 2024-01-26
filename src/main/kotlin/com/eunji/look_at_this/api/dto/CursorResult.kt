package com.eunji.look_at_this.api.dto


class CursorResult<T>(var values: List<T>, var hasNext: Boolean, var nextCursor: Long?)