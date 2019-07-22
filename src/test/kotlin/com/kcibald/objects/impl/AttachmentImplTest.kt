package com.kcibald.objects.impl

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class AttachmentImplTest {

    private val file = "objects.kcibald.com/akjkldfjlkajf"
    private val name = "name"

    val target = AttachmentImpl(file, name)

    @Test
    fun testName() {
        assertEquals(name, target.name)
    }

    @Test
    fun testFile() {
        assertEquals(file, target.file)
    }

}