package com.kcibald.utils

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class UrlKeyConversionKtTest {

    @Test
    fun toURLKey() {
        assertEquals("abcdefg", "abcdefg".toURLKey())
        assertEquals("12345", "12345".toURLKey())
        assertEquals("abd-e", "abd e".toURLKey())
        assertEquals("---d", "///d".toURLKey())
    }
}