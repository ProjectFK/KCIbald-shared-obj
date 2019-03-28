package com.kcibald.objects.impl

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class AttachmentURLImplTest {

    val url = "url"
    val obj = AttachmentURLImpl(url)

    @Test
    fun getUrl() {
        assertEquals(url, obj.url)
    }

    @Test
    fun asString() {
        assertEquals(url, obj.asString())
    }
}