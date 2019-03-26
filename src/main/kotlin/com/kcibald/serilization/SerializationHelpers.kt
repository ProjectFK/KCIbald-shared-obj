package com.kcibald.serilization

import java.time.LocalDateTime
import java.time.ZoneOffset

fun formatTime(time: LocalDateTime) = time.toEpochSecond(ZoneOffset.UTC).toString()