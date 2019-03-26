package com.kcibald.serilization

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

internal class SerializationHelpersKtTest {

    @Test
    fun formatTime_normal() {
        val date1 = LocalDateTime.of(2019, 2, 23, 8, 34, 23)
        assertEquals(formatTime(date1), 1550910863.toString())
    }

}