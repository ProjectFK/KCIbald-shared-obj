package com.kcibald.objects

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

internal class TimestampKtTest {

    @Test
    fun toLocalDateTime_smaller_than_threshold() {
        val t: Timestamp = -5
        assertNull(t.toLocalDateTime())
    }

    @Test
    fun toLocalDateTime_impossible() {
        val t: Timestamp = Long.MAX_VALUE
        assertNull(t.toLocalDateTime())
    }

    @Test
    fun toLocalDateTime() {
        val t: Timestamp = 1526112272
        assertEquals(LocalDateTime.of(2018, 5, 12, 8, 4, 32), t.toLocalDateTime())
    }
}