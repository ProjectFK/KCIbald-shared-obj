package com.kcibald.objects

import com.kcibald.serilization.keyspecs.ContentBasedKeySpec
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class ContentBasedTest {

    @Test
    fun authorSpec() {
        assertEquals(ContentBasedKeySpec.author, "author")
    }

    @Test
    fun createTimeStampSpec() {
        assertEquals(ContentBasedKeySpec.createTimeStamp, "create_time_stamp")
    }

    @Test
    fun updateTimeStampSpec() {
        assertEquals(ContentBasedKeySpec.updateTimestamp, "update_time_stamp")
    }

    @Test
    fun contentSpec() {
        assertEquals(ContentBasedKeySpec.content, "content")
    }

    @Test
    fun attachmentsSpec() {
        assertEquals(ContentBasedKeySpec.attachments, "attachments")
    }



}