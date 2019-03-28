package com.kcibald.objects

import java.time.LocalDateTime
import java.time.ZoneOffset

typealias Timestamp = Long

fun Timestamp.toLocalDateTime(): LocalDateTime? = runCatching {
//    before 2015 is all bs
    if (this < 1420070400)
        return null
    LocalDateTime.ofEpochSecond(this, 0, ZoneOffset.UTC)
}.getOrNull()

