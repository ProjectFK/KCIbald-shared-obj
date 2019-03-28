package com.kcibald.objects.impl

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class HTMLContentImplTest {
    val content = "content"
    val obj = HTMLContentImpl(content)

    @Test
    fun asString() {
        assertEquals(content, obj.asString())
    }

    @Test
    fun getContent() {
        assertEquals(content, obj.content)
    }
}