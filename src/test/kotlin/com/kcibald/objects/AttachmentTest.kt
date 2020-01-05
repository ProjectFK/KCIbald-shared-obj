package com.kcibald.objects

import com.kcibald.objects.impl.AttachmentImpl
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class AttachmentTest {

    private val file = File.withIdentifier("objects.kcibald.com/akjkldfjlkajf")
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

    @Test
    fun json() {
        val apply = Attachment.vertxGenToJson(target)
        assertEquals(target, Attachment.vertxGenFromJson(apply))
    }

    @Test
    fun attachment_factory_creation() {
        assertEquals(target, Attachment.createDefault(file, name))
    }

}